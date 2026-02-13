package porus.task;

/**
 * A task that must be completed by a specific time (stored as text).
 */
public class Deadline extends Task {

    private String by;

    /**
     * Creates a porus.task.Deadline task.
     *
     * @param description task description
     * @param by deadline detail (e.g., "Sunday", "2026-02-10 1800")
     */
    public Deadline(String description, String by) {
        super(description);
        setBy(by);
    }

    /**
     * Returns the deadline detail.
     *
     * @return deadline detail
     */
    public String getBy() {
        return by;
    }

    /**
     * Updates the deadline detail.
     *
     * @param by new deadline detail
     */
    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return baseString() + " (by: " + by + ")";
    }
}

