import java.nio.file.Paths;
import java.util.*;

public class Mintty {

    public static void main(String[] args) {
        Mintty mintty = new Mintty();
        mintty.run();
    }

    public enum Command {
        BYE("bye", "exit", "quit"),
        LIST("list"),
        TODO("todo", "td"),
        DEADLINE("deadline", "ddl"),
        EVENT("event", "e"),
        MARK("mark", "m"),
        UNMARK("unmark", "u"),
        DELETE("delete","del"),
        UNKNOWN();

        private final Set<String> aliases;

        Command(String... aliases) {
            this.aliases = new HashSet<>();
            this.aliases.addAll(Arrays.asList(aliases));
        }

        public static Command from(String token) {
            if (token == null || token.trim().isEmpty()) return UNKNOWN;
            String t = token.trim().toLowerCase();

            for (Command c : values()) {
                if (c.aliases.contains(t)) return c;
            }
            return UNKNOWN;
        }
    }

    Scanner sc = new Scanner(System.in);
    String separator = "-".repeat(50);
    Storage storage = new Storage(Paths.get("data", "mintty.txt"), new Formatter());

    // a list of tasks
    TaskList list = new TaskList();
    Ui ui = new Ui();

    public void run() {
        // start Mintty
        ui.printGreeting();

        // reload the storage from the result of running Mintty last time
        list.load(storage.load());

        // entering events
        while (true) {
            try {
                // get user input
                String userInput = readUserInput();
                if (userInput == null) {
                    ui.printGoodbye();
                    return;
                }

                // Parse the input (command + arg)
                var parsed = CommandParser.lineParser(userInput);
                Command command = parsed.command();
                String arg = parsed.arg(); // may contain by / from / to

                // based on different prompt, do different things
                switch (command) {
                    case BYE :
                        ui.printGoodbye();
                        sc.close();
                        return;

                    case LIST :
                        ui.printList(list);
                        break;

                    case MARK :
                        int n = CommandParser.parseTaskNumber(arg);
                        Task markedTask = list.setTask(n, true);
                        ui.printMarkedTask(markedTask);
                        storage.save(list.getList());
                        break;

                    case UNMARK :
                        int m = CommandParser.parseTaskNumber(arg);
                        Task unmarkedTask = list.setTask(m, false);
                        ui.printMarkedTask(unmarkedTask);
                        storage.save(list.getList());
                        break;

                    case DELETE :
                        int r = CommandParser.parseTaskNumber(arg);
                        Task removed = list.remove(r);
                        ui.printDelete(removed, list.size());
                        storage.save(list.getList());
                        break;

                    case TODO :
                        Task todoTask = new Todo(arg);
                        list.add(todoTask);
                        // handleTask(todoTask);
                        ui.printAddedMsg(todoTask, list.size());
                        storage.save(list.getList());
                        break;

                    case DEADLINE :
                        var dParts = CommandParser.deadlineParser(arg);
                        Task ddlTask = new Deadline(dParts.des(), dParts.by());
                        // handleTask(ddlTask);
                        list.add(ddlTask);
                        ui.printAddedMsg(ddlTask, list.size());
                        storage.save(list.getList());
                        break;

                    case EVENT :
                        var eParts = CommandParser.eventParser(arg);
                        Task eventTask = new Event(eParts.des(), eParts.from(), eParts.to());
                        // handleTask(eventTask);
                        list.add(eventTask);
                        ui.printAddedMsg(eventTask, list.size());
                        storage.save(list.getList());
                        break;

                    default:
                        throw new IllegalArgumentException("Oops!! I don't know what you're saying TT. Is there a typo?");
                }

            } catch (IllegalArgumentException e) {
                ui.printException(e.getMessage());
            }
        }


    }


//    public void handleTask(Task task) {
//        String des = task.getDescription();
//        if (des == null || des.isEmpty()) {
//            throw new IllegalArgumentException("Ooops... missing task description! TT");
//        }
//
//        list.add(task);
//        Ui.printAddedMsg(task, list.size());
//        storage.save(list.getList());
//    }

    public String readUserInput() {
        if (!sc.hasNextLine()) {
            return null;
        }
        return sc.nextLine();
    }

}
