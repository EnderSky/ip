public class Task {
    protected String description;
    protected boolean isDone;
    protected char tag;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tag = ' ';
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String toString() {
        if (this.tag == ' ') {
            return "[" + getStatusIcon() + "] " + description;
        }
        return "[" + this.tag + "][" + getStatusIcon() + "] " + description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsNotDone() {
        this.isDone = false;
    }
}
