package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import com.panopset.compat.Zombie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoZombieTest {

    @Test
    fun testZombieStop() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        verifyInitialState(bge.metrics.handCount)
        bge.exec(CMD_SHUFFLE)
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(1000) }
        Zombie.stop()
        synchronized(bge) { bge.waitMillis(100) }
        val priorHandCount: Int = bge.metrics.handCount
        synchronized(bge) { bge.waitMillis(100) }
        verifyPostAutoRun(priorHandCount, bge.metrics.handCount)
        Zombie.resume()
        Assertions.assertTrue(Zombie.isActive)
    }
}
