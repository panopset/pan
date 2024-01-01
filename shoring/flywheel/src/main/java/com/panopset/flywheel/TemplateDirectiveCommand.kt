package com.panopset.flywheel

import com.panopset.compat.Panop
import com.panopset.compat.Stringop
import java.io.StringWriter

/**
 * Command that is created from a directive.
 */
open class TemplateDirectiveCommand(panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateCommand(panop, templateLine, innerPiece, template) {


    init {
        var params = ""
        if (innerPiece.length > 2) {
            params = innerPiece.substring(Syntax.getDirective().length + 2)
        }
        val replacement = template.flywheel.getEntry(params)
        parms = if (Stringop.isPopulated(replacement)) {
            replacement
        } else {
            params
        }
    }

    override fun resolve(sw: StringWriter) {
        // does nothing here.
    }

    fun mapValueFirstThenExplicit(params: String): String {
        val replacementFileName = template.flywheel.getEntry(params)
        return if (Stringop.isPopulated(replacementFileName)) {
            replacementFileName
        } else params
    }
}
