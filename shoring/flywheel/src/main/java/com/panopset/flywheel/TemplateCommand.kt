package com.panopset.flywheel

import com.panopset.compat.Panop

/**
 * A template command is specified in a script, and has a source.
 */
abstract class TemplateCommand internal constructor(
    panop: Panop,
    templateLine: TemplateLine,
    val innerPiece: String,
    template: Template
) : Command(panop,
    template, templateLine
) {
    var parms = ""
    override fun getDescription(): String {
        return templateLine.line
    }

    fun getParams(): String {
        if (parms.length > Syntax.getDirective().length
            && parms.indexOf(Syntax.getDirective()) == 0
        ) {
            return template.flywheel.getEntry(parms.substring(Syntax.getDirective().length))
        }
        return parms
    }
}
