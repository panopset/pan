package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

open class SessionTest {
    var bge = BlackjackGameEngine(BlackjackConfigDefault())
    @Test
    fun testBlackjack() {
        bge.getShoe().stackTheDeckFromArray(BLACKJACK_WWCD)
        bge.shuffle()
        Assertions.assertEquals("Shuffled and stacked deck for debugging", bge.dealerMessage)
        doRecommendedAction(CMD_DEAL)
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(750L, bge.getTotalValue())
        bge.getShoe().stackTheDeckFromArray(SPLITACES_DLRBLACKJACK)
        doRecommendedAction(CMD_DEAL)
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(250L, bge.getTotalValue())
        bge.getShoe().stackTheDeckFromArray(SPLITACES_DLRHIT_TO_21)
        doRecommendedAction(CMD_DEAL)
        Assertions.assertTrue(bge.getCycle().isActive)
        Assertions.assertEquals("p", doRecommendedAction(CMD_SPLIT))
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(250L, bge.getTotalValue())
        Assertions.assertEquals(
            "J",
            bge.getCycle().activeSituation.dealerUpCard!!.card.face.key
        )
        val stackRawData = "six of clubs\njack of spades\nnine of hearts\nace of diamonds"
        bge.getShoe().stackTheDeckFromEOLseparatedText(stackRawData)
        doRecommendedAction(CMD_DEAL)
        Assertions.assertFalse(bge.getCycle().isActive)
    }

    @Test
    fun splitAcesPlayerBlackjackFirstHand() {
        bge.getShoe().stackTheDeckFromArray(SPLITACES_FIRST_HAND_PLAYER_BLACKJACK)
        bge.shuffle()
        doRecommendedAction(CMD_DEAL)
        Assertions.assertTrue(bge.getCycle().isActive)
        doRecommendedAction(CMD_SPLIT)
        Assertions.assertTrue(bge.getCycle().players[0].hands[0].isDone())
        Assertions.assertFalse(bge.getCycle().players[0].hands[1].isDone())
    }

    private fun doRecommendedAction(action: String): String {
        val ra = bge.getCycle().getRecommendedAction()
        Assertions.assertEquals(ra, action)
        bge.exec(action)
        return ra
    }
}
