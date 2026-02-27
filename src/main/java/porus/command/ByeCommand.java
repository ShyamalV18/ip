package porus.command;

import porus.*;

public class ByeCommand extends Command {

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showGoodbye();
        return true;
    }
}