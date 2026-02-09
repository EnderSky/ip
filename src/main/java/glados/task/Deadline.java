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
        this.tag = 'D';
        this.by = by;
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
