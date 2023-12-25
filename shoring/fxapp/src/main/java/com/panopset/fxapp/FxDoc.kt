package com.panopset.fxapp

import com.panopset.compat.KEY_WINDOW_DIMS
import com.panopset.compat.combineDelim
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.File

class FxDoc : Anchor {
    val stage: Stage
    lateinit var scene: Scene

    constructor(panApplication: PanApplication, stage: Stage, file: File) : super(panApplication, file) {
        this.stage = stage
    }

    constructor(panApplication: PanApplication, stage: Stage) : super(panApplication) {
        this.stage = stage
    }

    override fun updateTitle() {
        stage.title = createWindowTitle()
    }

    fun closeWindow() {
        saveWindow()
        stage.close()
    }

    fun saveWindow() {
        persistentMapFile.put(
            KEY_WINDOW_DIMS,
            combineDelim("|", stage.x.toString(), stage.y.toString(), stage.width.toString(), stage.height.toString())
        )
        saveDataToFile()
    }
}



/*

        application.doAfterLaunch(this)
        override fun doAfterLaunch(fxDoc: FxDoc) {
            super.doAfterLaunch(fxDoc)
            val floor = getFloor(fxDoc)
            Platform.runLater {
                updateEnvironmentWithProps(floor, panCheckboxMenu.currentSelection)
            }
        }



 */