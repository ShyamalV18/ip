package porus.command;

import porus.*;

public class ListCommand extends Command {

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks.getAll());
        return false;
    }
}