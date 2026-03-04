package porus;

import porus.command.Command;

/**
 * Entry point of the Porus task management application.
 * Manages program initialization and the main execution loop.
 */
public class Porus {

    private static final String FILE_PATH = "./data/porus.txt";

    /**
     * Launches the Porus application.
     * Initializes UI, storage, and task list before entering the main loop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        UI ui = new UI();
        Storage storage = new Storage(FILE_PATH);
        TaskList tasks = new TaskList(storage.load());

        ui.showGreeting();

        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parse(input);
                isExit = command.execute(tasks, ui, storage);
            } catch (PorusException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}