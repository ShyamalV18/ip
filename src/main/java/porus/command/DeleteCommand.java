package porus.command;

import porus.*;
import porus.task.Task;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Creates a DeleteCommand to remove the task at the specified index.
     *
     * @param index The index of the task to be removed.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete operation by removing the task
     * from the list and saving the updated list to storage.
     *
     * @param tasks The current task list.
     * @param ui The user interface handler.
     * @param storage The storage handler for persistence.
     * @return false since this command does not terminate the program.
     * @throws PorusException If the given index is invalid.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage)
            throws PorusException {

        Task removed = tasks.remove(index);
        storage.save(tasks.getAll());

        ui.showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();

        return false;
    }
}