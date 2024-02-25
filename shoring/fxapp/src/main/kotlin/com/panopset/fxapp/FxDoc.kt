package com.panopset.fxapp

import com.panopset.compat.*
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File

class FxDoc : Anchor, LogDsiplayer {
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

    override fun dspmsg(msg: String) {
        menuBarStatusMessage.text = msg
    }

    override fun warn(msg: String) {
        dspmsg(msg)
    }

    override fun clear() {
        dspmsg("")
    }

    override fun getPriorMessage(): String {
        return menuBarStatusMessage.text
    }

    override fun green(msg: String) {
        dspmsg(msg)
    }

    override fun errorMsg(msg: String, throwable: Throwable) {
        dspmsg(msg)
    }

    override fun errorMsg(msg: String, file: File) {
        dspmsg(msg)
    }

    override fun errorMsg(msg: String) {
        dspmsg(msg)
    }

    override fun errorMsg(file: File, throwable: Throwable) {
        //dspmsg(msg)
    }

    override fun errorEx(throwable: Throwable) {
        dspmsg(throwable.message ?: "unfathomable")
    }

    override fun debug(msg: String) {
        dspmsg(msg)
    }
}
