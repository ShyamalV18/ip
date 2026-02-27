package porus.command;

import porus.*;
import porus.task.Task;

public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

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
