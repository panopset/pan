package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.Wager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HandWagerTest {
    @Test
    fun test() {
        var hb = Wager(500)
        hb.initialBet = 500
        hb.initialPayoff = 500
        hb.doubleDown()
        hb.doubledPayoff = 500
        Assertions.assertEquals(2000, hb.liveValue)
        hb = Wager(300)
        hb.initialBet = 500
        Assertions.assertEquals(500, hb.liveValue)
        hb.lost()
        Assertions.assertEquals(0, hb.liveValue)
    }
}
