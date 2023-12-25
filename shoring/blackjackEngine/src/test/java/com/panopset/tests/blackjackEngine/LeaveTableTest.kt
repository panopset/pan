package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LeaveTableTest {

    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val strategicVeryNegativeCount = -3
        })
        bge.shuffle()
        Assertions.assertFalse(bge.isCountVeryNegative())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL), lowCountTest())
        Assertions.assertTrue(bge.isCountVeryNegative())
        Assertions.assertFalse(bge.isShuffleFlagOn)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.isShuffleFlagOn)
    }
}
