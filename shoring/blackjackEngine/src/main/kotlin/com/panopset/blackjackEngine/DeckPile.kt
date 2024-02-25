package com.panopset.blackjackEngine

import com.panopset.compat.FlagSwitch

object DeckPile {

    private val flag = FlagSwitch()

    fun reset() {
        flag.reset()
    }

    fun pull(): Boolean {
        return flag.pull()
    }
}
