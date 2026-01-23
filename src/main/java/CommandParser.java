public class CommandParser {

    // essentially, a parser based on where the first space is
    public static ParsedCommand lineParser(String line) {
        // no command and arg
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Hey we're in a conversation...! You can't expect me to reply with you saying nothing TT");
        }

        // only command
        int firstSpace = line.indexOf(' ');
        if (firstSpace == -1) {
            return new ParsedCommand(Mintty.Command.from(line), "");
        }


        String command = line.substring(0, firstSpace).toLowerCase();
        Mintty.Command cmd = Mintty.Command.from(command);
        String arg = line.substring(firstSpace + 1).trim();

        return new ParsedCommand(cmd, arg);
    }


    public static int parseTaskNumber(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Noo.. Plz provide a task number!");
        }
        try {
            int n = Integer.parseInt(arg.trim());
            if (n <= 0) {
                throw new IllegalArgumentException("Noo... task number must be positive!");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Noo... task number must be an integer!");
        }

    }

    public static ParsedDeadline deadlineParser(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String lower = arg.toLowerCase();
        int byPos = lower.indexOf("/by");
        if (byPos < 0) {
            // if there is no "/by"
            throw new IllegalArgumentException("Oops.. missing /by!");
        }

        String des = arg.substring(0, byPos).trim();
        String by = arg.substring(byPos + 3).trim();

        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        if (by.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task deadline! TT");
        }

        return new ParsedDeadline(des, by);
    }

    public static ParsedEvent eventParser(String arg) {
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
        String from = arg.substring(fromPos + 5, toPos).trim();
        String to = arg.substring(toPos + 3).trim();

        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Oops.. missing time description!");
        }

        return new ParsedEvent(des, from, to);
    }


    public static final class ParsedCommand {
        private final Mintty.Command command;
        private final String arg;

        public ParsedCommand(Mintty.Command command, String arg) {
            this.command = command;
            this.arg = arg;
        }

        public Mintty.Command command() { return command; }
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
