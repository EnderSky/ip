package glados.task;

public enum DeadlineCategory {
    OVERDUE("Overdue"),
    DUE_TODAY("Due Today"),
    DUE_WITHIN_A_WEEK("Due Within a Week"),
    DUE_IN_THE_FUTURE("Due in the Future");

    private final String displayName;

    DeadlineCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the deadline category.
     *
     * @return The display name as a string.
     */
    @Override
    public String toString() {
        return this.displayName;
    }
}
