import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeParser {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
        DateTimeFormatter.ofPattern("d/M/yyyy h:mm a"), // eg. 25/12/2024 6:00 PM
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // eg. 2/1/2024 1800 (24-hour format)
        
        DateTimeFormatter.ofPattern("yyyy-M-d h:mm a"), // eg. 2024-12-25 06:00 AM
        DateTimeFormatter.ofPattern("yyyy-M-d HHmm"), // eg. 2024-1-5 0800 (24-hour format)

        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM yyyy h:mm a") // eg. 2 Dec 2024 06:30 PM
            .toFormatter(),

        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM yyyy HHmm") // eg. 5 Jul 2024 0900 (24-hour format)
            .toFormatter()
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
