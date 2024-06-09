package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SurrenderScenarios {
    @Test
    fun testSurrender16vs9() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val isLateSurrenderAllowed: Boolean
                get() = true
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_SURRENDER), surrender16vs9())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        bge.getShoe().stackTheDeckFromList(surrender16vs9())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not allowed in this casino", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testSurrenderFailHitTo16vs10() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
            override val isLateSurrenderAllowed: Boolean
                get() = true
        })
        bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_HIT)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not possible here", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_HIT)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not allowed in this casino", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }
}
