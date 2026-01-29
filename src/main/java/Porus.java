import java.util.Scanner;

public class Porus {

    private static final String DIVIDER =
            "--------------------------------------------------";

    public static void main(String[] args) {
        String logo =
                " ____    ___    ____   _   _    ____ \n"
                        + "|  _ \\  / _ \\  |  _ \\ | | | |  / ___|\n"
                        + "| |_) || | | | | |_) || | | |  \\___ \\\n"
                        + "|  __/ | |_| | |  _ < | |_| |   ___) |\n"
                        + "|_|     \\___/  |_| \\_\\ \\___/   |____/\n";

        printGreeting(logo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println(DIVIDER);
                System.out.println("Farewell. Glad to be of service!");
                System.out.println(DIVIDER);
                break;
            }

            System.out.println(DIVIDER);
            System.out.println("  " + userInput);
            System.out.println(DIVIDER);
        }
    }

    private static void printGreeting(String logo) {
        System.out.println("Greetings! I'm");
        System.out.println(logo);
        System.out.println("How may I assist you today?");
        System.out.println(DIVIDER);
    }
}


