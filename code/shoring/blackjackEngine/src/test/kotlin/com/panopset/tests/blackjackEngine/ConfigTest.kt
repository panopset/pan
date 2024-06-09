package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackConfiguration
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConfigTest {
    @Test
    fun test() {
        val config: BlackjackConfiguration = BlackjackConfigDefault()
        Assertions.assertTrue(config.isShowCount)
        config.toggleShowCount()
        Assertions.assertFalse(config.isShowCount)
        config.toggleShowCount()
        Assertions.assertTrue(config.isShowCount)
        Assertions.assertEquals(5, config.betIncrementInWholeDollars)
        Assertions.assertEquals(20, config.largeBetInWholeDollars)
    }

    /**
     * We want to be able to stack the deck from the configuration, in case a front end wants to give
     * that option to the users.
     */
    @Test
    fun deckStackerTest() {
        val config: BlackjackConfiguration = object : BlackjackConfigDefault() {
        }
        val bge = BlackjackGameEngine(config)
        bge.getShoe().shoe.stackedDeck = "five_of_clubs\njack_of_hearts\nqueen of clubs\nten_of_hearts\nsix_of_clubs"
        bge.exec(CMD_SHUFFLE)
        // Make sure the player knows there's a stacked deck.
        Assertions.assertEquals("Shuffled and stacked deck for debugging", bge.dealerMessage)
        Assertions.assertTrue(bge.getShoe().isTheDeckStacked())
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.getShoe().isTheDeckStacked())
        bge.exec(CMD_SHUFFLE)
        Assertions.assertEquals("Hand is still active.", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        bge.exec(CMD_SHUFFLE)
        Assertions.assertEquals("Hand is still active.", bge.dealerMessage)
        bge.exec(CMD_STAND)
        bge.exec(CMD_SHUFFLE)
        Assertions.assertEquals("Shuffled and stacked deck for debugging", bge.dealerMessage)
    }
}
