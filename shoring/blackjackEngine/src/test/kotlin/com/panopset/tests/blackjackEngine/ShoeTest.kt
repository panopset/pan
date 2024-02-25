package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.DeckPile.reset
import com.panopset.blackjackEngine.Shoe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ShoeTest {
    @Test
    fun testShuffle() {
        reset()
        val shoe = Shoe(5)
        Assertions.assertFalse(shoe.isTheDeckStacked())
        Assertions.assertEquals(5, shoe.numberOfDecks)
        val card = shoe.deal()
        Assertions.assertTrue(card.isBlue)
        Assertions.assertNotNull(card)
        Assertions.assertTrue(shoe.isBlue)
        Assertions.assertTrue(shoe.deal().isBlue)
        shoe.shuffle()
        Assertions.assertFalse(shoe.isBlue)
        Assertions.assertFalse(shoe.deal().isBlue)
    }

    @Test
    fun testDeal() {
        reset()
        val shoe = Shoe(1)
        Assertions.assertFalse(shoe.isTheDeckStacked())
        Assertions.assertTrue(shoe.isBlue)
        shoe.deal()
        for (i in 0..52) {
            shoe.deal()
        }
        Assertions.assertFalse(shoe.isBlue)
    }
}
