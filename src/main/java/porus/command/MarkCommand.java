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
        System.out.println("  " + task);
        ui.showLine();

        return false;
    }
}