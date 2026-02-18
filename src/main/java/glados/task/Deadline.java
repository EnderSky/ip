package glados.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a description and a due date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param description Description of the deadline task.
     * @param by          Due date and time of the deadline task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        assert by != null : "Deadline time should not be null";
        this.tag = 'D';
        this.by = by;
    }

    /**
     * Gets the due date and time of the deadline.
     *
     * @return The due date and time as a LocalDateTime object.
     */
    public LocalDateTime getBy() {
        return this.by;
    }

    /**
     * Gets the deadline status based on the current date and time.
     *
     * @return A string representing the deadline status (Overdue, Due Today, Due
     *         Within a Week, Due in the Future).
     */
    public String getDeadlineStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (this.by.isBefore(now)) {
            return "Overdue";
        } else if (this.by.toLocalDate().isEqual(now.toLocalDate())) {
            return "Due Today";
        } else if (this.by.isBefore(now.plusDays(7))) {
            return "Due Within a Week";
        } else {
            return "Due in the Future";
        }
    }

    /**
     * Gets the due date and time of the deadline.
     *
     * @return Formatted due date and time as a string.
     */
    @Override
    public String getAdditionalInfo() {
        // Return in format: DD MMM YYYY hh:mm am/pm (e.g. 02 Jan 2024 06:00 PM)
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
        return this.by.format(format);
    }

    @Override
    public String toString() {
        // Format LocalDateTime to a readable string
        // Format: DD MMM YYYY HH:mm
        return super.toString() + " (by: " + this.getAdditionalInfo() + ")";
    }
}
