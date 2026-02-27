package porus;
import porus.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interaction (input and output).
 * Responsible only for printing and reading commands.
 */
public class UI {

    private static final String DIVIDER =
            "----------------------------------------------------------------------------------------------------";

    private final Scanner scanner;

    public UI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads user input.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays the greeting message and logo.
     */
    public void showGreeting() {
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
     * Displays goodbye message.
     */
    public void showGoodbye() {
        System.out.println(DIVIDER);
        System.out.println("Farewell. Glad to be of service!");
        System.out.println(DIVIDER);
    }

    /**
     * Displays task list.
     */
    public void showList(ArrayList<Task> tasks) {
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
     * Displays error messages.
     */
    public void showError(String message) {
        System.out.println(DIVIDER);
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }
}