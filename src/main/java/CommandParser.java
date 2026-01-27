import java.time.DateTimeException;
import java.time.LocalDateTime;

public class CommandParser {

    // essentially, a parser based on where the first space is
    // [command arg] -> [command] + [arg]
    public ParsedCommand lineParser(String line) {
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


    public int parseTaskNumber(String arg) {
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

    // [des + by] -> [des] + [by]
    public ParsedDeadline deadlineParser(String arg) {
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
        if (des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        String raw = arg.substring(byPos + 3).trim();
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task deadline! TT");
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

    // [arg] -> [des] + [to] + [from]
    public ParsedEvent eventParser(String arg) {
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
            return new ParsedEvent(des, from, to);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date (T^T) Make sure using the format:\n" +
                    "2026.1.26 8pm\n" +
                    "2026-1-26 20:00\n" +
                    "2026/1/26 20");
        }
    }


    public final class ParsedCommand {
        private final Mintty.Command command;
        private final String arg;

        public ParsedCommand(Mintty.Command command, String arg) {
            this.command = command;
            this.arg = arg;
        }

        public Mintty.Command command() { return command; }
        public String arg() { return arg; }
    }


    public class ParsedDeadline {
        private final String des;
        private final LocalDateTime by;

        public ParsedDeadline(String des, LocalDateTime by) {
            this.des = des;
            this.by = by;
        }

        public String des() {return des;}
        public LocalDateTime by() {return by;}
    }

    public class ParsedEvent {
        private final String des;
        private final LocalDateTime from;
        private final LocalDateTime to;

        public ParsedEvent(String des, LocalDateTime from, LocalDateTime to) {
            this.des = des;
            this.from = from;
            this.to = to;
        }

        public String des() { return des;}
        public LocalDateTime from() { return from;}
        public LocalDateTime to() {return to;}
    }

}
