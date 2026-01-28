package mintty.parser;

import java.time.LocalDateTime;
import java.time.format.*;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Converts user-input date and time to LocalDateTime format.
 *
 * Supported user-input formats:
 * 2026-01-26 20:00
 * 2026-1-3 20:00
 * 2026-1-26 20
 * 2026.1.26 8pm
 */
public class DateTimeParser {
    private static final DateTimeFormatter FMT_24H = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyy/M/d") // support single number for month/day
            .appendLiteral(' ') // a space is required between date and time
            .appendPattern("H") // hours: from 0 to 24
            .optionalStart() // optional for minutes
            .appendLiteral(':')
            .appendPattern("mm") // minute ranges from 0 to 59
            .optionalEnd()
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0) // if no minute is specified, use 0 by default
            .toFormatter();

    private static final DateTimeFormatter FMT_12H = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyy/M/d") // support single number for month/day
            .appendLiteral(' ') // a space is required between date and time
            .appendPattern("h") // hours: from 0 to 12
            .optionalStart() // optional for minutes
            .appendLiteral(':')
            .appendPattern("mm") // minute ranges from 0 to 59
            .optionalEnd()
            .appendPattern("a") // am / pm
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0) // if no minute is specified, use 0 by default
            .toFormatter(Locale.ENGLISH); // make sure that am/pm is parsed by English

    /**
     * Parses user-input string into standard LocalDateTime.
     *
     * @param raw The raw input line to parse; must be a valid date with valid time
     * @return A {@code LocalDateTime} in the form of yyyy-mm-ddTmm:hh (in 24 hours)
     * @throws IllegalArgumentException if the raw input is of wrong formats, or of invalid values.
     */
    public static LocalDateTime parse(String raw) {
        String s = raw.trim().toLowerCase(Locale.ENGLISH)
                .replace('-', '/')
                .replace('.', '/')
                .replaceAll("\\s+", " ");

        try {
            return LocalDateTime.parse(s, FMT_24H);
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDateTime.parse(s, FMT_12H);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Ooops ... invalid date/time. Some supported examples:\n" +
                            "  2026-1-16 20:00\n" +
                            "  2026/1/26 20\n" +
                            "  2026/1/26 8pm\n" +
                            "  2026.1.26 20\n" +
                            "  Also, remember to give complete time info (year & month & day & time)!"
            );
        }
    }
}
