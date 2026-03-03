package porus.command;

import porus.TaskList;
import porus.UI;
import porus.Storage;
import porus.task.Task;

public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {

        ui.showLine();
        System.out.println("Here are the matching tasks in your list:");

        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            if (t.getDescription().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                count++;
                System.out.println("  " + count + "." + t);
            }
        }

        if (count == 0) {
            System.out.println("  No matching tasks found.");
        }

        ui.showLine();
        return false; // do not exit
    }
}