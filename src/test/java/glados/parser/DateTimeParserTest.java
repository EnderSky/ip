package glados.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.format.DateTimeFormatter;

import glados.parser.DateTimeParser;

/**
 * Test class for DateTimeParser.
 */
public class DateTimeParserTest {

    @Test
    public void parseToLocalDateTime_validInput_success() {
        String input1 = "25/12/2024 6:00 PM";
        String input2 = "2/1/2024 1800";
        String input3 = "2024-12-25 06:00 AM";
        String input4 = "2024-1-5 0800";
        String input5 = "2 Dec 2024 06:30 pm";
        String input6 = "5 Jul 2024 0900";

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

        assertEquals("25 Dec 2024 06:00 pm", DateTimeParser.parseToLocalDateTime(input1).format(format).toString());
        assertEquals("02 Jan 2024 06:00 pm", DateTimeParser.parseToLocalDateTime(input2).format(format).toString());
        assertEquals("25 Dec 2024 06:00 am", DateTimeParser.parseToLocalDateTime(input3).format(format).toString());
        assertEquals("05 Jan 2024 08:00 am", DateTimeParser.parseToLocalDateTime(input4).format(format).toString());
        assertEquals("02 Dec 2024 06:30 pm", DateTimeParser.parseToLocalDateTime(input5).format(format).toString());
        assertEquals("05 Jul 2024 09:00 am", DateTimeParser.parseToLocalDateTime(input6).format(format).toString());
    }

    @Test
    public void parseToLocalDateTime_invalidInput_throwsException() {
        String invalidInput = "12/25/2024 6 PM";
        try {
            DateTimeParser.parseToLocalDateTime(invalidInput);
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported datetime format: " + invalidInput, e.getMessage());
        }
    }
}
