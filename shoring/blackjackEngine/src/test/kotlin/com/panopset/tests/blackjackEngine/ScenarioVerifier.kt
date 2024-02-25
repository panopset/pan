package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.Card
import com.panopset.compat.Stringop.arrayToList
import org.junit.jupiter.api.Assertions

class ScenarioVerifier {
    fun performDeviantActions(
        bge: BlackjackGameEngine, expectedActions: Array<String>,
        stackedDeckForTesting: List<Card>
    ) {
        bge.getShoe().stackTheDeckFromList(stackedDeckForTesting)
        var i = 0
        for (action in expectedActions) {
            doTheNextAction(bge, action, i++, false)
        }
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    fun verifyRecommendedActions(
        bge: BlackjackGameEngine, expectedActions: Array<String>,
        stackedDeckForTesting: List<Card>
    ) {
        bge.getShoe().stackTheDeckFromList(stackedDeckForTesting)
        var i = 0
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("***************Verify and execute:  " + arrayToList(expectedActions))
        for (action in expectedActions) {
            println("***>$action")
            verifyAndDoRecommendedAction(bge, action, i++)
            println("<***$action")
        }
        println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        if (bge.getCycle().isActive) {
            println("ERROR - Split aces did not close action.")
        }
        Assertions.assertFalse(bge.getCycle().isActive)
    }

    @SafeVarargs
    fun verifyRecommendedActionsFastDeal(
        bge: BlackjackGameEngine, expectedActions: Array<String>,
        vararg stackedDecksForTesting: List<Card>
    ) {
        for (stackedDeckForTesting in stackedDecksForTesting) {
            bge.getShoe().stackTheDeckFromList(stackedDeckForTesting)
        }
        var i = 0
        for (action in expectedActions) {
            verifyAndDoRecommendedAction(bge, action, i++)
        }
    }

    companion object {
        fun verifyAndDoRecommendedAction(bge: BlackjackGameEngine, action: String?, i: Int) {
            doTheNextAction(bge, action, i, true)
        }

        fun doTheNextAction(bge: BlackjackGameEngine, action: String?, i: Int, verify: Boolean) {
            val ra = bge.getCycle().getRecommendedAction()
            println(showTheCycleSituation(bge, action, i, "before action"))
            if (verify) {
                Assertions.assertEquals(ra, action)
            }
            bge.exec(action!!)
            println(showTheCycleSituation(bge, action, i, "after action"))
            println(String.format("Strategy line for next action is %s.", bge.getCycle().getStrategyLine()))
        }

        fun showTheCycleSituation(bge: BlackjackGameEngine, action: String?, i: Int, msg: String?): String {
            // if (!bge.getCycle().isDealt() || bge.getCycle().getActivePlayer() == null) {
            return String.format(
                "%s %d %s: %s\n stacked deck remaining: %s \nactive situation:%s", msg, i, action,
                bge.getCycle(), bge.getShoe().dumpStack(), bge.getCycle().activeSituation
            )
        }
    }
}
