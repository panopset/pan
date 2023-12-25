package com.panopset.flywheel;

import com.panopset.compat.Logop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Flywheel command matcher.
 */
final class CommandMatcher {

  /**
   * Given a List of commands, tie the quit commands to the MatchableCommands.
   *
   * @param commands
   *          Commands to add structure to.
   * @return New List of commands.
   * @throws FlywheelException
   *           Thrown if there is an unmatched quit.
   */
  protected static List<Command> matchQuitCommands(final List<Command> commands)
      throws FlywheelException {
    FlywheelException fwe = null; 
    final List<MatchableCommand> matchedCommandsForDebugging = new ArrayList<>();
    final List<Command> rtn = new ArrayList<>();
    final Deque<MatchableCommand> stack = new ArrayDeque<>();
    for (Command command : commands) {
      updateStack(stack, rtn, command);
      if (command instanceof MatchableCommand) {
        matchedCommandsForDebugging.add((MatchableCommand) command);
        if (stack.isEmpty()) {
          rtn.add(command);
        }
        stack.push((MatchableCommand) command);
      } else if (command instanceof CommandQuit) {
        if (stack.isEmpty()) {
          for (MatchableCommand mc : matchedCommandsForDebugging) {
            Logop.info(String.format("Succussfully matched with quit, before un-matched quit found: %s", mc.toString()));
          }
          fwe = new FlywheelException("Un-matched quit found, stopping.");
          break;
        }
        stack.pop();
      }
    }
    if (fwe != null) {
      Logop.errorMsg("Failed to process the following template commands:");
      for (Command cmd : commands) {
        Logop.info(cmd.toString());
      }
      throw fwe;
    }
    return rtn;
  }
  
  private static void updateStack(Deque<MatchableCommand> stack, List<Command> rtn, Command command ) {
    if (stack.isEmpty()) {
      if (!(command instanceof MatchableCommand)) {
        rtn.add(command);
      }
    } else {
      stack.peek().commands.add(command);
    }
  }

  private CommandMatcher() {
  }
}
