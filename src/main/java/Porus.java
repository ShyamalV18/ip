import java.util.Scanner;

public class Porus {

    private static final String DIVIDER =
            "--------------------------------------------------";

    // Max number of tasks the chatbot can store
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String logo =
                " ____    ___    ____   _   _    ____ \n"
                        + "|  _ \\  / _ \\  |  _ \\ | | | |  / ___|\n"
                        + "| |_) || | | | | |_) || | | |  \\___ \\\n"
                        + "|  __/ | |_| | |  _ < | |_| |   ___) |\n"
                        + "|_|     \\___/  |_| \\_\\ \\___/   |____/\n";

        // Prints chatbot greeting at startup.
        System.out.println("Greetings! I'm");
        System.out.println(logo);
        System.out.println("(Personally Operating Real Understanding Service)");
        System.out.println("How may I assist you today?");
        System.out.println(DIVIDER + "\n");

        Scanner scanner = new Scanner(System.in);

        // Array to store tasks (can store Todo/Deadline/Event because all are Tasks)
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        // Main loop: keep reading commands until user says "bye"
        while (true) {
            String userInput = scanner.nextLine().trim();

            // Exit command
            if (userInput.equals("bye")) {
                System.out.println(DIVIDER);
                System.out.println("Farewell. Glad to be of service!");
                System.out.println(DIVIDER);
                break;
            }

            // List all stored tasks
            if (userInput.equals("list")) {
                printList(tasks, taskCount);
                continue;
            }

            // Mark task as done: "mark 2"
            if (userInput.startsWith("mark ")) {
                String numberPart = userInput.substring(5).trim();

                if (!numberPart.matches("\\d+")) {
                    System.out.println(DIVIDER);
                    System.out.println("Kindly provide a valid task number to mark.");
                    System.out.println(DIVIDER);
                    continue;
                }

                int taskNumber = Integer.parseInt(numberPart);
                int index = taskNumber - 1;

                if (index < 0 || index >= taskCount) {
                    System.out.println(DIVIDER);
                    System.out.println("That task number does not exist.");
                    System.out.println(DIVIDER);
                    continue;
                }

                tasks[index].setDone(true);

                System.out.println(DIVIDER);
                System.out.println("Fantabulous! The deed is done. Marked as complete:");
                System.out.println("  " + tasks[index]);
                System.out.println(DIVIDER);
                continue;
            }

            // Unmark task: "unmark 2"
            if (userInput.startsWith("unmark ")) {
                String numberPart = userInput.substring(7).trim();

                if (!numberPart.matches("\\d+")) {
                    System.out.println(DIVIDER);
                    System.out.println("Kindly provide a valid task number to unmark.");
                    System.out.println(DIVIDER);
                    continue;
                }

                int taskNumber = Integer.parseInt(numberPart);
                int index = taskNumber - 1;

                if (index < 0 || index >= taskCount) {
                    System.out.println(DIVIDER);
                    System.out.println("That task number does not exist.");
                    System.out.println(DIVIDER);
                    continue;
                }

                tasks[index].setDone(false);

                System.out.println(DIVIDER);
                System.out.println("Very well. I shall consider it unfinished:");
                System.out.println("  " + tasks[index]);
                System.out.println(DIVIDER);
                continue;
            }

            // Prevent adding more tasks if storage is full
            if (taskCount >= MAX_TASKS) {
                System.out.println(DIVIDER);
                System.out.println("My scroll is full. I cannot record more quests.");
                System.out.println(DIVIDER);
                continue;
            }

            // LEVEL 4: Add task types (Todo / Deadline / Event)
            // todo <description>
            if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5).trim();

                if (description.isEmpty()) {
                    System.out.println(DIVIDER);
                    System.out.println("A todo needs a description, brave one.");
                    System.out.println(DIVIDER);
                    continue;
                }

                tasks[taskCount] = new Todo(description);
                taskCount++;

                System.out.println(DIVIDER);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER);
                continue;
            }

            // deadline <description> /by <by>
            if (userInput.startsWith("deadline ")) {
                String rest = userInput.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);

                if (parts.length < 2) {
                    System.out.println(DIVIDER);
                    System.out.println("Format: deadline <description> /by <by>");
                    System.out.println(DIVIDER);
                    continue;
                }

                String description = parts[0].trim();
                String by = parts[1].trim();

                if (description.isEmpty() || by.isEmpty()) {
                    System.out.println(DIVIDER);
                    System.out.println("A deadline needs BOTH a description and a /by value.");
                    System.out.println(DIVIDER);
                    continue;
                }

                tasks[taskCount] = new Deadline(description, by);
                taskCount++;

                System.out.println(DIVIDER);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER);
                continue;
            }

            // event <description> /from <from> /to <to>
            if (userInput.startsWith("event ")) {
                String rest = userInput.substring(6).trim();
                String[] firstSplit = rest.split(" /from ", 2);

                if (firstSplit.length < 2) {
                    System.out.println(DIVIDER);
                    System.out.println("Format: event <description> /from <from> /to <to>");
                    System.out.println(DIVIDER);
                    continue;
                }

                String description = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if (secondSplit.length < 2) {
                    System.out.println(DIVIDER);
                    System.out.println("Format: event <description> /from <from> /to <to>");
                    System.out.println(DIVIDER);
                    continue;
                }

                String from = secondSplit[0].trim();
                String to = secondSplit[1].trim();

                if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    System.out.println(DIVIDER);
                    System.out.println("An event needs description, /from, and /to.");
                    System.out.println(DIVIDER);
                    continue;
                }

                tasks[taskCount] = new Event(description, from, to);
                taskCount++;

                System.out.println(DIVIDER);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER);
                continue;
            }

            // If user didn't type todo/deadline/event, treat it as a normal Todo-style task
            tasks[taskCount] = new Todo(userInput);
            taskCount++;

            System.out.println(DIVIDER);
            System.out.println("  added: " + userInput);
            System.out.println(DIVIDER);
        }

        // Good practice to close scanner when done
        scanner.close();
    }

    // Prints current list of tasks with numbering and status/type (uses each task's toString)
    private static void printList(Task[] tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("Bretheren, please complete thy tasks");

        // Handle empty task list
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
}



