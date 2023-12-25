package com.panopset.marin.fx

import com.panopset.compat.Logop.errorMsg
import javafx.scene.image.Image

object ImageMapCache {

    fun find(key: String, path: String?): Image? {
        var rtn = map[key]
        if (rtn == null) {
            rtn = try {
                Image(javaClass.getResource(path ?: "")?.toExternalForm())
            } catch (iae: IllegalArgumentException) {
                errorMsg(path, iae)
                return null
            }
            if (rtn != null) {
                map[key] = rtn
            }
        }
        return rtn
    }

    private val map: MutableMap<String, Image> = HashMap()

    operator fun get(key: String): Image? {
        return map[key]
    }

    operator fun get(key: String, path: String?): Image? {
        return find(key, path)
    }
}
