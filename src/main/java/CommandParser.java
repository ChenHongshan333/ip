public class CommandParser {

    // essentially, a parser based on where the first space is
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

        // standardize the line
        line = line.toLowerCase();

        String command = line.substring(0, firstSpace);
        String arg = line.substring(firstSpace + 1).trim();

        return new ParsedCommand(command, arg);
    }


    public static int parseTaskNumber(String arg) {
        // if there is no arg
        if (arg == "") {
            return -1;
        }
        return Integer.parseInt(arg.trim());
    }

    public static ParsedDeadline deadlineParser(String arg) {
        int byPos = arg.indexOf("/by");
        if (byPos < 0) throw new IllegalArgumentException("Missing /by");

        String des = arg.substring(0, byPos).trim();
        String by = arg.substring(byPos + 3).trim();

        if (des.isEmpty() || by.isEmpty()) throw new IllegalArgumentException("Bad deadline format");
        return new ParsedDeadline(des, by);
    }

    public static ParsedEvent eventParser(String arg) {
        int fromPos = arg.indexOf("/from");
        int toPos = arg.indexOf("/to");
        if (fromPos < 0 || toPos < 0 || toPos <= fromPos) throw new IllegalArgumentException("Bad event format");

        String des = arg.substring(0, fromPos).trim();
        String from = arg.substring(fromPos + 5, toPos).trim();
        String to = arg.substring(toPos + 3).trim();

        if (des.isEmpty() || from.isEmpty() || to.isEmpty()) throw new IllegalArgumentException("Bad event format");
        return new ParsedEvent(des, from, to);
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


    public static class ParsedDeadline {
        private final String des;
        private final String by;

        public ParsedDeadline(String des, String by) {
            this.des = des;
            this.by = by;
        }

        public String des() {return des;}
        public String by() {return by;}
    }

    public static class ParsedEvent {
        private final String des;
        private final String from;
        private final String to;

        public ParsedEvent(String des, String from, String to) {
            this.des = des;
            this.from = from;
            this.to = to;
        }

        public String des() { return des;}
        public String from() { return from;}
        public String to() {return to;}
    }

}
