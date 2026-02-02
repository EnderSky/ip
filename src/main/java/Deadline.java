public class Deadline extends Task {
    
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.tag = 'D';
        this.by = by;
    }

    @Override
    public String getAdditionalInfo() {
        return this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
