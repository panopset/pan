package com.panopset.compat

import java.io.*

class Report(private val ff: File, private val tf: File, val transformer: Transformer) {
    constructor(ff: File, transformer: Transformer) : this(ff, ff, transformer)

    fun withByLineFilters(byLineFilters: MutableList<ByLineFilter>): Report {
        for (lf in byLineFilters) {
            transformer.addByLineFilter(lf)
        }
        return this
    }

    fun withByLineFilters(vararg byLineFilters: ByLineFilter): Report {
        for (lf in byLineFilters) {
            transformer.addByLineFilter(lf)
        }
        return this
    }

    fun exec() {
        if (tf == ff) {
            execInMemory()
        } else {
            execToFile()
        }
    }

    private fun execInMemory() {
        val sw = StringWriter()
        val hasChanged = processToWriter(sw)
        if (hasChanged) {
            Fileop.write(sw.toString(), ff)
        }
    }

    private fun execToFile() {
        try {
            FileWriter(tf).use { fw -> processToWriter(fw) }
        } catch (e: IOException) {
            Logop.errorMsg(tf, e)
        }
    }

    private fun processToWriter(writer: Writer): Boolean {
        var changed: Boolean
        FileReader(ff).use { fr ->
            changed = transformer.process(fr, writer)
        }
        return changed
    }
}
