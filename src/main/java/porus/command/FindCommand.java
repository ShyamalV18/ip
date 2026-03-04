package porus.command;

import porus.TaskList;
import porus.UI;
import porus.Storage;
import porus.task.Task;

/**
 * Represents a command that searches for tasks
 * containing a specific keyword as a whole word.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Creates a FindCommand with the specified search keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find operation by searching through all tasks
     * and displaying those that contain the keyword as a whole word.
     *
     * @param tasks The current task list.
     * @param ui The user interface handler.
     * @param storage The storage handler.
     * @return false since this command does not terminate the program.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {

        ui.showLine();
        System.out.println("Here are the matching tasks in your list:");

        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            String[] words = t.getDescription().toLowerCase().split("\\s+");

            for (String word : words) {
                if (word.equals(keyword.toLowerCase())) {
                    count++;
                    System.out.println("  " + count + "." + t);
                    break;
                }
            }
        }

        if (count == 0) {
            System.out.println("  No matching tasks found.");
        }

        ui.showLine();
        return false;
    }
}