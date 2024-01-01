package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Panop
import java.io.StringWriter

/**
 * <h1>e - Execute</h1>
 *
 *
 * Excecute any public static method, defined as a Java System property,
 * where the key starts with "com.panopset.flywheel.cmdkey." and finishes
 * with the name of the command to be used in the e command definition.
 * The function returned by the key must be one that returns a String and takes 0 or more
 * String parameters. If parameters match variable names, then they will be
 * replaced by the variable value, otherwise the parameter will be used as is.
 *
 *
 *
 * You may register objects that will be available using the
 * Flywheel.Builder.registerObject function. The keys must be one word. Instance
 * methods may be called from registered objects.
 *
 *
 * <pre>
 *
 * ${&#064;p name}panopset${&#064;q}
 *
 * ${&#064;e capitalize(name)}
 *
</pre> *
 *
 *
 *
 * The above script will output:
 *
 *
 * <pre>
 *
 * Panopset
 *
</pre> *
 */
class CommandExecute(panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : TemplateDirectiveCommand(
    panop, templateLine, innerPiece, template
) {
    override fun resolve(sw: StringWriter) {
        flywheel = template.flywheel
        try {
            val str = getParams()
            val incr = str.lastIndexOf(".")
            if (incr > -1) {
                val key = str.substring(0, incr)
                val obj = template.flywheel.registeredObjects[key]
                if (obj != null) {
                    sw.append(
                        ReflectionInvoker.Builder(panop, template.flywheel).objx(obj)
                            .methodAndParms(str.substring(incr + 1))
                            .mapProvider(template.flywheel).construct().exec(template.flywheel)
                    )
                    return
                }
            }
            val rtn = ReflectionInvoker.Builder(panop, template.flywheel)
                .classMethodAndParms(getParams())
                .mapProvider(template.flywheel).construct().exec(template.flywheel)
            Logop.warn(panop, String.format("%s %s: %s", template.templateSource.name, templateLine.toString(), rtn))
            sw.append(rtn)
        } catch (ex: FlywheelException) {
            Logop.errorEx(panop, ex)
            Logop.warn(panop, template.relativePath)
            if (template.commandFile != null) {
                Logop.warn(panop, "Output file: " + template.commandFile)
                Logop.warn(panop, "source: $templateLine")
            }
            template.flywheel.stop(ex.message)
            val errorMessage = String.format("Failure executing %s.", templateLine)
            template.flywheel.stop(errorMessage)
        }
    }

    companion object {
        /**
         * Short HTML text for publishing command format in an HTML document.
         *
         * @return **${&#064;e capitalize(name)}**.
         */
        const val shortHtmlText = "\${&#064; capitalize(name)}"
        @JvmField
        var flywheel: Flywheel? = null
    }
}
