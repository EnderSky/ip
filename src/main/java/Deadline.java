import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.tag = 'D';
        this.by = by;
    }

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
