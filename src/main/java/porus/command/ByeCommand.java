package porus.command;

import porus.*;

/**
 * Represents a command that exits the application.
 */
public class ByeCommand extends Command {

    /**
     * Executes the exit operation by displaying a goodbye message.
     *
     * @param tasks The current task list.
     * @param ui The user interface handler.
     * @param storage The storage handler.
     * @return true to indicate that the program should terminate.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showGoodbye();
        return true;
    }
}