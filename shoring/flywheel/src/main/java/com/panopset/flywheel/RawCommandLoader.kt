package com.panopset.flywheel

import com.panopset.compat.Logop
import com.panopset.compat.Stringop
import java.util.*

internal class RawCommandLoader(template: Template) {
    private val tmplt: Template
    private val queue: Deque<TemplateLine> = ArrayDeque()
    private val commands: MutableList<Command> = ArrayList()

    init {
        tmplt = template
    }

    fun load(): List<Command> {
        tmplt!!.templateSource.reset()
        var templateIndex = 0
        var lineNumber = 0
        while (!tmplt.templateSource.isDone) {
            if (tmplt.flywheel.isStopped) {
                return ArrayList()
            }
            flushQueue()
            if (tmplt.flywheel.isStopped) {
                return ArrayList()
            }
            var line = tmplt.templateSource.next()
            if (tmplt.templateRules.lineBreaks) {
                line = String.format("%s%s", line, Stringop.getEol())
            }
            process(TemplateLine(line, templateIndex, lineNumber++))
            templateIndex += line.length
        }
        flushQueue()
        return commands
    }

    @Throws(FlywheelException::class)
    private fun flushQueue() {
        while (!queue.isEmpty()) {
            process(queue.pop())
            if (tmplt!!.flywheel.isStopped) {
                return
            }
        }
    }

    private fun loadCommand(command: Command) {
        commands.add(command)
        Logop.debug("Loading command: $command")
    }

    @Throws(FlywheelException::class)
    private fun process(templateLine: TemplateLine) {
        val line = templateLine.line
        val openDirectiveLoc = line.indexOf(Syntax.getOpenDirective())
        val closeDirectiveLoc = line.indexOf(Syntax.getCloseDirective())
        if (closeDirectiveLoc == -1 || openDirectiveLoc == -1) {
            loadCommand(CommandText(tmplt, templateLine, line))
            return
        }
        if (closeDirectiveLoc < openDirectiveLoc) {
            skipTo(templateLine, openDirectiveLoc)
            return
        }
        val endOfDirective = closeDirectiveLoc + Syntax.getCloseDirective().length
        if (openDirectiveLoc == 0) {
            val command = CommandBuilder().template(tmplt).source(templateLine, closeDirectiveLoc).construct()
            if (command == null) {
                skipTo(templateLine, 1)
            } else {
                val remainder = line.substring(endOfDirective)
                loadCommand(command)
                if (remainder.length > 0) {
                    queue.push(
                        TemplateLine(
                            remainder,
                            templateLine.templateCharIndex + openDirectiveLoc,
                            templateLine.templateLineNumber
                        )
                    )
                }
            }
        } else {
            skipTo(templateLine, openDirectiveLoc)
        }
    }

    private fun skipTo(templateLine: TemplateLine, pos: Int) {
        val line = templateLine.line
        val templateCharIndex = templateLine.templateCharIndex
        val templateLineNumber = templateLine.templateLineNumber
        val skippedTextLine = line.substring(0, pos)
        loadCommand(
            CommandText(
                tmplt,
                TemplateLine(skippedTextLine, templateCharIndex, templateLineNumber),
                skippedTextLine
            )
        )
        queue.push(TemplateLine(line.substring(pos), templateCharIndex + pos, templateLineNumber))
    }
}

fun addStructure(commands: List<Command>) {
    var prev: Command? = null
    for (command in commands) {
        command.prev = prev
        if (prev != null) {
            prev.next = command
        }
        prev = command
    }
}
