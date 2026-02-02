import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeParser {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
        DateTimeFormatter.ofPattern("d/M/yyyy h:mm a"), // eg. 25/12/2024 06:00 PM
        DateTimeFormatter.ofPattern("yyyy-M-d h:mm a") // eg. 2024-12-25 06:00 PM

        /*
        Dates to test:
        - 25/12/2024 06:00 PM
        - 5/1/2024 9:30 AM
        - 2024-12-25 06:00 PM
        - 2024-1-5 9:30 AM
        */
    );

    public static LocalDateTime parseToLocalDateTime(String input) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new IllegalArgumentException("Unsupported datetime format: " + input);
    }
}
