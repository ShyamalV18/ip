package porus.task;

/**
 * A task that occurs during a time window (stored as text).
 */
public class Event extends Task {

    private String from;
    private String to;

    /**
     * Creates an porus.task.Event task.
     *
     * @param description event description
     * @param from start time detail (e.g., "Mon 2pm")
     * @param to end time detail (e.g., "Mon 4pm")
     */
    public Event(String description, String from, String to) {
        super(description);
        setFrom(from);
        setTo(to);
    }

    /**
     * Returns the start time detail.
     *
     * @return start detail
     */
    public String getFrom() {
        return from;
    }

    /**
     * Updates the start time detail.
     *
     * @param from new start detail
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Returns the end time detail.
     *
     * @return end detail
     */
    public String getTo() {
        return to;
    }

    /**
     * Updates the end time detail.
     *
     * @param to new end detail
     */
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return baseString() + " (from: " + from + " to: " + to + ")";
    }
}


