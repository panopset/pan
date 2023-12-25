package com.panopset.blackjackEngine

import java.util.*

class Strategy(blackjackConfiguration: BlackjackConfiguration) : Configurable(blackjackConfiguration) {

    fun getRecommendation(s: Situation): String {
        val line = findStrategyLine(s)
        return line!!.getAction(s)
    }

    private var sls: Map<StratCat, MutableMap<String, StrategyLine>>? = null
    private var hardHeader = ""
    private var softHeader = ""
    private var splitHeader = ""
    fun getHeaderFor(cat: StratCat?): String {
        return when (cat) {
            StratCat.SOFT -> softHeader
            StratCat.SPLIT -> splitHeader
            else -> hardHeader
        }
    }

    val strategyLines: Map<StratCat, MutableMap<String, StrategyLine>>?
        get() {
            if (sls == null) {
                sls = createStrategyLineMap()
                populateStrategyLineMap()
            }
            return sls
        }

    private fun populateStrategyLineMap() {
        var reading = StratCat.NONE
        for (s in Objects.requireNonNull(blackjackConfiguration.strategyData)) {
            if (s.length < 1 || s.substring(0, 1) == "#") {
                continue
            }
            if (StratCat.HARD.toString().equals(s.substring(0, 4), ignoreCase = true)) {
                reading = StratCat.HARD
                hardHeader = s
            } else if (StratCat.SOFT.toString().equals(s.substring(0, 4), ignoreCase = true)) {
                reading = StratCat.SOFT
                softHeader = s
            } else if (StratCat.SPLIT.toString().equals(s.substring(0, 5), ignoreCase = true)) {
                reading = StratCat.SPLIT
                splitHeader = s
            } else {
                if (StratCat.HARD == reading) {
                    val sl = StrategyLine(StratCat.HARD, s, blackjackConfiguration)
                    sls!![StratCat.HARD]!![sl.key] = sl
                } else if (StratCat.SOFT == reading) {
                    val sl = StrategyLine(StratCat.SOFT, s, blackjackConfiguration)
                    sls!![StratCat.SOFT]!![sl.key] = sl
                } else {
                    val sl = StrategyLine(StratCat.SPLIT, s, blackjackConfiguration)
                    sls!![StratCat.SPLIT]!![sl.key] = sl
                }
            }
        }
    }

    fun findStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        var rtn: StrategyLine? = null
        if (shouldSplit(s)) {
            rtn = findSplitStrategyLine(s)
        }
        if (rtn != null) {
            return rtn
        }
        if (s.handPlayer.isSoft) {
            rtn = findSoftStrategyLine(s)
        }
        return rtn ?: findHardStrategyLine(s)
    }

    private fun shouldSplit(s: Situation): Boolean {
        if (s.handPlayer == null) {
            return false
        }
        if (s.handPlayer.isInitialDeal && s.handPlayer.isCardFacesSplittable) {
            val v = s.handPlayer.cards[0].hardValue
            return v != 5 && v != 10
        }
        return false
    }

    private fun findSplitStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val cv = s.handPlayer.getHardValueOf(0)
        var key = "$cv,$cv"
        if (key == "1,1") {
            key = "A,A"
        }
        return strategyLines!![StratCat.SPLIT]!![key]
    }

    private fun findSoftStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val v = s.handPlayer.value
        val key = if (v > 19) "20+" else v.toString()
        return strategyLines!![StratCat.SOFT]!![key]
    }

    private fun findHardStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val v = s.handPlayer.value
        val key = if (v > 17) {
            "18+"
        } else if (v < 8) {
            "7"
        } else {
            v.toString()
        }
        return strategyLines!![StratCat.HARD]!![key]
    }

    private fun createStrategyLineMap(): Map<StratCat, MutableMap<String, StrategyLine>> {
        val rtn: MutableMap<StratCat, MutableMap<String, StrategyLine>> = EnumMap(
            StratCat::class.java
        )
        rtn[StratCat.HARD] = HashMap()
        rtn[StratCat.SOFT] = HashMap()
        rtn[StratCat.SPLIT] = HashMap()
        return rtn
    }
}
