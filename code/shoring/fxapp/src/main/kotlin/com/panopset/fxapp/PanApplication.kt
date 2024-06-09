package com.panopset.fxapp

import java.util.*

abstract class PanApplication {
    abstract fun getCompanyName(): String
    abstract fun getApplicationDisplayName(): String
    abstract fun getDescription(): String
    open fun doAfterShow(fxDoc: FxDoc) {

    }

    val applicationShortName: String
        get() = this.javaClass.name.lowercase(Locale.getDefault())
    val filesKey: String
        get() = applicationShortName + "_files"

    var id = 0L
    fun getNextUniqueID(): Long {
        return id++
    }
}
