package com.panopset.compat

import java.io.File
import java.io.IOException


class GlobalProperties(val panop: Panop) {
    /**
     *
     * Some things, like a font size, should persist for all applications.
     *
     */
    val pmf = PersistentMapFile(panop, File(HiddenFolder.getFullPathRelativeTo("global.properties")))

    private fun saveToFile() {
        pmf.flush()
    }

    private fun putValue(key: String, value: String) {
        pmf.put(key, value)
    }

    fun globalPropsFlush(panop: Panop) {
        try {
            saveToFile()
        } catch (ex: IOException) {
            Logop.errorEx(panop, ex)
        }
    }

    fun globalPropsPut(panop: Panop, key: String, value: String) {
        try {
            putValue(key, value)
        } catch (ex: IOException) {
            Logop.errorEx(panop, ex)
        }
    }

    fun globalPropsGet(panop: Panop, key: String?): String {
        return try {
            if (key == null) {
                return ""
            }
            pmf[key]
        } catch (e: IOException) {
            Logop.warn(panop, String.format("%s not found, %s", key, e.message))
            ""
        }
    }
}
