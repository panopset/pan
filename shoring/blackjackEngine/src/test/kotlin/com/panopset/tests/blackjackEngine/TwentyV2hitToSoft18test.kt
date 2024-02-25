package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class TwentyV2hitToSoft18test {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_STAND), twentyV2hitToSoft18())
    }
}
