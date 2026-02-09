package glados.task;

/**
 * Represents an Event task with a description, start time, and end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Constructs an Event task.
     *
     * @param description Description of the event.
     * @param from        Start time of the event.
     * @param to          End time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.tag = 'E';
        this.from = from;
        this.to = to;
    }

    /**
     * Gets from and to information of the event.
     */
    @Override
    public String getAdditionalInfo() {
        return this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
