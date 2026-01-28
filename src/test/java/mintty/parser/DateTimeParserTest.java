package mintty.parser;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeParserTest {
    @Test
    public void parse_invalidFormatDate_throwsIllegalArgumentException() {
        String line = "29.1.30 9am";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> DateTimeParser.parse(line));
        assertEquals("Ooops ... invalid date/time. Some supported examples:\n" +
                "  2026-1-16 20:00\n" +
                "  2026/1/26 20\n" +
                "  2026/1/26 8pm\n" +
                "  2026.1.26 20\n" +
                "  Also, remember to give complete time info (year & month & day & time)!", e.getMessage());
    }
}
