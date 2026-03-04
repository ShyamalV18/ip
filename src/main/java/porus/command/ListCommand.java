package porus.command;

import porus.*;

/**
 * Represents a command that displays all tasks
 * currently stored in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list operation by displaying all tasks.
     *
     * @param tasks The current task list.
     * @param ui The user interface handler.
     * @param storage The storage handler.
     * @return false since this command does not terminate the program.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks.getAll());
        return false;
    }
}