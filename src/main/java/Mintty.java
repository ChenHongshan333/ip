import java.nio.file.Paths;
import java.util.*;

public class Mintty {

    private final Storage storage;
    private final TaskList list;
    private final Ui ui;
    private final CommandParser parser;

    public Mintty(String filePath) {
        ui = new Ui();
        storage = new Storage(Paths.get(filePath), new Formatter());
        parser = new CommandParser();

        TaskList loaded;

        try {
            loaded = new TaskList();
            // when initialized, reload the content of mintty.txt (the result of last run) to the current taskList
            loaded.load(storage.load());
        } catch (IllegalArgumentException e) {
            ui.printException(e.getMessage());
            loaded = new TaskList();
        }
        list = loaded;
    }


    public void run() {
        // start Mintty
        ui.printGreeting();

        // create scanner
        Scanner sc = new Scanner(System.in);

        // entering events
        while (true) {
            try {
                // get user input
                String userInput = ui.readUserInput(sc);
                if (userInput == null) {
                    ui.printGoodbye();
                    sc.close();
                    return;
                }

                // Parse the input (command + arg)
                var parsed = parser.lineParser(userInput);
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
                        int n = parser.parseTaskNumber(arg);
                        Task markedTask = list.setTask(n, true);
                        ui.printMarkedTask(markedTask);
                        storage.save(list.getList());
                        break;

                    case UNMARK :
                        int m = parser.parseTaskNumber(arg);
                        Task unmarkedTask = list.setTask(m, false);
                        ui.printMarkedTask(unmarkedTask);
                        storage.save(list.getList());
                        break;

                    case DELETE :
                        int r = parser.parseTaskNumber(arg);
                        Task removed = list.remove(r);
                        ui.printDelete(removed, list.size());
                        storage.save(list.getList());
                        break;

                    case TODO :
                        Task todoTask = new Todo(arg);
                        list.add(todoTask);
                        ui.printAddedMsg(todoTask, list.size());
                        storage.save(list.getList());
                        break;

                    case DEADLINE :
                        var dParts = parser.deadlineParser(arg);
                        Task ddlTask = new Deadline(dParts.des(), dParts.by());
                        list.add(ddlTask);
                        ui.printAddedMsg(ddlTask, list.size());
                        storage.save(list.getList());
                        break;

                    case EVENT :
                        var eParts = parser.eventParser(arg);
                        Task eventTask = new Event(eParts.des(), eParts.from(), eParts.to());
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

    public static void main(String[] args) {
        Mintty mintty = new Mintty("data/mintty.txt");
        mintty.run();
    }


}
