package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FastDealTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isFastDeal: Boolean
                get() = true
        })
        verifyRecommendedActionsFastDeal(
            bge, arrayOf(CMD_DEAL, CMD_DOUBLE), dealerBlackjack(),
            dealerSoft17_0(), doubleDown()
        )
    }

    @Test
    fun testMistake() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isFastDeal: Boolean
                get() = true
        })
        bge.getShoe().stackTheDeckFromList(stackSplit6_v_2())
        bge.exec(CMD_DEAL)
        bge.exec(CMD_DOUBLE)
        Assertions.assertEquals(1, bge.metrics.mistakeCount)
    }

    @Test
    @Throws(Exception::class)
    fun testAutomatic() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isFastDeal: Boolean
                get() = true
        })
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(100) }
        bge.exec(CMD_AUTO)
        Assertions.assertEquals(0, bge.metrics.mistakeCount)
    }
}
