package com.panopset.flywheel

import com.panopset.compat.Logop
import java.io.StringWriter

class CommandUnkown (
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateDirectiveCommand(templateLine, innerPiece, template) {
    override fun resolve(sw: StringWriter) {
        Logop.errorMsg("UNKNOWN COMMAND: $templateLine")
        super.resolve(sw)
    }
}
