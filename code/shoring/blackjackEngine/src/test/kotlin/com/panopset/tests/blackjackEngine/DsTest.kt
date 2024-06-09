package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DsTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        bge.getShoe().shuffle()
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), soft18v3())
        Assertions.assertEquals(
            "| Stake: $10.00| Chips: $310.00| Score: 1 (1)| Hi-Lo: -1",
            bge.getGameStatus()
        )
        Assertions.assertEquals(
            Stringop.arrayToList(
                arrayOf(
                    "Stake: $10.00",
                    "Chips: $310.00",
                    "Score: 1 (1)",
                    "Hi-Lo: -1"
                )
            ),
            bge.getGameStatusVertical()
        )
        Assertions.assertEquals(
            " Stake: $10.00 Chips: $310.00 Score: 1 (1) Hi-Lo: -1",
            bge.getGameStatusHorizontal()
        )
        Assertions.assertEquals(
            Stringop.arrayToList(arrayOf("reloads: 1", "Chips: $310.00", "Next bet: $5.00")),
            bge.getStatusChipsVertical()
        )
        Assertions.assertEquals(
            "  reloads: 1  Chips: $310.00  Next bet: $5.00",
            bge.getStatusChipsHorizontal()
        )
        Assertions.assertEquals(
            "  reloads: 1|  Chips: $310.00|  Next bet: $5.00",
            bge.getRawStatusChips()
        )
        Assertions.assertEquals(
            "  reloads: 1|  Chips: $310.00|  Next bet: $5.00",
            bge.getRawStatusChips()
        )
        bge.shuffle()
        bge.getShoe().stackTheDeckFromList(soft18v3())
        bge.shuffle()
        bge.exec(CMD_COUNT)
        Assertions.assertEquals("| Stake: $10.00| Chips: $310.00| Score: 1 (1)", bge.getGameStatus())
    }

    @Test
    fun testDoubleNotAllowed() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), softHitTo18v3())
    }
}
