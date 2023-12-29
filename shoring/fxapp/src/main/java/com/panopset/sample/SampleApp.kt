package com.panopset.sample

import com.panopset.compat.HiddenFolder
import com.panopset.compat.Logop
import com.panopset.fxapp.BrandedApp
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.createPanTabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.control.Tab

class SampleApp: BrandedApp() {

    override fun updateVersionMessage() {
        Logop.info("Green log entry.")
        Logop.debug("Yellow log entry.")
        Logop.warn("Orange log entry.")
        Logop.errorMsg("Red log entry.")
    }

    override fun createDynapane(fxDoc: FxDoc): Pane {
        val bp: BorderPane = createStandardMenubarBorderPane(fxDoc)
        val tp = createPanTabPane(fxDoc, "sampleBPcenter")
        tp.tabs.add(Tab("SomeTab"))
        bp.center = tp
        return bp
    }

    override fun getApplicationDisplayName(): String {
        return "Sample"
    }

    override fun getDescription(): String {
        return "Sample application."
    }

    override fun getCompanyName(): String {
        return "ACME Anvils corporation"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SampleApp().go()
        }
    }
}
