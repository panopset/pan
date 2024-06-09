package com.panopset.compat

import java.io.File

object Logz {

    var logzDsiplayer: LogDisplayer = LogzDisplayerCMD

    fun errorEx(throwable: Throwable) {
        logzDsiplayer.errorEx(throwable)
    }
    fun errorMsg(msg: String, file: File) {
        logzDsiplayer.errorMsg(msg, file)
    }
    fun errorMsg(file: File, throwable: Throwable) {
        logzDsiplayer.errorMsg(file, throwable)
    }
    fun errorMsg(msg: String, throwable: Throwable) {
        logzDsiplayer.errorMsg(msg, throwable)
    }
    fun errorMsg(msg: String) {
        logzDsiplayer.errorMsg(msg)
    }

    fun info(msg: String) {
        logzDsiplayer.dspmsg(msg)
    }

    fun debug(msg: String) {
        logzDsiplayer.debug(msg)
    }

    fun dspmsg(msg: String) {
        logzDsiplayer.dspmsg(msg)
    }
    fun warn(msg: String) {
        logzDsiplayer.warn(msg)
    }

    fun clear() {
        logzDsiplayer.clear()
    }

    fun getPriorMessage(): String {
        return logzDsiplayer.getPriorMessage()
    }

    fun green(msg: String) {
        logzDsiplayer.green(msg)
    }
}
