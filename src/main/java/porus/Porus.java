package porus;

import porus.command.Command;

/**
 * Main application entry.
 */
public class Porus {

    private static final String FILE_PATH = "./data/porus.txt";

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

