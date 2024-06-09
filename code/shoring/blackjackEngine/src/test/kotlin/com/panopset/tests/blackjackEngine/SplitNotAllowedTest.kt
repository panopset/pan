package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.CardDefinition
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SplitNotAllowedTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        bge.exec(CMD_RESET)
        bge.getShoe().stackTheDeckFromList(splitNotAllowedTest())
        bge.exec(CMD_DEAL)
        bge.exec(CMD_SPLIT)
        verifyPlayerCards(
            arrayOf(CardDefinition.TWO_OF_SPADES, CardDefinition.EIGHT_OF_DIAMONDS),
            bge.getCurrentSnapshot().getPlayers()[0].hands[0].cards
        )
        Assertions.assertEquals(
            CardDefinition.TWO_OF_SPADES.face,
            bge.getCurrentSnapshot().getPlayers()[0].activeHand!!.cards[0].card.face
        )
        Assertions.assertEquals(
            CardDefinition.TWO_OF_SPADES.suit,
            bge.getCurrentSnapshot().getPlayers()[0].activeHand!!.cards[0].card.suit
        )
        Assertions.assertEquals(
            CardDefinition.EIGHT_OF_DIAMONDS.face,
            bge.getCurrentSnapshot().getPlayers()[0].activeHand!!.cards[1].card.face
        )
        Assertions.assertEquals(
            CardDefinition.EIGHT_OF_DIAMONDS.suit,
            bge.getCurrentSnapshot().getPlayers()[0].activeHand!!.cards[1].card.suit
        )
        Assertions.assertEquals(
            "Can't split cards that don't have the same face",
            bge.dealerMessage
        )
    }
}
