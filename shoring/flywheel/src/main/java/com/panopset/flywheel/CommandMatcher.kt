package com.panopset.flywheel

import com.panopset.compat.Logop.errorMsg
import com.panopset.compat.Logop.info
import java.util.*

/**
 * Flywheel command matcher.
 */
internal object CommandMatcher {
    /**
     * Given a List of commands, tie the quit commands to the MatchableCommands.
     *
     * @param commands
     * Commands to add structure to.
     * @return New List of commands.
     * @throws FlywheelException
     * Thrown if there is an unmatched quit.
     */
    @Throws(FlywheelException::class)
    fun matchQuitCommands(commands: List<Command>): List<Command> {
        var fwe: FlywheelException? = null
        val matchedCommandsForDebugging: MutableList<MatchableCommand> = ArrayList()
        val rtn: MutableList<Command> = ArrayList()
        val stack: Deque<MatchableCommand> = ArrayDeque()
        for (command in commands) {
            updateStack(stack, rtn, command)
            if (command is MatchableCommand) {
                matchedCommandsForDebugging.add(command)
                if (stack.isEmpty()) {
                    rtn.add(command)
                }
                stack.push(command)
            } else if (command is CommandQuit) {
                if (stack.isEmpty()) {
                    for (mc in matchedCommandsForDebugging) {
                        info(
                            String.format(
                                "Succussfully matched with quit, before un-matched quit found: %s",
                                mc.toString()
                            )
                        )
                    }
                    fwe = FlywheelException("Un-matched quit found, stopping.")
                    break
                }
                stack.pop()
            }
        }
        if (fwe != null) {
            errorMsg("Failed to process the following template commands:")
            for (cmd in commands) {
                info(cmd.toString())
            }
            throw fwe
        }
        return rtn
    }

    private fun updateStack(stack: Deque<MatchableCommand>, rtn: MutableList<Command>, command: Command) {
        if (stack.isEmpty()) {
            if (command !is MatchableCommand) {
                rtn.add(command)
            }
        } else {
            stack.peek().commands.add(command)
        }
    }
}
