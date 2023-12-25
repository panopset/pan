package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BustedHstarTest {
    @Test
    fun testSingleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), bustedHstarSingle())
        performDeviantActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT), bustedHstarSingle())
        Assertions.assertEquals(
            "Busted",
            bge.getCycle().players[0].hands[0].message
        )
    }

    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), bustedHstarSingle())
        performDeviantActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT), bustedHstarSingle())
        Assertions.assertEquals(
            "Busted",
            bge.getCycle().players[0].hands[0].message
        )
    }
}
