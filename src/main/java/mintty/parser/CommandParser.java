package mintty.parser;

import mintty.command.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * Parses raw user input into concrete {@link Command} objects.
 *
 * <p>This class is responsible ONLY for:
 * <ul>
 *     <li>Validating input syntax</li>
 *     <li>Extracting parameters</li>
 *     <li>Constructing appropriate Command objects</li>
 * </ul>
 *
 * <p>It does NOT execute any business logic.
 * All business logic is encapsulated inside Command implementations.
 */
public class CommandParser {

    /**
     * Parses raw user input into a concrete {@link Command}.
     *
     * <p>The first token is treated as the command word.
     * The remaining text is passed as argument to specific parsers.</p>
     *
     * @param input Raw user input.
     * @return A concrete Command object ready for execution.
     * @throws IllegalArgumentException If input is invalid or command is unknown.
     */
    public Command parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT");
        }

        String[] parts = input.trim().split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {

        case "bye":
        case "exit":
        case "quit":
            return new ExitCommand();

        case "list":
        case "l":
            return new ListCommand();

        case "todo":
        case "td":
            return parseTodo(arg);

        case "deadline":
        case "ddl":
            return parseDeadline(arg);

        case "event":
        case "e":
            return parseEvent(arg);

        case "delete":
        case "del":
            return new DeleteCommand(parseTaskNumber(arg));

        case "mark":
        case "m":
            return new MarkCommand(parseTaskNumber(arg));

        case "unmark":
        case "u":
            return new UnmarkCommand(parseTaskNumber(arg));

        case "find":
        case "f":
            return new FindCommand(arg);

        case "snooze":
        case "s":
            return parseSnooze(arg);

        default:
            throw new IllegalArgumentException(
                    "Oops!! I don't know what you're saying TT. Is there a typo?");
        }
    }

    /**
     * Parses a todo command argument.
     *
     * @param arg Argument string after command word.
     * @return TodoCommand
     * @throws IllegalArgumentException If description is missing.
     */
    public Command parseTodo(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }
        return new TodoCommand(arg.trim());
    }

    /**
     * Parses a deadline command argument.
     *
     * @param arg Raw argument string.
     * @return DeadlineCommand
     * @throws IllegalArgumentException If format or datetime is invalid.
     */
    public Command parseDeadline(String arg) {

        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String lower = arg.toLowerCase();
        int byPos = lower.indexOf("/by");

        if (byPos < 0) {
            throw new IllegalArgumentException("Oops.. missing /by!");
        }

        String des = arg.substring(0, byPos).trim();
        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String raw = arg.substring(byPos + 3).trim();
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task deadline! TT");
        }

        try {
            LocalDateTime by = DateTimeParser.parse(raw);
            return new DeadlineCommand(des, by);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n"
                    + "2026.1.26 8pm\n"
                    + "2026-1-26 20:00\n"
                    + "2026/1/26 20");
        }
    }

    /**
     * Parses an event command argument.
     *
     * @param arg Raw argument string.
     * @return EventCommand
     * @throws IllegalArgumentException If format or datetime is invalid.
     */
    public Command parseEvent(String arg) {

        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String lower = arg.toLowerCase();
        int fromPos = lower.indexOf("/from");
        int toPos = lower.indexOf("/to");

        if (fromPos < 0 || toPos < 0 || toPos <= fromPos) {
            throw new IllegalArgumentException("Oops.. missing time description!");
        }

        String des = arg.substring(0, fromPos).trim();
        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String dateF = arg.substring(fromPos + 5, toPos).trim();
        String dateT = arg.substring(toPos + 3).trim();

        if (dateF.isEmpty() || dateT.isEmpty()) {
            throw new IllegalArgumentException("Oops.. missing time description!");
        }

        try {
            LocalDateTime from = DateTimeParser.parse(dateF);
            LocalDateTime to = DateTimeParser.parse(dateT);
            return new EventCommand(des, from, to);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n"
                    + "2026.1.26 8pm\n"
                    + "2026-1-26 20:00\n"
                    + "2026/1/26 20");
        }
    }

    /**
     * Parses snooze command argument.
     *
     * <p>Supported formats:
     * <pre>
     * snooze 1 by 2026-1-1 20:00
     * snooze 2 from 2026-1-1 10:00
     * snooze 2 to 2026-1-1 12:00
     * snooze 2 from 2026-1-1 10:00 to 2026-1-1 12:00
     * </pre>
     *
     * @param arg Raw argument string.
     * @return SnoozeCommand
     * @throws IllegalArgumentException If format is invalid.
     */
    public Command parseSnooze(String arg) {

        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing snooze description! TT");
        }

        String[] parts = arg.trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: snooze <index> by/from/to ...");
        }

        int index = parseTaskNumber(parts[0]);
        String tail = parts[1].trim();
        String lower = tail.toLowerCase();

        try {

            if (lower.startsWith("by ")) {
                LocalDateTime by = DateTimeParser.parse(tail.substring(3).trim());
                return new SnoozeCommand(index, by, null, null);
            }

            if (lower.startsWith("to ")) {
                LocalDateTime to = DateTimeParser.parse(tail.substring(3).trim());
                return new SnoozeCommand(index, null, null, to);
            }

            if (lower.startsWith("from ")) {

                String afterFrom = tail.substring(5).trim();
                int toPos = indexOfToken(afterFrom, "to");

                if (toPos >= 0) {
                    String fromStr = afterFrom.substring(0, toPos).trim();
                    String toStr = afterFrom.substring(toPos + 2).trim();

                    LocalDateTime from = DateTimeParser.parse(fromStr);
                    LocalDateTime to = DateTimeParser.parse(toStr);

                    return new SnoozeCommand(index, null, from, to);
                }

                LocalDateTime from = DateTimeParser.parse(afterFrom);
                return new SnoozeCommand(index, null, from, null);
            }

        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n"
                    + "2026.1.26 8pm\n"
                    + "2026-1-26 20:00\n"
                    + "2026/1/26 20");
        }

        throw new IllegalArgumentException("Usage:\n"
                + "snooze <index> by <datetime>\n"
                + "snooze <index> from <datetime>\n"
                + "snooze <index> to <datetime>\n"
                + "snooze <index> from <datetime> to <datetime>");
    }

    /**
     * Parses task number and validates it.
     *
     * @param arg Raw string.
     * @return Positive integer index.
     * @throws IllegalArgumentException If invalid.
     */
    public int parseTaskNumber(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Noo.. Plz provide a task number!");
        }

        try {
            int taskNumber = Integer.parseInt(arg.trim());
            if (taskNumber <= 0) {
                throw new IllegalArgumentException("Noo... task number must be positive!");
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Noo... task number must be an integer!");
        }
    }

    /**
     * Finds index of a token within a string using whitespace separation.
     *
     * @param s Full string.
     * @param token Token to search.
     * @return Starting position of token, or -1 if not found.
     */
    private int indexOfToken(String s, String token) {
        String[] tokens = s.split("\\s+");
        int pos = 0;

        for (String t : tokens) {
            if (t.equalsIgnoreCase(token)) {
                return pos;
            }
            pos += t.length() + 1;
        }
        return -1;
    }
}