package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import com.panopset.blackjackEngine.HandPlayer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MessagesTest {
    @Test
    fun test() {
        bge.exec(CMD_RESET)
        Assertions.assertEquals("Reset", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertEquals("Please select L=Deal", bge.dealerMessage)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), dealerBlackjack())
        bge.exec(CMD_HIT)
        Assertions.assertEquals("Please select L=Deal", bge.dealerMessage)
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND),
            eightVersusFive()
        )
        bge.exec(CMD_HIT)
        Assertions.assertEquals("Please select L=Deal", bge.dealerMessage)
        bge.exec(CMD_DOUBLE)
        Assertions.assertEquals("Please select L=Deal", bge.dealerMessage)
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        ScenarioVerifier.verifyAndDoRecommendedAction(bge, CMD_DEAL, 0)
        ScenarioVerifier.verifyAndDoRecommendedAction(bge, CMD_HIT, 1)
        Assertions.assertEquals(0, bge.metrics.mistakeCount)
        Assertions.assertFalse(bge.getCycle().players[0].hands[0].isDoubleDowned)
        bge.exec(CMD_DEAL)
        Assertions.assertEquals("Hand is still active.", bge.dealerMessage)
        bge.exec(CMD_DOUBLE)
        Assertions.assertEquals("Double not possible here", bge.dealerMessage)
        bge.exec(CMD_STAND)
        Assertions.assertEquals(
            "Won",
            bge.getCycle().players[0].hands[0].message
        )
        Assertions.assertEquals(1, bge.metrics.mistakeCount)
        bge.getShoe().stackTheDeckFromList(playerBlackjack())
        bge.exec(CMD_DEAL)
        Assertions.assertEquals(
            "Blackjack",
            bge.getCycle().players[0].hands[0].message
        )
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        Assertions.assertEquals(1, bge.metrics.mistakeCount)
        Assertions.assertEquals(1, bge.metrics.handsSinceLastMistake)
        bge.exec(CMD_DEAL)
        bge.exec(CMD_STAND)
        Assertions.assertEquals(2, bge.metrics.mistakeCount)
        Assertions.assertEquals(0, bge.metrics.handsSinceLastMistake)
        Assertions.assertEquals(3, bge.metrics.getHandsSinceLastMistakeRecord())
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        bge.exec(CMD_DEAL)
        bge.exec(CMD_HIT)
        Assertions.assertEquals(2, bge.metrics.mistakeCount)
        bge.exec(CMD_STAND)
        Assertions.assertEquals(3, bge.metrics.mistakeCount)
    }

    @Test
    fun testHandPlayer() {
        val hp = HandPlayer(Wager(bge.getNextBet()))
        Assertions.assertEquals("Please select deal (L).", hp.message)
    }

    @Test
    fun testDoubleDown() {
        bge.getShoe().stackTheDeckFromList(doubleDown())
        Assertions.assertTrue(bge.getShoe().isTheDeckStacked())
        Assertions.assertFalse(bge.getCycle().players[0].hands[0].isInitialDeal)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.getCycle().getActivePlayer()!!.activeHand!!.isInitialDeal)
        Assertions.assertTrue(bge.getCycle().getActivePlayer()!!.activeHand!!.canDouble(bge.config.isDoubleAfterSplitAllowed))
        Assertions.assertTrue(bge.getCycle().dealer.upCard.card.isShowing)
        Assertions.assertFalse(bge.getCycle().dealer.downCard.card.isShowing)
        bge.exec(CMD_DOUBLE)
        Assertions.assertTrue(bge.getCycle().dealer.downCard.card.isShowing)
    }

    @Test
    fun testNoSplitAfterHit() {
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        bge.exec(CMD_DEAL)
        bge.exec(CMD_HIT)
        bge.exec(CMD_SPLIT)
        Assertions.assertEquals("Can't split a hit hand", bge.dealerMessage)
    }

    companion object {
        var bge: BlackjackGameEngine = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
    }
}
