package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScenarioTest {

    @Test
    fun testWithSingleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), doubleDown())
        Assertions.assertEquals(29000, bge.bankroll.getChips())
        bge.setNextBet(0)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), eightVersusFive())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        bge.setNextBet(2000)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT),
            resplitAces()
        )
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(34000, bge.bankroll.getChips())
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND),
            doubleAfterSplit()
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testBlackjack() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isBlackjack6to5: Boolean
                get() = false
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), playerBlackjack())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30750, bge.bankroll.getChips())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isBlackjack6to5: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), playerBlackjack())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30600, bge.bankroll.getChips())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), playerAndDealerBlackjack())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30600, bge.bankroll.getChips())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isEuropeanStyle: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), playerAndDealerBlackjack())
        Assertions.assertEquals(29500, bge.bankroll.getChips())
        Assertions.assertEquals(
            "Lost",
            bge.getCycle().players[0].hands[0].message
        )
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testBlackjackVs21total() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val seats: Int
                get() = 2
        })
        bge.exec(CMD_INCREASE)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_STAND),
            playerBlackjackAnd20Vs21total2players()
        )
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30500, bge.bankroll.getChips())
        bge.exec(CMD_DECREASE)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_STAND),
            playerBlackjackAnd20Vs21total2players()
        )
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30750, bge.bankroll.getChips())
    }

    @Test
    fun testDealerStandsOnSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isDealerHitSoft17: Boolean
                get() = false
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), dealerSoft17_0())
        Assertions.assertEquals(
            "Won",
            bge.getCycle().players[0].hands[0].message
        )
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(31000, bge.bankroll.getChips())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(31500, bge.bankroll.getChips())
    }

    @Test
    fun testDealerHitsOnSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {})
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), dealerSoft17_1())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        Assertions.assertEquals(
            "Push",
            bge.getCycle().players[0].hands[0].message
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(30500, bge.bankroll.getChips())
    }

    @Test
    fun testEvenMoney() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isEvenMoneyOnBlackjackVace: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), playerAndDealerBlackjack())
        Assertions.assertEquals(500, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(
            "Even money",
            bge.getCycle().players[0].hands[0].message
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(
            "Lost",
            bge.getCycle().players[0].hands[0].message
        )
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }
}
