package com.panopset.blackjackEngine

import java.util.*

class StrategyLine(val stratCat: StratCat, val source: String, private val config: BlackjackConfiguration) {

    private var strategyLineElements: MutableList<String> = ArrayList()
    init {
        val st = StringTokenizer(source, " ")
        while (st.hasMoreElements()) {
            strategyLineElements.add(st.nextToken())
        }
    }

    val key: String

    fun getAction(situation: Situation): String {
        if (situation.dealerUpCard == null) {
            return CMD_DEAL
        }
        return StrategyAction(
            situation,
            CasinoRules(
                config.isLateSurrenderAllowed,
                config.isDealerHitSoft17,
                config.isDoubleAfterSplitAllowed,
                config.decks
            )
        )
            .getRecommendedAction(getTextForDealerUpCard(situation.dealerUpCard))
    }

    private fun getTextForDealerUpCard(card: BlackjackCard): String {
        return getStrategyLineElements()[card.softValue - 1]
    }


    init {
        key = getStrategyLineElements()[0]
    }

    private fun getStrategyLineElements(): List<String> {
        return strategyLineElements
    }

    override fun toString(): String {
        return source
    }
}
