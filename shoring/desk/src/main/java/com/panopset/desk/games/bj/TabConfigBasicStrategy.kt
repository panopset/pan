package com.panopset.desk.games.bj

import com.panopset.blackjackEngine.DefaultResources
import com.panopset.fxapp.*
import javafx.application.Platform
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabConfigBasicStrategy {

    fun createTab(ctls: BlackjackFxControls): Tab {
        val rtn = FontManagerFX.registerTab(Tab("Basic Strategy"))
        val bp = BorderPane()
        bp.top = createPanFlowPane(
            createPanButton({userReset(ctls)}, "Reset", false, "Reset to default values."),
            createPanLabel("  Basic Strategy.  Game must be reset (R key on the game) or restarted to pick up changes:")
        )
        bp.center = createPanScrollPane(ctls.taBasicStrategy)
        rtn.content = bp
        userReset(ctls)
        return rtn
    }

    private fun userReset(ctls: BlackjackFxControls) {
        Platform.runLater {
            val dft = DefaultResources.defaultBasicStrategy
            ctls.taBasicStrategy.text = dft
        }
    }
}
