package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BetAmountStrategy
import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BetAmountStrategyTest {
    @Test
    fun testDoubleAfterBust() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isBetIdeaDoubleAfterBust: Boolean
                get() = true
        })
        bge.setAutomaticOnForTesting()
        var bas = BetAmountStrategy(bge)
        bge.setPriorHandBustedFlag()
        Assertions.assertEquals(1000, bas.adjust())
        bge.clearPriorHandBustedFlag()
        Assertions.assertEquals(500, bas.adjust())
        bge.clearAutomaticForTesting()
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val isBetIdeaLetItRideAfterTwoWins: Boolean
                get() = true
            override val strategicVeryPositiveCount: Int
                get() = Int.MIN_VALUE
            override val largeBetInWholeDollars: Int
                get() = 50
        })
        bge.setAutomaticOnForTesting()
        bas = BetAmountStrategy(bge)
        Assertions.assertEquals(5000, bas.adjust())
        bge.incrementStreak()
        Assertions.assertEquals(5000, bas.adjust())
        bge.incrementStreak()
        Assertions.assertEquals(10000, bas.adjust())
        bge.clearAutomaticForTesting()
    }
}
