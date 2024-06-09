package com.panopset.compat

import com.panopset.compat.Stringop.getEol
import java.io.StringWriter

class JavaVersionChart {
    private fun print(): String {
        val sw = StringWriter()
        for (mv in MajorVersion.entries) {
            sw.append(getEol())
            sw.append(mv.toString())
        }
        return sw.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            dspmsglg(chart)
        }

        private val chart = JavaVersionChart().print()
    }
}
