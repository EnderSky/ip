package glados.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for Event task.
 */
public class EventTest {

    @Test
    public void event_createEvent_success() {
        Event event = new Event("Project meeting", "2024-10-01 14:00", "2024-10-01 16:00");
        assertEquals("[E][ ] Project meeting (from: 2024-10-01 14:00 to: 2024-10-01 16:00)", event.toString());
    }
}
