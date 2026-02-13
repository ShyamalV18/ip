package porus.task;
/**
 * A todo task that has only a description.
 */


public class Todo extends Task {

    /**
     * Creates a porus.task.Todo task.
     *
     * @param description description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }
}

