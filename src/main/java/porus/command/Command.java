package porus.command;

import porus.PorusException;
import porus.Storage;
import porus.TaskList;
import porus.UI;

/**
 * Represents a user command.
 */
public abstract class Command {

    /**
     * Executes the command.
     * @return true if program should exit
     */
    public abstract boolean execute(TaskList tasks, UI ui, Storage storage)
            throws PorusException;
}