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
    List<Task> list = new ArrayList<>();

    public void run() {
        // start Mintty
        printGreeting();

        // start the storage
        list.addAll(storage.load());

        // entering events
        while (true) {
            try {
                // get user input
                String userInput = readUserInput();

                // Parse the input (command + arg)
                var parsed = CommandParser.lineParser(userInput);
                Command command = parsed.command();
                String arg = parsed.arg(); // may contain by / from / to

                // based on different prompt, do different things
                switch (command) {
                    case BYE :
                        handleExit();
                        return;

                    case LIST :
                        handleList();
                        break;

                    case MARK :
                        int n = CommandParser.parseTaskNumber(arg);
                        handleMark(n, true);
                        break;

                    case UNMARK :
                        int m = CommandParser.parseTaskNumber(arg);
                        handleMark(m, false);
                        break;

                    case TODO :
                        Task todoTask = new Todo(arg);
                        handleTask(todoTask);
                        break;

                    case DEADLINE :
                        var dParts = CommandParser.deadlineParser(arg);
                        Task ddlTask = new Deadline(dParts.des(), dParts.by());
                        handleTask(ddlTask);
                        break;

                    case EVENT :
                        var eParts = CommandParser.eventParser(arg);
                        Task eventTask = new Event(eParts.des(), eParts.from(), eParts.to());
                        handleTask(eventTask);
                        break;

                    case DELETE :
                        int r = CommandParser.parseTaskNumber(arg);
                        handleDelete(r);
                        break;

                    default:
                        throw new IllegalArgumentException("Oops!! I don't know what you're saying TT. Is there a typo?");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(separator);
                System.out.println(e.getMessage());
                System.out.println(separator);
            }

        }


    }

    public void handleExit() {
        printGoodbye();
        closeResources();
    }

    public void handleList() {
        printList();
    }

    public void handleMark(int n, boolean b) {
        if (n <= 0 || n > list.size()) {
            throw new IllegalArgumentException("Oops... It is illegal to enter: " + n + "... plz enter a valid task number again!");
        }
        Task t = updateTaskStatus(n, b);
        if (t == null) {
            if (b) {
                throw new IllegalArgumentException("Oops... you can't mark nothing!");
            } else {
                throw new IllegalArgumentException("Oops... you can't unmark nothing!");
            }
        } else {
            printMarkedTask(t);
            storage.save(list);
        }
    }


    public void handleTask(Task task) {
        String des = task.getDescription();
        if (des == null || des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing task description! TT");
        }

        // print user msg
        printAddedMsg(task);

        // add user input to the list
        addUserInput(task);

        // save the added task
        storage.save(list);
    }

    public void handleDelete(int number) {
        if (number <= 0 || number > list.size()) {
            throw new IllegalArgumentException("Oops... It is illegal to enter: " + number + "... plz enter a valid task number again!");
        }
        printDelete(number);
        list.remove(number - 1);
        storage.save(list);
    }


    public void printGreeting() {
        System.out.println(separator);
        System.out.println("Heyyy this is Mintty ~\nWhat can I do for you?");
        System.out.println(separator);
    }

    public String readUserInput() {
        return  sc.nextLine();
    }

    public void addUserInput(Task task) {
        list.add(task);
    }

    public void printGoodbye() {
        System.out.println(separator);
        System.out.println("Nice to talk to you ^^\nSee you!");
        System.out.println(separator);
    }

    public void closeResources() {
        sc.close();
    }

    public void printAddedMsg(Task task) {
        System.out.println(separator);
        System.out.println("Okie!! I've added this to the task list:\n"
                + task.toString()
                + "\nNow you have " + (list.size()+1) + " tasks in total");
        System.out.println(separator);
    }

    public void printList() {
        int index = 1;
        System.out.println(separator);
        if (list.isEmpty()) {
            System.out.println("There is no task in your list QAQ");
            System.out.println(separator);
            return;
        }

        System.out.println("Here are the tasks in your list: ");
        for (Task t : list) {
            System.out.println(index + "." + t.toString());
            index++;
        }
        System.out.println(separator);
    }

    public Task updateTaskStatus(int number, boolean b) {
        int index = number - 1;
        if (index >= 0 && index < list.size()) {
            Task t = list.get(index);
            if (b) {
                t.setDone();
            } else {
                t.setUndone();
            }
            return t;
        }
        return null;
    }

    public void printMarkedTask(Task t) {
        System.out.println(separator);
        if (t.getStatus()) {
            System.out.println("Niceee! I've marked this task as done: \n" + t.toString());
        } else {
            System.out.println("Okie, I've marked this task as not done yet: \n" + t.toString());
        }
        System.out.println(separator);
    }

    public void printDelete(int number) {
        System.out.println(separator);
        System.out.println("Okie!! I've removed this to from the task list:\n"
                + list.get(number - 1).toString()
                + "\nNow you have " + (list.size() - 1) + " tasks in total.");
        System.out.println(separator);
    }

}
