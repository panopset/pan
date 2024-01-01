package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Panop
import java.io.StringWriter

class CommandUnkown (
    panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateDirectiveCommand(panop, templateLine, innerPiece, template) {
    override fun resolve(sw: StringWriter) {
        Logop.errorMsg(panop, "UNKNOWN COMMAND: $templateLine")
        super.resolve(sw)
    }
}
