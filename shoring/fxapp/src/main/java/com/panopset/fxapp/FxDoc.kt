package com.panopset.fxapp

import com.panopset.compat.*
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File

class FxDoc : Anchor {
    val stage: Stage
    lateinit var menuBarStatusMessage: TextField
    lateinit var scene: Scene

    constructor(panApplication: PanApplication, stage: Stage, file: File) : super(panApplication, file) {
        this.stage = stage
    }

    constructor(panApplication: PanApplication, stage: Stage) : super(panApplication) {
        this.stage = stage
    }

    override fun updateTitle() {
        stage.title = createWindowTitle()
        //setLogLis()
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
//    fun setLogLis() {
//        Logop.setLogListener(object: LogListener {
//            override fun log(logEntry: LogEntry) {
//                FontManagerFX.setMenubarLogRecord(
//                    logEntry,
//                    menuBarStatusMessage
//                )
//            }
//        })
//    }
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