package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.DeckPile.Companion.pull
import com.panopset.blackjackEngine.DeckPile.Companion.reset
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
