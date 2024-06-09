package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class HdTest {
    @Test
    fun testSingleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testDoubleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 2
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testTripleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 3
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testDoubleDeckDealerStandsSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 2
            override val isDealerHitSoft17: Boolean
                get() = false
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testTripleDeckDealerStandsSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 3
            override val isDealerHitSoft17: Boolean
                get() = false
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), stackHard11_v_A())
    }

    @Test
    fun testSingleDeckHardHitTo11() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND), stackHardHitTo11_v_A())
    }
}
