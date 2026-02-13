/**
 * Represents a generic task with a description and completion status.
 * Subclasses add task-specific fields (e.g., deadlines, event times).
 */
package porus.task;

public abstract class Task {

    private String description;
    private boolean isDone;

    /**
     * Creates a task with the given description. Tasks start as not done.
     *
     * @param description task description
     */
    public Task(String description) {
        setDescription(description);
        setDone(false);
    }

    /**
     * Returns the description of the task.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the task.
     *
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if done, false otherwise
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done true to mark done, false to mark not done
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns the status icon for printing.
     *
     * @return "X" if done, otherwise " "
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the type icon for the task (e.g., "T", "D", "E").
     *
     * @return task type icon
     */
    public abstract String getTypeIcon();

    /**
     * Returns the core printable form of a task without extra subclass details.
     *
     * @return printable string
     */
    protected String baseString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Returns the task as a printable line for the CLI.
     *
     * @return printable string
     */
    @Override
    public String toString() {
        return baseString();
    }
}

