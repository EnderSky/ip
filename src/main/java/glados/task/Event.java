package glados.task;
public class Event extends Task {

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.tag = 'E';
        this.from = from;
        this.to = to;
    }

    @Override
    public String getAdditionalInfo() {
        return this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
