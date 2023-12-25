package com.panopset.desk.utilities

import com.panopset.desk.utilities.fwtabs.*
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.createPanTabPane
import com.panopset.marin.fx.PanopsetBrandedAppTran
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

class Flywheel : PanopsetBrandedAppTran() {

    override fun getApplicationDisplayName(): String {
        return "Flywheel text utilities."
    }

    override fun getDescription(): String {
        return "Text processing utilities (flywheel, hexdump, global replace, list audit.)"
    }

    override fun createDynapane(fxDoc: FxDoc): Pane {
        val b: BorderPane = createStandardMenubarBorderPane(fxDoc)
        b.center = createTabPane(fxDoc)
        return b
    }
    private fun createTabPane(fxDoc: FxDoc): TabPane {
        val rtn = createPanTabPane(fxDoc, "fwMainTabSelected")
        rtn.tabs.add(TabFlywheel().createTab(fxDoc))
        rtn.tabs.add(TabGlobalReplace().createTab(fxDoc))
        rtn.tabs.add(TabHexDump().createTab(fxDoc))
        rtn.tabs.add(TabLineRemover().createTab(fxDoc))
        rtn.tabs.add(TabListAudit().createTab(fxDoc))
        rtn.tabs.add(TabStract().createTab(fxDoc))
        rtn.tabs.add(TabGlobalOptions().createTab(fxDoc))
        return rtn
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Flywheel().go()
        }
    }
}
