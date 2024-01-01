package com.panopset.flywheel

import com.panopset.compat.Fileop
import com.panopset.compat.Logop
import com.panopset.compat.Panop
import com.panopset.compat.Stringop.getEol
import java.io.StringWriter

class CommandRaw(panop: Panop, templateLine: TemplateLine, innerPiece: String, template: Template) :
    TemplateDirectiveCommand(panop, templateLine, innerPiece, template) {
    override fun resolve(sw: StringWriter) {
        val ts = template.templateSource
        Logop.info(panop,
            String.format(
                "Template %s executing raw as is file %s at line %4d.",
                ts.name,
                getParams(),
                ts.line
            )
        )
        val sourceFile = SourceFile(panop, template.flywheel, getParams())
        for (line in Fileop.readLines(panop, sourceFile.file)) {
            sw.append(line)
            sw.append(getEol())
        }
    }
}
