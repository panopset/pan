package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.compat.Stringop
import com.panopset.compat.Zombie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        testAutoRun(bge)
    }

    @Test
    fun testFromVariations() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val isBasicStrategyVariationsOnly: Boolean
                get() = true
        })
        bge.exec(CMD_AUTO)
        Assertions.assertEquals(
            "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically.",
            bge.dealerMessage
        )
    }

    @Test
    fun targetStakeTest() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
            override val targetStakeInWholeDollars: Int
                get() = 1000
            override val strategicVeryNegativeCount: Int
                get() = 4
            override val strategicVeryPositiveCount: Int
                get() = 4
            override val largeBetInWholeDollars: Int
                get() = 200
        })
        bge.exec(CMD_AUTO)
        synchronized(bge) {
            var paintedSnapshot: CycleSnapshot? = null
            while (bge.isAutomaticRunning() && Zombie.isActive) {
                bge.waitMillis(200)
                val cycleSnapshot = bge.getCurrentSnapshot()
                if (cycleSnapshot != paintedSnapshot) {
                    paintedSnapshot = cycleSnapshot
                    println(
                        String.format(
                            "Stake: %s, Chips: %s Hands: %d ",
                            Stringop.getDollarString(cycleSnapshot.getStakeIncludingHands()),
                            Stringop.getDollarString(cycleSnapshot.getChips()), cycleSnapshot.getMetrics().handCount
                        )
                    )
                    Assertions.assertEquals(500, cycleSnapshot.getNextBet())
                }
            }
        }
        Assertions.assertTrue(bge.bankroll.getChips() > 99999)
    }

    private fun testAutoRun(bge: BlackjackGameEngine) {
        verifyInitialState(bge.metrics.handCount)
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(1000) }
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(100) }
        val priorHandCount: Int = bge.metrics.handCount
        synchronized(bge) { bge.waitMillis(100) }
        verifyPostAutoRun(priorHandCount, bge.metrics.handCount)
    }
}
