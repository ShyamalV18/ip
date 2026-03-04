package porus.command;

import porus.*;
import porus.task.Task;

public class MarkCommand extends Command {

    private final int index;
    private final boolean isDone;

    public MarkCommand(int index, boolean isDone) {
        this.index = index;
        this.isDone = isDone;
    }

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