import java.util.Scanner;

public class Porus {

    private static final String DIVIDER =
            "--------------------------------------------------";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
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
        System.out.println(DIVIDER + "\n");

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("bye")) {
                System.out.println(DIVIDER);
                System.out.println("Farewell. Glad to be of service!");
                System.out.println(DIVIDER);
                break;
            }

            if (userInput.equals("list")) {
                printList(tasks, taskCount);
                continue;
            }

            if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5).trim());
                int index = taskNumber - 1;

                tasks[index].setDone(true);

                System.out.println(DIVIDER);
                System.out.println("Fantabulous! The deed is done. Marked as complete:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                System.out.println(DIVIDER);
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                int index = taskNumber - 1;

                tasks[index].setDone(false);

                System.out.println(DIVIDER);
                System.out.println("Very well. I shall consider it unfinished:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                System.out.println(DIVIDER);
                continue;
            }

            if (taskCount >= MAX_TASKS) {
                System.out.println(DIVIDER);
                System.out.println("My scroll is full. I cannot record more quests.");
                System.out.println(DIVIDER);
                continue;
            }

            tasks[taskCount] = new Task(userInput);
            taskCount++;

            System.out.println(DIVIDER);
            System.out.println("  added: " + userInput);
            System.out.println(DIVIDER);
        }
    }

    private static void printList(Task[] tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("Bretheren, please complete thy tasks");

        if (taskCount == 0) {
            System.out.println("  (No quests assigned yet.)");
        }

        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
        }

        System.out.println(DIVIDER);
    }
}

