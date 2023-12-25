package com.panopset.desk.games.bj

import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.createPanTabPane
import javafx.scene.control.Tab

class TabConfig {

    fun createTab(ctls: BlackjackFxControls): Tab {
        val rtn = FontManagerFX.registerTab(Tab("Configuration"))
        val tabPane = createPanTabPane(ctls.fxDoc, "bjConfigTabPane")
        tabPane.tabs.add(TabConfigRules().createTab(ctls))
        tabPane.tabs.add(TabConfigCounting().createTab(ctls))
        tabPane.tabs.add(TabConfigBasicStrategy().createTab(ctls))
        rtn.content = tabPane
        return rtn
    }
}
