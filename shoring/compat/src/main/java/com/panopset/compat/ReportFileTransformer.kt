package com.panopset.compat

import java.io.*

class ReportFileTransformer(private val panop: Panop, private val ff: File, private val tf: File, val transformer: Transformer) {
    constructor(panop: Panop, ff: File, transformer: Transformer) : this(panop, ff, ff, transformer)

    fun withByLineFilters(vararg byLineFilters: ByLineFilter): ReportFileTransformer {
        for (lf in byLineFilters) {
            transformer.withByLineFilter(lf)
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
            Fileop.write(panop, sw.toString(), ff)
        }
    }

    private fun execToFile() {
        try {
            FileWriter(tf).use { fw -> processToWriter(fw) }
        } catch (e: IOException) {
            Logop.errorMsg(panop, tf, e)
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
