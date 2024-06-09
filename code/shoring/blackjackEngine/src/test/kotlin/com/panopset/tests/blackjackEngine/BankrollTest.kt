package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BankrollTest {

    private var bankroll = Bankroll()
    @Test
    fun test() {
        Assertions.assertEquals("$5.95", Stringop.getDollarString(595))
        bankroll.subtract(1000)
        Assertions.assertEquals(29000, bankroll.getChips())
        bankroll.subtract(30000)
        Assertions.assertEquals(29000, bankroll.getChips())
        Assertions.assertEquals(2, bankroll.reloadCount)
        Assertions.assertEquals(29000, bankroll.getChips())
        bankroll.add(5000)
        Assertions.assertEquals(34000, bankroll.getChips())
        bankroll.add(500000)
        Assertions.assertEquals(2, bankroll.reloadCount)
        bankroll.subtract(474000)
        Assertions.assertEquals(60000, bankroll.getChips())
        bankroll.reset()
        Assertions.assertEquals(30000, bankroll.getChips())
    }
}
