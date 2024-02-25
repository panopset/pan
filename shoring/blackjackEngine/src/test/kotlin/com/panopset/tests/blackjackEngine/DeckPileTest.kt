package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.DeckPile.pull
import com.panopset.blackjackEngine.DeckPile.reset
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DeckPileTest {
    @Test
    fun test() {
        reset()
        Assertions.assertTrue(pull())
        Assertions.assertFalse(pull())
        Assertions.assertTrue(pull())
    }
}
