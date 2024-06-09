package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackCard
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.Card
import com.panopset.blackjackEngine.CardDefinition
import org.junit.jupiter.api.Assertions

fun verifyRecommendedActions(
    bge: BlackjackGameEngine, expectedActions: Array<String>,
    stackedDeckForTesting: List<Card>
) {
    ScenarioVerifier().verifyRecommendedActions(bge, expectedActions, stackedDeckForTesting)
}

fun performDeviantActions(
    bge: BlackjackGameEngine, expectedActions: Array<String>,
    stackedDeckForTesting: List<Card>
) {
    ScenarioVerifier().performDeviantActions(bge, expectedActions, stackedDeckForTesting)
}

@SafeVarargs
fun verifyRecommendedActionsFastDeal(
    bge: BlackjackGameEngine, expectedActions: Array<String>,
    vararg stackedDeckForTesting: List<Card>
) {
    ScenarioVerifier().verifyRecommendedActionsFastDeal(bge, expectedActions, *stackedDeckForTesting)
}

fun verifyPlayerCards(expected: Array<CardDefinition>, cards: List<BlackjackCard>) {
    Assertions.assertEquals(expected.size, cards.size)
    for ((i, blackjackCard) in cards.withIndex()) {
        Assertions.assertEquals(expected[i], blackjackCard.card.cardDefinition)
    }
}
