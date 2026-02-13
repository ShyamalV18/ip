import java.util.Scanner;

/**
 * Entry point and command loop for the Porus chatbot.
 * Reads user commands, creates tasks, marks/unmarks tasks, and lists tasks.
 */
public class Porus {

    private static final String DIVIDER = "----------------------------------------------------------------------------------------------------";
    private static final int MAX_TASKS = 100;

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

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

            try {
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
                    throw new PorusException("My scroll is full. I cannot record more quests.");
                }

                Task newTask = parseTask(userInput);

                tasks[taskCount++] = newTask;

                System.out.println(DIVIDER);
                System.out.println("  added: " + newTask);
                System.out.println("  Now you have " + taskCount + " tasks in the list.");
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
     * Parses user input into a Task object.
     *
     * @param userInput raw user input line
     * @return created Task
     * @throws PorusException if command format is invalid
     */
    private static Task parseTask(String userInput) throws PorusException {

        if (userInput.startsWith(COMMAND_TODO)) {
            String description = userInput.substring(COMMAND_TODO.length()).trim();
            if (description.isEmpty()) {
                throw new PorusException("The description of a todo cannot be empty bretheren.");
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

        throw new PorusException("I do not understand that command. I will make you regret your existence.");
    }

    /**
     * Marks or unmarks a task by index.
     *
     * @param tasks task array
     * @param taskCount number of tasks stored
     * @param userInput user command
     * @param isDone true to mark done, false to unmark
     * @throws PorusException if index is invalid
     */
    private static void handleMark(Task[] tasks, int taskCount,
                                   String userInput, boolean isDone)
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

        if (index < 0 || index >= taskCount) {
            throw new PorusException("Invalid task number. Please rethink thy life choices.");
        }

        tasks[index].setDone(isDone);

        System.out.println(DIVIDER);
        System.out.println("  " + tasks[index]);
        System.out.println(DIVIDER);
    }

    /**
     * Prints the current list of tasks.
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



