package com.panopset.flywheel

import com.panopset.compat.Fileop
import com.panopset.compat.Logop
import com.panopset.compat.Panop
import java.io.File
import java.io.StringWriter

class CommandMap (
    panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateDirectiveCommand(panop, templateLine, innerPiece, template) {
    override fun resolve(sw: StringWriter) {
        val mapFileName = mapValueFirstThenExplicit(getParams())
        val mapFile = File(mapFileName)
        if (!mapFile.exists()) {
            Logop.errorMsg(panop, "File not found: $mapFileName")
        }
        val props  = Fileop.loadProps(panop, mapFile)
        for (key in props.propertyNames()) {
            template.flywheel.topMap[key.toString()] = props.getProperty(key.toString())
        }
        super.resolve(sw)
    }
}
