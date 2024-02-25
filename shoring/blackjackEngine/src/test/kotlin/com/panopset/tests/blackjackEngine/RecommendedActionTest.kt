package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RecommendedActionTest : SessionTest() {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        bge.getShoe().stackTheDeckFromList(soft13vs4())
        bge.exec(CMD_DEAL)
        Assertions.assertEquals(CMD_DOUBLE, bge.getCycle().getRecommendedAction())
        bge.exec(CMD_SPLIT)
        Assertions.assertEquals(
            "Can't split cards that don't have the same face",
            bge.dealerMessage
        )
        bge.exec(CMD_STAND)
        Assertions.assertEquals("soft 2  3  4  5  6  7  8  9  T  A ", bge.mistakeHeader)
        Assertions.assertEquals(" 13  H  H  H* Dh Dh H  H  H  H  H ", bge.mistakeMessage)
        Assertions.assertEquals(CMD_STAND, bge.lastActionSnapshot?.getAction() ?: "")
    }
}
