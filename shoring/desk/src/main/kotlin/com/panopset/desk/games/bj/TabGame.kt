package com.panopset.desk.games.bj

import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.createPanFlowPane
import com.panopset.fxapp.createPanHBox
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabGame {

    fun createTab(ctls: BlackjackFxControls): Tab {
        val rtn = FontManagerFX.registerTab(Tab("Game"))
        val bp = BorderPane()

        bp.center = createPanHBox(
            ctls.bgc.felt
        )

        bp.bottom = createPanFlowPane(
            // TODO create the buttons here.
        )

        rtn.content = bp
        return rtn
    }
}
