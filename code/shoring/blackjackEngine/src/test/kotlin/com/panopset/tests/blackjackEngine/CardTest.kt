package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.blackjackEngine.CardDefinition.Companion.find
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun testFaces() {
        Assertions.assertEquals("A", Face.ACE.key)
        Assertions.assertEquals("2", Face.TWO.key)
        Assertions.assertEquals("3", Face.THREE.key)
        Assertions.assertEquals("4", Face.FOUR.key)
        Assertions.assertEquals("5", Face.FIVE.key)
        Assertions.assertEquals("6", Face.SIX.key)
        Assertions.assertEquals("7", Face.SEVEN.key)
        Assertions.assertEquals("8", Face.EIGHT.key)
        Assertions.assertEquals("9", Face.NINE.key)
        Assertions.assertEquals("T", Face.TEN.key)
        Assertions.assertEquals("J", Face.JACK.key)
        Assertions.assertEquals("Q", Face.QUEEN.key)
        Assertions.assertEquals("K", Face.KING.key)
    }

    @Test
    fun testSuits() {
        Assertions.assertEquals("H", BlackjackCard(Card(CardDefinition.ACE_OF_HEARTS)).card.suit.key)
        Assertions.assertEquals("S", BlackjackCard(Card(CardDefinition.ACE_OF_SPADES)).card.suit.key)
        Assertions.assertEquals("D", BlackjackCard(Card(CardDefinition.ACE_OF_DIAMONDS)).card.suit.key)
        Assertions.assertEquals("C", BlackjackCard(Card(CardDefinition.ACE_OF_CLUBS)).card.suit.key)
        Assertions.assertEquals("H", CardDefinition.ACE_OF_HEARTS.suit.key)
        Assertions.assertEquals("S", CardDefinition.ACE_OF_SPADES.suit.key)
        Assertions.assertEquals("D", CardDefinition.ACE_OF_DIAMONDS.suit.key)
        Assertions.assertEquals("C", CardDefinition.ACE_OF_CLUBS.suit.key)
        Assertions.assertEquals("H", Suit.HEART.key)
        Assertions.assertEquals("S", Suit.SPADE.key)
        Assertions.assertEquals("D", Suit.DIAMOND.key)
        Assertions.assertEquals("C", Suit.CLUB.key)
        Assertions.assertEquals("H", CardDefinition.ACE_OF_HEARTS.suit.key)
        Assertions.assertNull(find("foo"))
        Assertions.assertEquals("A", find("ACE_OF_HEARTS")!!.face.key)
        Assertions.assertEquals("A", find(Face.ACE, Suit.HEART)!!.face.key)
    }

    @Test
    fun testClone() {
        val tenOfHearts = BlackjackCard(Card(CardDefinition.TEN_OF_HEARTS), false)
        var clone = BlackjackCard(tenOfHearts)
        Assertions.assertFalse(tenOfHearts.card.isShowing)
        Assertions.assertFalse(clone.card.isShowing)
        tenOfHearts.show()
        Assertions.assertTrue(tenOfHearts.card.isShowing)
        Assertions.assertTrue(clone.card.isShowing)
        val fiveOfDiamonds = BlackjackCard(Card(CardDefinition.FIVE_OF_DIAMONDS))
        clone = BlackjackCard(fiveOfDiamonds)
        Assertions.assertEquals(clone.card.suit.key, Suit.DIAMOND.key)
        Assertions.assertFalse(clone.card.isShowing)
    }
}
