package porus;

import porus.task.*;

import java.util.ArrayList;

/**
 * Entry point and main command loop for Porus.
 */
public class Porus {

    private static final String FILE_PATH = "./data/porus.txt";

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    private static final String DEADLINE_BY_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    public static void main(String[] args) {

        UI ui = new UI();
        Storage storage = new Storage(FILE_PATH);
        TaskList tasks = new TaskList(storage.load());

        ui.showGreeting();

        while (true) {
            String userInput = ui.readCommand();

            try {
                if (userInput.equals(COMMAND_BYE)) {
                    ui.showGoodbye();
                    break;
                }

                if (userInput.equals(COMMAND_LIST)) {
                    ui.showList(tasks.getAll());
                    continue;
                }

                if (userInput.startsWith(COMMAND_MARK)) {
                    handleMark(tasks, userInput, true, storage, ui);
                    continue;
                }

                if (userInput.startsWith(COMMAND_UNMARK)) {
                    handleMark(tasks, userInput, false, storage, ui);
                    continue;
                }

                if (userInput.startsWith(COMMAND_DELETE)) {
                    handleDelete(tasks, userInput, storage, ui);
                    continue;
                }

                Task newTask = parseTask(userInput);
                tasks.add(newTask);
                storage.save(tasks.getAll());

                ui.showLine();
                System.out.println("  added: " + newTask);
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                ui.showLine();

            } catch (PorusException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static void handleMark(TaskList tasks,
                                   String userInput,
                                   boolean isDone,
                                   Storage storage,
                                   UI ui) throws PorusException {

        String numberPart = userInput.substring(
                isDone ? COMMAND_MARK.length() : COMMAND_UNMARK.length()).trim();

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new PorusException("Task index must be a valid number.");
        }

        Task updatedTask = tasks.mark(taskNumber - 1, isDone);
        storage.save(tasks.getAll());

        ui.showLine();
        System.out.println("  " + updatedTask);
        ui.showLine();
    }

    private static void handleDelete(TaskList tasks,
                                     String userInput,
                                     Storage storage,
                                     UI ui) throws PorusException {

        String numberPart = userInput.substring(COMMAND_DELETE.length()).trim();

        if (numberPart.isEmpty()) {
            throw new PorusException("Please specify a task number to delete.");
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new PorusException("Task index must be a valid number.");
        }

        Task removedTask = tasks.remove(taskNumber - 1);
        storage.save(tasks.getAll());

        ui.showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }

    private static Task parseTask(String userInput) throws PorusException {

        if (userInput.startsWith(COMMAND_TODO)) {
            String description = userInput.substring(COMMAND_TODO.length()).trim();
            if (description.isEmpty()) {
                throw new PorusException("The description of a todo cannot be empty.");
            }
            return new Todo(description);
        }

        if (userInput.startsWith(COMMAND_DEADLINE)) {
            String rest = userInput.substring(COMMAND_DEADLINE.length()).trim();
            int byIndex = rest.indexOf(DEADLINE_BY_DELIMITER);

            if (byIndex < 0) {
                throw new PorusException("Format: deadline DESCRIPTION /by DATE");
            }

            String description = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + DEADLINE_BY_DELIMITER.length()).trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new PorusException("Deadline must include description and /by date.");
            }

            return new Deadline(description, by);
        }

        if (userInput.startsWith(COMMAND_EVENT)) {
            String rest = userInput.substring(COMMAND_EVENT.length()).trim();

            int fromIndex = rest.indexOf(EVENT_FROM_DELIMITER);
            int toIndex = rest.indexOf(EVENT_TO_DELIMITER);

            if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
                throw new PorusException("Format: event DESCRIPTION /from START /to END");
            }

            String description = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
            String to = rest.substring(toIndex + EVENT_TO_DELIMITER.length()).trim();

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new PorusException("Event must include description, /from and /to.");
            }

            return new Event(description, from, to);
        }

        throw new PorusException("I do not understand that command.");
    }
}

