package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScenarioSplitAces {
    @Test
    fun testWithSixDecks() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val isResplitAcesAllowed: Boolean
                get() = true
        })
        Assertions.assertEquals("", bge.mistakeMessage)
        Assertions.assertEquals("", bge.mistakeHeader)
        Assertions.assertEquals("", bge.dealerMessage)
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertEquals(
            "Dealer: []" + Stringop.LINE_SEPARATOR +
                    " Bankroll:Chips: 30000, reloadAmount: 30000, reloadCount: 1", bge.getCycle().toString()
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), doubleDown())
        Assertions.assertTrue(bge.getCycle().players[0].hands[0].isDoubleDowned)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND),
            eightVersusFive()
        )
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_SPLIT),
            resplitAces()
        )
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND),
            doubleAfterSplit()
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testWithSixDecksResplitAcesAllowed() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val isResplitAcesAllowed: Boolean
                get() = true
        })
        verifyRecommendedActions(
            bge, arrayOf(
                CMD_DEAL, CMD_SPLIT, CMD_SPLIT
            ), resplitAces()
        )
    }

    @Test
    fun testWithSingleDeckResplitAcesAllowed() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
            override val isResplitAcesAllowed: Boolean
                get() = true
        })
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), doubleDown())
        Assertions.assertEquals(29000, bge.bankroll.getChips())
        bge.setNextBet(0)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), eightVersusFive())
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        bge.setNextBet(2000)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_SPLIT),
            resplitAces()
        )
        Assertions.assertEquals(36000, bge.bankroll.getChips())
        Assertions.assertEquals(6000, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND),
            doubleAfterSplit()
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testWithDoubleAfterSplitNotAllowed6decks() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isDoubleAfterSplitAllowed: Boolean
                get() = false
            override val isResplitAcesAllowed: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), doubleDown())
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND),
            eightVersusFive()
        )
        verifyRecommendedActions(
            bge, arrayOf(
                CMD_DEAL, CMD_SPLIT, CMD_SPLIT
            ), resplitAces1()
        )
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_STAND, CMD_SPLIT, CMD_STAND, CMD_STAND),
            doubleAfterSplit()
        )
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18vT())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
    }

    @Test
    fun testWithResplitAcesNotAllowed6decks() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        bge.getShoe().stackTheDeckFromList(resplitAces())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        Assertions.assertEquals(
            "cycle:Dealer: [THREE_OF_CLUBS, KING_OF_CLUBS]" + Stringop.LINE_SEPARATOR +
                    "Player 0: Player #0: cards:[ACE_OF_DIAMONDS, ACE_OF_HEARTS] value: 12 " + Stringop.LINE_SEPARATOR +
                    " Bankroll:Chips: 29500, reloadAmount: 30000, reloadCount: 1",
            bge.ct.toString()
        )
        bge.exec(CMD_SPLIT)
        Assertions.assertTrue(bge.getCycle().getRecommendedAction() == "l")
        Assertions.assertEquals(CMD_DEAL, bge.getCycle().getRecommendedAction())
        Assertions.assertFalse(bge.getCycle().isActive)
        bge.exec(CMD_DEAL)
        println(bge.getShoe().dumpStack())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }
}
