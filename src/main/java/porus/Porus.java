package porus;

import porus.task.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point and command loop for the Porus chatbot.
 * <p>
 * This class handles:
 * <ul>
 *     <li>User input processing</li>
 *     <li>Task creation (Todo, Deadline, Event)</li>
 *     <li>Marking and unmarking tasks</li>
 *     <li>Deleting tasks</li>
 *     <li>Listing tasks</li>
 * </ul>
 * Tasks are stored using {@code ArrayList<Task>} to satisfy
 * the A-Collections requirement.
 */
public class Porus {

    /** Divider line used for formatting output. */
    private static final String DIVIDER =
            "----------------------------------------------------------------------------------------------------";

    /** Supported command keywords. */
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    /** Delimiters for parsing complex commands. */
    private static final String DEADLINE_BY_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    /**
     * Starts the Porus chatbot.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        printGreeting();

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String userInput = scanner.nextLine().trim();

            try {
                if (userInput.equals(COMMAND_BYE)) {
                    printGoodbye();
                    break;
                }

                if (userInput.equals(COMMAND_LIST)) {
                    printList(tasks);
                    continue;
                }

                if (userInput.startsWith(COMMAND_MARK)) {
                    handleMark(tasks, userInput, true);
                    continue;
                }

                if (userInput.startsWith(COMMAND_UNMARK)) {
                    handleMark(tasks, userInput, false);
                    continue;
                }

                if (userInput.startsWith(COMMAND_DELETE)) {
                    handleDelete(tasks, userInput);
                    continue;
                }

                Task newTask = parseTask(userInput);
                tasks.add(newTask);

                System.out.println(DIVIDER);
                System.out.println("  added: " + newTask);
                System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(DIVIDER);

            } catch (PorusException e) {
                System.out.println(DIVIDER);
                System.out.println(e.getMessage());
                System.out.println(DIVIDER);
            }
        }

        scanner.close();
    }

    /**
     * Parses user input into a corresponding {@code Task}.
     *
     * @param userInput the raw user input
     * @return the created {@code Task}
     * @throws PorusException if the command format is invalid
     */
    private static Task parseTask(String userInput) throws PorusException {

        if (userInput.startsWith(COMMAND_TODO)) {
            String description = userInput.substring(COMMAND_TODO.length()).trim();
            if (description.isEmpty()) {
                throw new PorusException(
                        "The description of a todo cannot be empty bretheren.");
            }
            return new Todo(description);
        }

        if (userInput.startsWith(COMMAND_DEADLINE)) {
            String rest = userInput.substring(COMMAND_DEADLINE.length()).trim();
            int byIndex = rest.indexOf(DEADLINE_BY_DELIMITER);

            if (byIndex < 0) {
                throw new PorusException(
                        "Format: deadline DESCRIPTION /by DATE");
            }

            String description = rest.substring(0, byIndex).trim();
            String by = rest.substring(
                    byIndex + DEADLINE_BY_DELIMITER.length()).trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new PorusException(
                        "porus.task.Deadline must include description and /by date.");
            }

            return new Deadline(description, by);
        }

        if (userInput.startsWith(COMMAND_EVENT)) {
            String rest = userInput.substring(COMMAND_EVENT.length()).trim();

            int fromIndex = rest.indexOf(EVENT_FROM_DELIMITER);
            int toIndex = rest.indexOf(EVENT_TO_DELIMITER);

            if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
                throw new PorusException(
                        "Format: event DESCRIPTION /from START /to END");
            }

            String description = rest.substring(0, fromIndex).trim();
            String from = rest.substring(
                    fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
            String to = rest.substring(
                    toIndex + EVENT_TO_DELIMITER.length()).trim();

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new PorusException(
                        "porus.task.Event must include description, /from and /to.");
            }

            return new Event(description, from, to);
        }

        throw new PorusException(
                "I do not understand that command. I will make you regret your existence.");
    }

    /**
     * Marks or unmarks a task based on user input.
     *
     * @param tasks the list of tasks
     * @param userInput the full command string
     * @param isDone true to mark complete, false to unmark
     * @throws PorusException if the index is invalid
     */
    private static void handleMark(ArrayList<Task> tasks,
                                   String userInput,
                                   boolean isDone)
            throws PorusException {

        String numberPart = userInput.substring(
                isDone ? COMMAND_MARK.length() : COMMAND_UNMARK.length()).trim();

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new PorusException("Task index must be a valid number.");
        }

        int index = taskNumber - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new PorusException(
                    "Invalid task number. Please rethink thy life choices.");
        }

        tasks.get(index).setDone(isDone);

        System.out.println(DIVIDER);
        System.out.println("  " + tasks.get(index));
        System.out.println(DIVIDER);
    }

    /**
     * Deletes a task from the list.
     *
     * @param tasks the list of tasks
     * @param userInput the full command string
     * @throws PorusException if the index is invalid
     */
    private static void handleDelete(ArrayList<Task> tasks,
                                     String userInput)
            throws PorusException {

        String numberPart =
                userInput.substring(COMMAND_DELETE.length()).trim();

        if (numberPart.isEmpty()) {
            throw new PorusException(
                    "Please specify a task number to delete.");
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new PorusException("Task index must be a valid number.");
        }

        int index = taskNumber - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new PorusException(
                    "Invalid task number. Please rethink thy life choices.");
        }

        Task removedTask = tasks.remove(index);

        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints all tasks currently stored.
     *
     * @param tasks the list of tasks
     */
    private static void printList(ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        System.out.println("Bretheren, please complete thy tasks");

        if (tasks.isEmpty()) {
            System.out.println("  (No quests assigned yet.)");
            System.out.println(DIVIDER);
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.get(i));
        }

        System.out.println(DIVIDER);
    }

    /**
     * Prints the startup greeting and logo.
     */
    private static void printGreeting() {
        String logo =
                " ____    ___    ____   _   _    ____ \n"
                        + "|  _ \\  / _ \\  |  _ \\ | | | |  / ___|\n"
                        + "| |_) || | | | | |_) || | | |  \\___ \\\n"
                        + "|  __/ | |_| | |  _ < | |_| |   ___) |\n"
                        + "|_|     \\___/  |_| \\_\\ \\___/   |____/\n";

        System.out.println("Greetings! I'm");
        System.out.println(logo);
        System.out.println("(Personally Operating Real Understanding Service)");
        System.out.println("How may I assist you today?");
        System.out.println(DIVIDER);
    }

    /**
     * Prints the goodbye message.
     */
    private static void printGoodbye() {
        System.out.println(DIVIDER);
        System.out.println("Farewell. Glad to be of service!");
        System.out.println(DIVIDER);
    }
}
