package com.panopset.blackjackEngine

import com.panopset.compat.FlagSwitch

enum class DeckPile {
    INSTANCE;

    private val flag = FlagSwitch()

    companion object {
        @JvmStatic
        fun reset() {
            INSTANCE.flag.reset()
        }

        @JvmStatic
        fun pull(): Boolean {
            return INSTANCE.flag.pull()
        }
    }
}
