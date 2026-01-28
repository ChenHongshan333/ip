package mintty.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * Parses user-input string to command and arguments.
 * Further parsing on arguments are also supported.
 */
public class CommandParser {

    /**
     * Parses a raw input line into a {@link ParsedCommand} consisting of a command word and an argument.
     *
     * <p>The command word is taken as the first token (before the first space). The remaining text
     * after the first space is treated as the argument and is trimmed. If there is no space, the
     * argument is an empty string.</p>
     *
     * @param line The raw input line to parse; must not be {@code null} or blank.
     * @return A {@code ParsedCommand} containing the parsed {@code Command} and argument string.
     * @throws IllegalArgumentException If {@code line} is {@code null} or blank.
     */
    public ParsedCommand lineParser(String line) {
        // no command and arg
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT");
        }

        // only command
        int firstSpace = line.indexOf(' ');
        if (firstSpace == -1) {
            return new ParsedCommand(Command.from(line), "");
        }


        String command = line.substring(0, firstSpace).toLowerCase();
        Command cmd = Command.from(command);
        String arg = line.substring(firstSpace + 1).trim();

        return new ParsedCommand(cmd, arg);
    }


    /**
     * Parses a raw input string into an integer.
     *
     * @param arg The raw input string to parse; must not be {@code null} or blank.
     * @return An integer
     * @throws IllegalArgumentException If {@code arg} is {@code null} or blank, or {@code arg} is negative or float.
     */
    public int parseTaskNumber(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Noo.. Plz provide a mintty.task number!");
        }
        try {
            int n = Integer.parseInt(arg.trim());
            if (n <= 0) {
                throw new IllegalArgumentException("Noo... mintty.task number must be positive!");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Noo... mintty.task number must be an integer!");
        }

    }

    /**
     * Parses a raw input line into a {@link ParsedDeadline} consisting of a task description and a "by" argument.
     *
     * <p>The task description is taken as the first token (before {@code /by}). The remaining text
     * after {@code /by} is treated as the argument and is trimmed.
     * If there is no {@code /by}, an IllegalArgumentException will be thrown.</p>
     *
     * @param arg The raw input line to parse; must not be {@code null} or blank.
     * @return A {@code ParsedDeadline} containing the parsed description string and deadline string.
     * @throws IllegalArgumentException If {@code arg} is {@code null} or blank, or the parsed description string or {@code /by} string is {@code null}.
     */
    public ParsedDeadline deadlineParser(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }

        String lower = arg.toLowerCase();
        int byPos = lower.indexOf("/by");
        if (byPos < 0) {
            // if there is no "/by"
            throw new IllegalArgumentException("Oops.. missing /by!");
        }

        String des = arg.substring(0, byPos).trim();
        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }

        String raw = arg.substring(byPos + 3).trim();
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task deadline! TT");
        }

        try {
            LocalDateTime by = DateTimeParser.parse(raw);
            return new ParsedDeadline(des, by);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n" +
                    "2026.1.26 8pm\n" +
                    "2026-1-26 20:00\n" +
                    "2026/1/26 20");
        }
    }


    /**
     * Parses a raw input line into a {@link ParsedEvent},
     * consisting of a task description, a {@code /from} and a {@code /to} argument.
     *
     * <p>The task description is taken as the first token (before {@code /from}).
     * The starting time (between {@code /from} and {@code /to}) is taken as the second token.
     * The remaining text after {@code /to} is treated as the ending time argument and is trimmed.
     * If any of the above tokens is missing, an IllegalArgumentException will be thrown.</p>
     *
     * @param arg The raw input line to parse; must not be {@code null} or blank.
     * @return A {@code ParsedEvent} containing the parsed description string, starting time and ending time string.
     * @throws IllegalArgumentException If {@code arg} is {@code null} or blank, or any of the above tokens is missing.
     */
    public ParsedEvent eventParser(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }

        String lower = arg.toLowerCase();
        int fromPos = lower.indexOf("/from");
        int toPos = lower.indexOf("/to");

        if (fromPos < 0 || toPos < 0 || toPos <= fromPos) {
            throw new IllegalArgumentException("Oops.. missing time description!");
        }

        String des = arg.substring(0, fromPos).trim();
        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }

        String dateF = arg.substring(fromPos + 5, toPos).trim();
        String dateT = arg.substring(toPos + 3).trim();

        if (dateF.isEmpty() || dateT.isEmpty()) {
            throw new IllegalArgumentException("Oops.. missing time description!");
        }

        try {
            LocalDateTime from = DateTimeParser.parse(dateF);
            LocalDateTime to = DateTimeParser.parse(dateT);
            return new ParsedEvent(des, from, to);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n" +
                    "2026.1.26 8pm\n" +
                    "2026-1-26 20:00\n" +
                    "2026/1/26 20");
        }
    }


    /**
     * Represents a parsed user input consisting of a {@link Command} and its argument string.
     *
     * <p>This is an immutable value object produced by the parser. If the original input contains
     * only a command word, the argument is typically an empty string ({@code ""}).</p>
     */
    public final class ParsedCommand {
        private final Command command;
        private final String arg;

        public ParsedCommand(Command command, String arg) {
            this.command = command;
            this.arg = arg;
        }

        public Command command() {
            return command;
        }

        public String arg() {
            return arg;
        }
    }


    /**
     * Represents a parsed deadline argument input consisting of a description string and its argument string.
     */
    public class ParsedDeadline {
        private final String des;
        private final LocalDateTime by;

        public ParsedDeadline(String des, LocalDateTime by) {
            this.des = des;
            this.by = by;
        }

        public String des() {
            return des;
        }

        public LocalDateTime by() {
            return by;
        }
    }

    /**
     * Represents a parsed event argument input consisting of a description string and its argument string.
     */
    public class ParsedEvent {
        private final String des;
        private final LocalDateTime from;
        private final LocalDateTime to;

        public ParsedEvent(String des, LocalDateTime from, LocalDateTime to) {
            this.des = des;
            this.from = from;
            this.to = to;
        }

        public String des() {
            return des;
        }

        public LocalDateTime from() {
            return from;
        }

        public LocalDateTime to() {
            return to;
        }
    }

}
