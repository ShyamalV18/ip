package porus.command;

import porus.*;
import porus.task.Task;

/**
 * Represents a command that marks or unmarks
 * a task as done.
 */
public class MarkCommand extends Command {

    private final int index;
    private final boolean isDone;

    /**
     * Creates a MarkCommand.
     *
     * @param index The index of the task to mark or unmark.
     * @param isDone True if marking as done, false if unmarking.
     */
    public MarkCommand(int index, boolean isDone) {
        this.index = index;
        this.isDone = isDone;
    }

    /**
     * Executes the mark/unmark operation by updating
     * the task status and saving the updated list.
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

        Task task = tasks.mark(index, isDone);
        storage.save(tasks.getAll());

        ui.showLine();

        if (isDone) {
            System.out.println("Good job bretheren! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet, dont be noob please:");
        }

        System.out.println("  " + task);
        ui.showLine();

        return false;
    }
}