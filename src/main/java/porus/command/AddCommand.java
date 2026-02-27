package porus.command;

import porus.*;
import porus.task.Task;

public class AddCommand extends Command {

    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

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