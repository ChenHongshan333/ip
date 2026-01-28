package mintty.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandParserTest {
    @Test
    public void lineParser_emptyDescription_throwsIllegalArgumentException() {
        String line = "";
        CommandParser commandParser = new CommandParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> commandParser.lineParser(line));
        assertEquals("Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT",
                e.getMessage());
    }

    @Test
    public void parseTaskNumber_emptyArgument_throwsIllegalArgumentException() {
        String arg = "";
        CommandParser commandParser = new CommandParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> commandParser.parseTaskNumber(arg));
        assertEquals("Noo.. Plz provide a mintty.task number!", e.getMessage());
    }

    @Test
    public void parseTaskNumber_negativeNumber_throwsIllegalArgumentException() {
        String arg = "-9";
        CommandParser commandParser = new CommandParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> commandParser.parseTaskNumber(arg));
        assertEquals("Noo... mintty.task number must be positive!", e.getMessage());
    }

    @Test
    public void parseTaskNumber_floatNumber_throwsIllegalArgumentException() {
        String arg = "1.8";
        CommandParser commandParser = new CommandParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> commandParser.parseTaskNumber(arg));
        assertEquals("Noo... mintty.task number must be an integer!", e.getMessage());
    }

    @Test
    public void deadlineParser_invalidDateFormat_throwsIllegalArgumentException() {
        String line = "2026;1;29 /by 9pm";
        CommandParser commandParser = new CommandParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> commandParser.deadlineParser(line));
        assertEquals("Ooops ... invalid date/time. Some supported examples:\n" +
                "  2026-1-16 20:00\n" +
                "  2026/1/26 20\n" +
                "  2026/1/26 8pm\n" +
                "  2026.1.26 20\n" +
                "  Also, remember to give complete time info (year & month & day & time)!",
                e.getMessage());
    }
}
