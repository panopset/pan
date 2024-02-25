package com.panopset.desk.games

import com.panopset.desk.games.bj.BlackjackFxControls
import com.panopset.desk.games.bj.TabConfig
import com.panopset.desk.games.bj.TabGame
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.createPanTabPane
import com.panopset.marin.fx.PanopsetBrandedAppTran
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

class Blackjack : PanopsetBrandedAppTran() {
    private lateinit var ctls: BlackjackFxControls

    val BLACKJACK_STAKE_KEY = "blackjackStake"
    override fun createDynapane(fxDoc: FxDoc): Pane {
        ctls = BlackjackFxControls(fxDoc)
        val bp: BorderPane = createStandardMenubarBorderPane(fxDoc)
        bp.center = createTabPane(ctls)
        return bp
    }

    private fun createTabPane(ctls: BlackjackFxControls): TabPane {
        val rtn = createPanTabPane(ctls.fxDoc, "bjMainTabSelected")
        rtn.tabs.add(TabConfig().createTab(ctls))
        rtn.tabs.add(TabGame().createTab(ctls))
        return rtn
    }
    override fun getApplicationDisplayName(): String {
        return "Blackjack"
    }

    override fun getDescription(): String {
        return "Blackjack trainer."
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Blackjack().go()
        }
    }
}
