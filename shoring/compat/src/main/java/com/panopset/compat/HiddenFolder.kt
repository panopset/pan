package com.panopset.compat

import com.panopset.compat.Stringop.FSP
import com.panopset.compat.Stringop.USH

object HiddenFolder {

    private val basePath = USH + FSP + getHiddenFolderName()

    private var rfn = "root"
    private var hfn = "temp_but_should_replace"

        fun getFullPathRelativeTo(relativePathToAppend: String): String {
            return basePath + FSP + relativePathToAppend
        }

        fun getRootLogFileName(): String {
            return rfn
        }

        fun setRootLogFileName(value: String) {
            rfn = value
        }

        fun getHiddenFolderName(): String {
            return hfn
        }

        fun setHiddenFolderName(value: String) {
            hfn = value
        } 
}
