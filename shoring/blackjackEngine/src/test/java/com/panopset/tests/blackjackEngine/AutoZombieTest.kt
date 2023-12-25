package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoZombieTest {

    @Test
    @Throws(Exception::class)
    fun testZombieStop() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        var priorHandCount = 0
        verifyInitialState(bge.metrics.handCount)
        bge.exec(CMD_SHUFFLE)
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(1000) }
        bge.stop()
        synchronized(bge) { bge.waitMillis(100) }
        priorHandCount = bge.metrics.handCount
        synchronized(bge) { bge.waitMillis(100) }
        verifyPostAutoRun(priorHandCount, bge.metrics.handCount)
        bge.resume()
        Assertions.assertTrue(bge.isActive())
    }
}
