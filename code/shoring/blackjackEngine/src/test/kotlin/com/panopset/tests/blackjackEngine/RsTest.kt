package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class RsTest {
    @Test
    fun testSurrenderAllowed() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isLateSurrenderAllowed: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_SURRENDER), stackHard17vA())
    }

    @Test
    fun testSurrenderNotAllowed() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_STAND), stackHard17vAwithDealerHit())
    }
}
