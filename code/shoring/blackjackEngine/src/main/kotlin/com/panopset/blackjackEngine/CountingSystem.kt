package com.panopset.blackjackEngine

import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import java.util.*

class CountingSystem(val name: String, dta: String, val selection: Int) : Comparable<CountingSystem> {
    var count = 0
        private set
    private val vals: MutableMap<String, Int> = HashMap()

    init {
        val st = StringTokenizer(dta, " ")
        vals["2"] = Stringop.parseInt( st.nextToken().trim())
        vals["3"] = Stringop.parseInt( st.nextToken().trim())
        vals["4"] = Stringop.parseInt( st.nextToken().trim())
        vals["5"] = Stringop.parseInt( st.nextToken().trim())
        vals["6"] = Stringop.parseInt( st.nextToken().trim())
        vals["7"] = Stringop.parseInt( st.nextToken().trim())
        vals["8"] = Stringop.parseInt( st.nextToken().trim())
        vals["9"] = Stringop.parseInt( st.nextToken().trim())
        vals["T"] = Stringop.parseInt( st.nextToken().trim())
        vals["A"] = Stringop.parseInt( st.nextToken().trim())
    }

    fun count(blackjackCard: BlackjackCard) {
        if (blackjackCard.isAce) {
            count += vals["A"]!!
        } else {
            var valRep = String.format("%d", blackjackCard.softValue)
            if ("10" == valRep) {
                valRep = "T"
            }
            count += vals[valRep]!!
        }
    }

    override fun compareTo(other: CountingSystem): Int {
        return selection.compareTo(other.selection)
    }

    override fun equals(other: Any?): Boolean {
        return other is CountingSystem && selection == other.selection
    }

    override fun hashCode(): Int {
        return selection.hashCode()
    }

    fun resetCount() {
        count = 0
    }
}
