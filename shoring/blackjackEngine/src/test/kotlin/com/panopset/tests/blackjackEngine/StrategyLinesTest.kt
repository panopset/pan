package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StrategyLinesTest {
    @Test
    fun test() {
        val s = Strategy(BlackjackConfigDefault())
        val handPlayer = HandPlayer(Wager(500))
        Assertions.assertEquals(handPlayer.wager.liveValue, 500)
        handPlayer.dealCard(BlackjackCard(CardDefinition.TEN_OF_DIAMONDS))
        handPlayer.dealCard(BlackjackCard(CardDefinition.SIX_OF_CLUBS))
        val ra = s.getRecommendation(
            Situation(BlackjackCard(CardDefinition.ACE_OF_CLUBS), handPlayer)
        )
        Assertions.assertEquals(CMD_HIT, ra)
        Assertions.assertEquals(
            "soft 2  3  4  5  6  7  8  9  T  A ",
            s.getHeaderFor(StratCat.SOFT)
        )
        Assertions.assertEquals(
            "split2  3  4  5  6  7  8  9  T  A ",
            s.getHeaderFor(StratCat.SPLIT)
        )
        Assertions.assertEquals(
            "hard 2  3  4  5  6  7  8  9  T  A ",
            s.getHeaderFor(StratCat.HARD)
        )
    }

    @Test
    fun testSoft13v4() {
        var bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft13vs4())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), soft13vs4())
        bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 1
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), soft13vs4())
    }

    @Test
    fun testSoft13vs4afterSplitAces() {
        val bge = BlackjackGameEngine(object : BlackjackConfigDefault() {
            override val decks: Int
                get() = 6
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_SPLIT), soft13vs4afterSplitAces())
    }
}
