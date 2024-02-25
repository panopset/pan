package com.panopset.desk.games.bj

import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.createPanHBox
import com.panopset.fxapp.createPanLabel
import com.panopset.fxapp.createPanVBox
import javafx.scene.control.Tab

class TabConfigRules {
    fun createTab(ctls: BlackjackFxControls): Tab {
        val rtn = FontManagerFX.registerTab(Tab("Rules"))
        rtn.content = createPanVBox(
            createPanHBox(
                createPanVBox(
                    ctls.dealerHitsSoft17, ctls.resplitAces, ctls.doubleAfterSplit,
                    createPanHBox(createPanLabel("Decks: "), ctls.cbDecks)
                ),
                createPanVBox(
                    ctls.rule65, ctls.ruleLateSurrender, ctls.ruleVariations,
                    createPanHBox(createPanLabel("Seats: "), ctls.cbSeats)
                ),
                createPanVBox(
                    ctls.ruleShowCount, ctls.ruleEuropeanStyle, ctls.ruleFastDeal,
                    createPanHBox(createPanLabel("Reload: "), ctls.reloadAmount)
                )
            ),
            createPanHBox(
                createPanVBox(
                    ctls.ruleEvenMoney, ctls.betIdeaDoubleAfterBust, ctls.betideaLetItRide,
                    createPanLabel("* 'Bets' strategy simulations only work with one player.\"")
                )
            )
        )
        return rtn
    }
}
