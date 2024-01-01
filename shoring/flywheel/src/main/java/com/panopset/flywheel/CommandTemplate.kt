package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Panop
import java.io.StringWriter

/**
 * <h1>t - Template</h1>
 *
 *
 *
 * Example 1
 *
 *
 * <pre>
 * ${&#064;t someTemplateFile.txt}
</pre> *
 *
 *
 *
 * Example 2
 *
 *
 * <pre>
 * ${&#064;p templateName}someTemplateFile.txt${&#064;q}
 * ${&#064;t &#064;templateName}
</pre> *
 *
 *
 *
 * Continue execution using the supplied template file.
 *
 */
class CommandTemplate(
    panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateDirectiveCommand(panop, templateLine, innerPiece, template) {
    /**
     * Get last command.
     *
     * @return Last command.
     */
    /**
     * Set last command.
     *
     * @param command
     * Last command.
     */
    /**
     * Last command.
     */
    var lastCommand: Command? = null
    override fun resolve(sw: StringWriter) {
        val ts = template.templateSource
        val templateFileName = mapValueFirstThenExplicit(getParams())
        Logop.info(panop,
            String.format(
                "Template %s executing template %s at line %4d.",
                ts.name,
                templateFileName,
                ts.line
            )
        )
        val template = Template(panop,
            template.flywheel, SourceFile(panop, template.flywheel, templateFileName), template.templateRules
        )



        template.exec(sw)
    }

    companion object {
        val shortHtmlText: String
            /**
             * Short HTML text for publishing command format in an HTML document.
             *
             * @return **${&#064;t someTemplateFile.txt}**.
             */
            get() = "\${&#064;t someTemplateFile.txt}"
    }
}
