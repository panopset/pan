package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class HT_Test {
    @Test
    fun testFlop12() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {})
        verifyRecommendedActions(
            bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND),
            stack12comp10_2_v_4()
        )
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {})
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_STAND), stack12comp8_4_v_4())
    }

    @Test
    fun testHitTo12() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {})
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), stack12comp3_2_7_v_4())
    }
}
