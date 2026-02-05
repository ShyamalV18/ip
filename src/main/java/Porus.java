import java.util.Scanner;

/**
 * Entry point and command loop for the Porus chatbot.
 * Reads user commands, creates tasks, marks/unmarks tasks, and lists tasks.
 */
public class Porus {

    private static final String DIVIDER = "--------------------------------------------------";
    private static final int MAX_TASKS = 100;

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";

    private static final String DEADLINE_BY_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    public static void main(String[] args) {
        printGreeting();

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equals(COMMAND_BYE)) {
                printGoodbye();
                break;
            }

            if (userInput.equals(COMMAND_LIST)) {
                printList(tasks, taskCount);
                continue;
            }

            if (userInput.startsWith(COMMAND_MARK)) {
                handleMark(tasks, taskCount, userInput, true);
                continue;
            }

            if (userInput.startsWith(COMMAND_UNMARK)) {
                handleMark(tasks, taskCount, userInput, false);
                continue;
            }

            if (taskCount >= MAX_TASKS) {
                System.out.println(DIVIDER);
                System.out.println("My scroll is full. I cannot record more quests.");
                System.out.println(DIVIDER);
                continue;
            }

            Task newTask = parseTask(userInput);
            if (newTask == null) {
                // Invalid command format.
                continue;
            }

            tasks[taskCount] = newTask;
            taskCount++;

            System.out.println(DIVIDER);
            System.out.println("  added: " + newTask);
            System.out.println("  Now you have " + taskCount + " tasks in the list.");
            System.out.println(DIVIDER);
        }

        scanner.close();
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
     * Prints the goodbye message and divider.
     */
    private static void printGoodbye() {
        System.out.println(DIVIDER);
        System.out.println("Farewell. Glad to be of service!");
        System.out.println(DIVIDER);
    }

    /**
     * Parses user input into a Task object if it matches a supported Level 4 command.
     * Returns null (and prints a message) if the command format is invalid.
     *
     * Supported:
     * - todo DESCRIPTION
     * - deadline DESCRIPTION /by BY
     * - event DESCRIPTION /from FROM /to TO
     *
     * @param userInput raw user input line
     * @return created Task, or null if invalid
     */
    private static Task parseTask(String userInput) {
        if (userInput.startsWith(COMMAND_TODO)) {
            String description = userInput.substring(COMMAND_TODO.length()).trim();
            if (description.isEmpty()) {
                printInvalidFormat("todo DESCRIPTION");
                return null;
            }
            return new Todo(description);
        }

        if (userInput.startsWith(COMMAND_DEADLINE)) {
            String rest = userInput.substring(COMMAND_DEADLINE.length()).trim();
            int byIndex = rest.indexOf(DEADLINE_BY_DELIMITER);
            if (byIndex < 0) {
                printInvalidFormat("deadline DESCRIPTION /by BY");
                return null;
            }

            String description = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + DEADLINE_BY_DELIMITER.length()).trim();

            if (description.isEmpty() || by.isEmpty()) {
                printInvalidFormat("deadline DESCRIPTION /by BY");
                return null;
            }

            return new Deadline(description, by);
        }

        if (userInput.startsWith(COMMAND_EVENT)) {
            String rest = userInput.substring(COMMAND_EVENT.length()).trim();

            int fromIndex = rest.indexOf(EVENT_FROM_DELIMITER);
            int toIndex = rest.indexOf(EVENT_TO_DELIMITER);

            if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
                printInvalidFormat("event DESCRIPTION /from FROM /to TO");
                return null;
            }

            String description = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
            String to = rest.substring(toIndex + EVENT_TO_DELIMITER.length()).trim();

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                printInvalidFormat("event DESCRIPTION /from FROM /to TO");
                return null;
            }

            return new Event(description, from, to);
        }

        System.out.println(DIVIDER);
        System.out.println("I do not understand. Try one of these formats:");
        System.out.println("  todo DESCRIPTION");
        System.out.println("  deadline DESCRIPTION /by BY");
        System.out.println("  event DESCRIPTION /from FROM /to TO");
        System.out.println(DIVIDER);
        return null;
    }

    /**
     * Marks or unmarks a task by index (1-based as the user sees it).
     *
     * @param tasks task array
     * @param taskCount number of tasks currently stored
     * @param userInput user command (e.g., "mark 2")
     * @param isDone true to mark done, false to unmark
     */
    private static void handleMark(Task[] tasks, int taskCount, String userInput, boolean isDone) {
        String numberPart = userInput.substring(isDone ? COMMAND_MARK.length() : COMMAND_UNMARK.length()).trim();

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            System.out.println(DIVIDER);
            System.out.println("That index is not a number.");
            System.out.println(DIVIDER);
            return;
        }

        int index = taskNumber - 1;
        if (index < 0 || index >= taskCount) {
            System.out.println(DIVIDER);
            System.out.println("Invalid index! Please provide a valid task number.");
            System.out.println(DIVIDER);
            return;
        }

        tasks[index].setDone(isDone);

        System.out.println(DIVIDER);
        if (isDone) {
            System.out.println("Fantabulous! The deed is done. Marked as complete:");
        } else {
            System.out.println("Very well. I shall consider it unfinished:");
        }
        System.out.println("  " + tasks[index]);
        System.out.println(DIVIDER);
    }

    /**
     * Prints the current list of tasks in a numbered format.
     *
     * @param tasks task array
     * @param taskCount number of tasks stored
     */
    private static void printList(Task[] tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("Bretheren, please complete thy tasks");

        if (taskCount == 0) {
            System.out.println("  (No quests assigned yet.)");
            System.out.println(DIVIDER);
            return;
        }

        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i + 1) + "." + tasks[i]);
        }

        System.out.println(DIVIDER);
    }

    /**
     * Prints a consistent invalid format message.
     *
     * @param expectedFormat expected command format
     */
    private static void printInvalidFormat(String expectedFormat) {
        System.out.println(DIVIDER);
        System.out.println("Invalid format. Expected:");
        System.out.println("  " + expectedFormat);
        System.out.println(DIVIDER);
    }
}



