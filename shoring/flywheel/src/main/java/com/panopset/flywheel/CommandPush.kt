package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Panop
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
class CommandPush(panop: Panop,
    templateLine: TemplateLine, innerPiece: String,
    template: Template
) : MatchableCommand(panop, templateLine, innerPiece, template), UserMatchableCommand {
    override fun resolve(sw: StringWriter) {
        val tsw = StringWriter()
        resolveMatchedCommands(tsw)
        template.flywheel.put(getParams(), tsw.toString())
        Logop.info(panop, "Push command defined ${getParams()} as $tsw.")
    }

    companion object {
        val shortHtmlText = "\${&#064;p name}"
    }
}
