package porus.command;

import porus.*;
import porus.task.Task;

/**
 * Represents a command that adds a new task to the task list.
 */
public class AddCommand extends Command {

    private final Task task;

    /**
     * Creates an AddCommand to add the specified task.
     *
     * @param task The task to be added to the task list.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add operation by adding the task to the list
     * and saving the updated list to storage.
     *
     * @param tasks The current task list.
     * @param ui The user interface handler.
     * @param storage The storage handler for persistence.
     * @return false since this command does not terminate the program.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {

        tasks.add(task);
        storage.save(tasks.getAll());

        ui.showLine();
        System.out.println("  added: " + task);
        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();

        return false;
    }
}