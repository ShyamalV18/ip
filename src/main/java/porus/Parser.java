package porus;

import porus.command.*;
import porus.task.*;

public class Parser {

    private static final String DEADLINE_BY_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    public static Command parse(String input) throws PorusException {

        if (input.equals("bye")) {
            return new ByeCommand();
        }

        if (input.equals("list")) {
            return new ListCommand();
        }

        if (input.startsWith("mark")) {
            int index = parseIndex(input.substring(4).trim());
            return new MarkCommand(index - 1, true);
        }

        if (input.startsWith("unmark")) {
            int index = parseIndex(input.substring(6).trim());
            return new MarkCommand(index - 1, false);
        }

        if (input.startsWith("delete")) {
            int index = parseIndex(input.substring(6).trim());
            return new DeleteCommand(index - 1);
        }

        if (input.startsWith("todo")) {
            String desc = input.substring(4).trim();
            if (desc.isEmpty()) {
                throw new PorusException("Todo description cannot be empty.");
            }
            return new AddCommand(new Todo(desc));
        }

        if (input.startsWith("deadline")) {
            String rest = input.substring(8).trim();
            int byIndex = rest.indexOf(DEADLINE_BY_DELIMITER);

            if (byIndex < 0) {
                throw new PorusException("Format: deadline DESCRIPTION /by DATE");
            }

            String desc = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + DEADLINE_BY_DELIMITER.length()).trim();

            return new AddCommand(new Deadline(desc, by));
        }

        if (input.startsWith("event")) {
            String rest = input.substring(5).trim();

            int fromIndex = rest.indexOf(EVENT_FROM_DELIMITER);
            int toIndex = rest.indexOf(EVENT_TO_DELIMITER);

            if (fromIndex < 0 || toIndex < 0) {
                throw new PorusException("Format: event DESCRIPTION /from START /to END");
            }

            String desc = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
            String to = rest.substring(toIndex + EVENT_TO_DELIMITER.length()).trim();

            return new AddCommand(new Event(desc, from, to));
        }

        throw new PorusException("I do not understand that command.");
    }

    private static int parseIndex(String number) throws PorusException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new PorusException("Task index must be a valid number.");
        }
    }
}