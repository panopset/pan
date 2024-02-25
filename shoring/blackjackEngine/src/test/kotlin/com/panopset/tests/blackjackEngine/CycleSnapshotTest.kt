package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.CardDefinition
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CycleSnapshotTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {})
        var cs = bge.getCurrentSnapshot()
        Assertions.assertEquals(30000, cs.getChips())
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        bge.exec(CMD_DEAL)
        cs = bge.getCurrentSnapshot()
        Assertions.assertEquals(29500, cs.getChips())
        Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.suit, cs.getDealer().upCard.card.suit)
        Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.face, cs.getDealer().upCard.card.face)
        Assertions.assertEquals(1, cs.getPlayers().size)
        cs = bge.getCurrentSnapshot()
        Assertions.assertEquals(1, cs.getPlayers().size)
        bge.exec(CMD_DOUBLE)
        cs = bge.getCurrentSnapshot()
        Assertions.assertEquals("hard 2  3  4  5  6  7  8  9  T  A ", cs.getMistakeHeader())
        Assertions.assertEquals("  8  H  H  H  H* H* H  H  H  H  H ", cs.getMistakeMessage())
        bge.exec(CMD_SPLIT)
        cs = bge.getCurrentSnapshot()
        Assertions.assertEquals("Please select L=Deal", cs.getDealerMessage())
        Assertions.assertEquals("Stake: $10.00", cs.getGameStatusVertical()[0])
        Assertions.assertEquals("Chips: $310.00", cs.getGameStatusVertical()[1])
        Assertions.assertEquals(
            " Stake: $10.00 Chips: $310.00 Score: 0 (1) Hi-Lo: 1",
            cs.getGameStatusHorizontal()
        )
        Assertions.assertEquals("reloads: 1", cs.getStatusChipsVertical()[0])
        Assertions.assertEquals("  reloads: 1  Chips: $310.00  Next bet: $5.00", cs.getStatusChipsHorizontal())
        Assertions.assertFalse(cs.isPainted)
        cs.setPainted()
        Assertions.assertTrue(cs.isPainted)
    }
}
