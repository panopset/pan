package com.panopset.fxapp

import com.panopset.compat.*
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File

class FxDoc : Anchor, LogDisplayer {
    val stage: Stage
    lateinit var menuBarStatusMessage: TextField
    lateinit var scene: Scene
    private var closingSaveComplete = false

    constructor(panApplication: PanApplication, stage: Stage, file: File) : super(panApplication, file) {
        this.stage = stage
    }

    constructor(panApplication: PanApplication, stage: Stage) : super(panApplication) {
        this.stage = stage
    }

    public override fun updateTitle() {
        stage.title = createWindowTitle()
    }

    fun closeWindow() {
        saveWindow()
        closingSaveComplete = true
        stage.close()
    }

    fun saveWindow() {
        if (closingSaveComplete) {
            return
        }
        pmf.put(
            KEY_WINDOW_DIMS,
            combineDelim("|", stage.x.toString(), stage.y.toString(), stage.width.toString(), stage.height.toString())
        )
        saveDataToFile()
    }

    override fun dspmsg(msg: String) {
        menuBarStatusMessage.style = "-fx-text-fill: green;"
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
        menuBarStatusMessage.style = "-fx-text-fill: red;"
        menuBarStatusMessage.text = msg
    }

    override fun errorMsg(msg: String, file: File) {
        menuBarStatusMessage.style = "-fx-text-fill: red;"
        menuBarStatusMessage.text = "$msg,\n${file.absolutePath}"
    }

    override fun errorMsg(msg: String) {
        menuBarStatusMessage.style = "-fx-text-fill: red;"
        menuBarStatusMessage.text = msg
    }

    override fun errorMsg(file: File, throwable: Throwable) {
        menuBarStatusMessage.style = "-fx-text-fill: red;"
        menuBarStatusMessage.text = "${throwable.message},\n${file.absolutePath}"
    }

    override fun errorEx(throwable: Throwable) {
        menuBarStatusMessage.style = "-fx-text-fill: red;"
        menuBarStatusMessage.text = "${throwable.message}"
    }

    override fun debug(msg: String) {
        dspmsg(msg)
    }
}
