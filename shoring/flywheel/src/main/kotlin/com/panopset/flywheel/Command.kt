package com.panopset.flywheel

import java.io.StringWriter

abstract class Command(val template: Template, val templateLine: TemplateLine) {
    var next: Command? = null
    var prev: Command? = null

    abstract fun resolve(sw: StringWriter)
    fun resolveCommand(sw: StringWriter) {
        resolve(sw)
    }

    abstract fun getDescription(): String

    override fun toString(): String {
        val sw = StringWriter()
        sw.append(template.relativePath)
        sw.append(" ")
        sw.append(templateLine.toString())
        return sw.toString()
    }
}
