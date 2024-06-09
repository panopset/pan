package com.panopset.compat

import java.io.File

object LogzDisplayerCMD : LogDisplayer {
    override fun dspmsg(msg: String) {
        dspmsglg(msg)
    }

    override fun warn(msg: String) {
        warnlg(msg)
    }

    override fun debug(msg: String) {
        debuglg(msg)
    }

    override fun errorMsg(msg: String, throwable: Throwable) {
        errorMsglg(msg, throwable)
    }

    override fun errorMsg(msg: String, file: File) {
        errorMsglg(msg, file)
    }

    override fun errorMsg(msg: String) {
        errorMsglg(msg)
    }

    override fun errorMsg(file: File, throwable: Throwable) {
        errorMsglg(file, throwable)
    }

    override fun errorEx(throwable: Throwable) {
        errorExlg(throwable)
    }

    override fun getPriorMessage(): String {
        return stacklg.peek().message
    }

    override fun green(msg: String) {
        dspmsg(msg)
    }

    override fun clear() {
        stacklg.clear()
    }
}
