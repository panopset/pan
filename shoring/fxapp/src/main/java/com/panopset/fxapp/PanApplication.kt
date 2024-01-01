package com.panopset.fxapp

import com.panopset.compat.Panop
import java.util.*

interface PanApplication {

    fun getPanop(): Panop
    fun getCompanyName(): String
    fun getApplicationDisplayName(): String
    fun getDescription(): String
    open fun doAfterShow(fxDoc: FxDoc) {

    }

    val applicationShortName: String
        get() = this.javaClass.name.lowercase(Locale.getDefault())
    val filesKey: String
        get() = applicationShortName + "_files"
}
