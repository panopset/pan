package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun test() {
        val player = Player(Wager(500))
        player.activeHand!!.dealCard(BlackjackCard(CardDefinition.ACE_OF_CLUBS))
        player.activeHand!!.dealCard(BlackjackCard(CardDefinition.TEN_OF_SPADES))
        Assertions.assertEquals("Player #0: cards:[ACE_OF_CLUBS, TEN_OF_SPADES] value: 21 ", player.toString())
        Assertions.assertEquals(1, player.hands.size)
    }
}
