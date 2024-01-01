package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Panop
import java.io.StringWriter

/**
 * Once a Flywheel Script file is read, it becomes a template to be processed. Templates may also be
 * processed using the template directive within any template.
 */
class Template(val panop: Panop, val flywheel: Flywheel, val templateSource: TemplateSource, val templateRules: LineFeedRules) {
    constructor(panop: Panop, flywheel: Flywheel, sourceFile: SourceFile, templateRules: LineFeedRules) :
            this(panop, flywheel, TemplateFile(sourceFile.file), templateRules)

    private var sf: SourceFile? = null
    var commandFile: CommandFile? = null

    private val rawCommands: List<Command> = RawCommandLoader(panop, this).load()
    private val topCommands: List<Command>

    init {
        addStructure(rawCommands)
        val commands = ImpliedQuitFilter().addImpliedQuits(rawCommands)
        topCommands = CommandMatcher.matchQuitCommands(panop, commands)
    }


    fun exec(sw: StringWriter) {
        val list = topCommands
        for (topCommand in list) {
            if (flywheel.isStopped) {
                return
            }
            try {
                topCommand.resolve(sw)
            } catch (e: FlywheelException) {
                Logop.errorEx(panop, e)
                Logop.warn(panop, "Relative path: $relativePath")
                flywheel.stop(e.message)
                return
            }
        }
    }

    fun output() {
        val list = topCommands
        try {
            for (topCommand in list) {
                topCommand.resolveCommand(flywheel.writer)
            }
        } catch (t: FlywheelException) {
            Logop.errorEx(panop, t)
            stop(t.message)
        }
    }

    private fun stop(msg: String?) {
        Logop.errorMsg(panop,
            String.format(
                "Stopped while processing line %d: %s.",
                templateSource.line,
                templateSource.name
            )
        )
        flywheel.stop(msg)
    }

    val relativePath: String
        get() = sf?.relativePath ?: ""
}
