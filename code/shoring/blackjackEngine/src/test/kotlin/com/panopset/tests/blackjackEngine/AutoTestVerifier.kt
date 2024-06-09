package com.panopset.tests.blackjackEngine

import org.junit.jupiter.api.Assertions

    fun verifyInitialState(handCount: Int) {
        Assertions.assertEquals(0, handCount)
        Assertions.assertFalse(isHandCountOver1000(handCount))
    }

    fun verifyPostAutoRun(priorHandCount: Int, handCount: Int) {
        Assertions.assertTrue(isHandCountOver1000(handCount))
        Assertions.assertEquals(priorHandCount, handCount)
    }

    private fun isHandCountOver1000(handCount: Int): Boolean {
        return handCount > 1000
    }
