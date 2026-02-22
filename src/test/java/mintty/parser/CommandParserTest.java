package mintty.parser;

import mintty.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommandParser.
 *
 * Tests ONLY parsing behaviour:
 * - Command recognition
 * - Argument validation
 * - Error message correctness
 *
 * Does NOT test business logic execution.
 */
public class CommandParserTest {

    private CommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CommandParser();
    }

    // =========================================================
    // parse() - basic validation
    // =========================================================

    @Test
    public void parse_nullInput_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(null)
        );

        assertEquals(
                "Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT",
                e.getMessage()
        );
    }

    @Test
    public void parse_emptyInput_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("   ")
        );

        assertEquals(
                "Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT",
                e.getMessage()
        );
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("blahblah")
        );

        assertEquals(
                "Oops!! I don't know what you're saying TT. Is there a typo?",
                e.getMessage()
        );
    }

    // =========================================================
    // Simple command recognition
    // =========================================================

    @Test
    public void parse_exitCommand_returnsExitCommand() {
        Command command = parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parse_listCommand_returnsListCommand() {
        Command command = parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parse_todoCommand_returnsTodoCommand() {
        Command command = parser.parse("todo read book");
        assertTrue(command instanceof TodoCommand);
    }

    // =========================================================
    // parseTaskNumber
    // =========================================================

    @Test
    public void parseTaskNumber_empty_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseTaskNumber("")
        );

        assertEquals("Noo.. Plz provide a task number!", e.getMessage());
    }

    @Test
    public void parseTaskNumber_negative_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseTaskNumber("-5")
        );

        assertEquals("Noo... task number must be positive!", e.getMessage());
    }

    @Test
    public void parseTaskNumber_nonInteger_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseTaskNumber("1.5")
        );

        assertEquals("Noo... task number must be an integer!", e.getMessage());
    }

    // =========================================================
    // Deadline parsing
    // =========================================================

    @Test
    public void parse_deadlineMissingBy_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("deadline homework 2026-1-1 20:00")
        );

        assertEquals("Oops.. missing /by!", e.getMessage());
    }

    @Test
    public void parse_deadlineInvalidDate_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("deadline hw /by invalid-date")
        );

        assertEquals(
                "Invalid date (T^T) Make sure using the format:\n" +
                        "2026.1.26 8pm\n" +
                        "2026-1-26 20:00\n" +
                        "2026/1/26 20",
                e.getMessage()
        );
    }

    // =========================================================
    // Event parsing
    // =========================================================

    @Test
    public void parse_eventMissingTime_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("event meeting")
        );

        assertEquals("Oops.. missing time description!", e.getMessage());
    }

    @Test
    public void parse_eventInvalidDate_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("event meeting /from bad /to worse")
        );

        assertEquals(
                "Invalid date (T^T) Make sure using the format:\n" +
                        "2026.1.26 8pm\n" +
                        "2026-1-26 20:00\n" +
                        "2026/1/26 20",
                e.getMessage()
        );
    }

    // =========================================================
    // Snooze parsing
    // =========================================================

    @Test
    public void parse_snoozeMissingArgument_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("snooze")
        );

        assertEquals("Ooops... missing snooze description! TT", e.getMessage());
    }

    @Test
    public void parse_snoozeInvalidUsage_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("snooze 1 something")
        );

        assertEquals(
                "Usage:\n" +
                        "snooze <index> by <datetime>\n" +
                        "snooze <index> from <datetime>\n" +
                        "snooze <index> to <datetime>\n" +
                        "snooze <index> from <datetime> to <datetime>",
                e.getMessage()
        );
    }

    @Test
    public void parse_snoozeInvalidDate_throwsException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("snooze 1 by bad-date")
        );

        assertEquals(
                "Invalid date (T^T) Make sure using the format:\n" +
                        "2026.1.26 8pm\n" +
                        "2026-1-26 20:00\n" +
                        "2026/1/26 20",
                e.getMessage()
        );
    }
}