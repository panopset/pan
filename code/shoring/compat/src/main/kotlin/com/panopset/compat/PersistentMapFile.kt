package com.panopset.compat

import java.io.File
import java.io.IOException
import java.util.*

class PersistentMapFile(newFile: File) : PersistentMap {

    fun setNewFile(newFile: File) {
        file = newFile
    }

    override fun put(key: String, value: String) {
        try {
            getProps().setProperty(key, value)
        } catch (ex: IOException) {
            Logz.errorEx(ex)
        }
    }

    fun entrySet(): Set<Map.Entry<Any, Any>> {
        return getProps().entries
    }

    override fun get(key: String, dft: String): String {
        return if (!Stringop.isPopulated(key)) {
            dft
        } else getProps().getProperty(key) ?: return dft
    }

    override fun get(key: String): String {
        return get( key, "")
    }

    fun purge() {
        if (Fileop.fileExists(file)) {
            file.delete()
        }
    }

    fun flush() {
        try {
            Propop.save(getProps(), file)
        } catch (ex: IOException) {
            Logz.errorEx(ex)
        }
    }

    private fun getProps(): Properties {
        if (props.isEmpty) {
            load()
        }
        return props
    }

    var file: File = File("")

    private var props: Properties = Properties()
    fun load() {
        props.clear()
        props = Propop.load( file)
    }

    init {
        setNewFile(newFile)
    }
}
