package com.panopset.compat

import java.io.StringWriter

class TextProcessor {
    private var sw = StringWriter()
    private val stringWriter = StringWriter()
    private val listeners: MutableList<ProcessListener> = ArrayList()
    fun addProcessListener(listener: ProcessListener) {
        listeners.add(listener)
    }

    fun append(value: String?) {
        for (listener in listeners) {
            listener.append(value)
        }
        stringWriter.append(value)
    }

    var text: String
        get() = sw.toString()
        set(value) {
            sw = StringWriter()
            for (listener in listeners) {
                listener.setText(value)
            }
            stringWriter.append(value)
        }
}
