package com.panopset.flywheel

import com.panopset.compat.Logop
import java.io.StringWriter

/**
 * <h1>p - Push</h1>
 *
 * <pre>
 *
 * ${&#064;p name}Joe${&#064;q}
</pre> *
 *
 *
 * Everything following this command is pushed into a String buffer, until a q
 * command is reached.
 *
 */
class CommandPush(
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : MatchableCommand(templateLine, innerPiece, template), UserMatchableCommand {
    override fun resolve(sw: StringWriter) {
        val tsw = StringWriter()
        resolveMatchedCommands(tsw)
        template.flywheel.put(getParams(), tsw.toString())
        Logop.info("Push command defined ${getParams()} as $tsw.")
    }

    companion object {
        val shortHtmlText = "\${&#064;p name}"
    }
}
