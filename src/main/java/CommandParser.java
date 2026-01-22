public class CommandParser {

    public static ParsedCommand lineParser(String line) {
        // no command and arg
        if (line == null) return new ParsedCommand("", "");

        line = line.trim();
        if (line.isEmpty()) return new ParsedCommand("", "");

        // only command
        int firstSpace = line.indexOf(' ');
        if (firstSpace == -1) {
            return new ParsedCommand(line, "");
        }

        String command = line.substring(0, firstSpace);
        String arg = line.substring(firstSpace + 1).trim();
        return new ParsedCommand(command, arg);
    }

    public static final class ParsedCommand {
        private final String command;
        private final String arg;

        public ParsedCommand(String command, String arg) {
            this.command = command;
            this.arg = arg;
        }

        public String command() { return command; }
        public String arg() { return arg; }
    }
}
