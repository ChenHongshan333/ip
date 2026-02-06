package mintty;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import mintty.parser.Command;
import mintty.parser.CommandParser;
import mintty.storage.Formatter;
import mintty.storage.Storage;
import mintty.task.Deadline;
import mintty.task.Event;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.task.Todo;
import mintty.ui.Ui;

/**
 * Represents a chatbot names Mintty.
 * Simple conversations are available using CLI.
 *
 * @author Hongshan
 * @version 0.1
 * @since 0.1
 */
public class Mintty {

    private final Storage storage;
    private final TaskList list;
    private final Ui ui;
    private final CommandParser parser;
    private boolean shouldExit = false;

    /**
     * Constructor for Mintty
     * @param filePath
     */
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

    /**
     * Starts the conversation.
     * Actively listens to users' inputs to respond to that.
     */
    public void run() {
        System.out.println(ui.printGreeting());
        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = ui.readUserInput(sc);
            String response = getResponse(userInput);
            System.out.println(response);

            if (isShouldExit()) {
                sc.close();
                return;
            }
        }
    }

    // an exit flag
    public boolean isShouldExit() {
        return shouldExit;
    }

    public String getResponse(String userInput) {
        shouldExit = false;

        try {
            if (userInput == null) {
                shouldExit = true;
                return ui.printGoodbye();
            }

            // Parse the input (command + arg)
            var parsed = parser.lineParser(userInput);
            Command command = parsed.command();
            String arg = parsed.arg(); // may contain by / from / to

            // based on different prompt, do different things
            switch (command) {
            case BYE:
                shouldExit = true;
                return ui.printGoodbye();

            case LIST:
                return ui.printList(list);

            case MARK: {
                int n = parser.parseTaskNumber(arg);
                Task markedTask = list.setTask(n, true);
                storage.save(list.getList());
                return ui.printMarkedTask(markedTask);
            }

            case UNMARK: {
                int m = parser.parseTaskNumber(arg);
                Task unmarkedTask = list.setTask(m, false);
                storage.save(list.getList());
                return ui.printMarkedTask(unmarkedTask);
            }


            case FIND: {
                List<Task> foundList = list.find(arg);
                return ui.printFind(foundList);
            }


            case DELETE: {
                int r = parser.parseTaskNumber(arg);
                Task removed = list.remove(r);
                storage.save(list.getList());
                return ui.printDelete(removed, list.size());
            }


            case TODO: {
                Task todoTask = new Todo(arg);
                list.add(todoTask);
                storage.save(list.getList());
                return ui.printAddedMsg(todoTask, list.size());
            }


            case DEADLINE: {
                var dParts = parser.deadlineParser(arg);
                Task ddlTask = new Deadline(dParts.des(), dParts.by());
                list.add(ddlTask);
                storage.save(list.getList());
                return ui.printAddedMsg(ddlTask, list.size());
            }


            case EVENT: {
                var eParts = parser.eventParser(arg);
                Task eventTask = new Event(eParts.des(), eParts.from(), eParts.to());
                list.add(eventTask);
                storage.save(list.getList());
                return ui.printAddedMsg(eventTask, list.size());
            }

            default:
                throw new IllegalArgumentException("Oops!! I don't know what you're saying TT. Is there a typo?");
            }

        } catch (IllegalArgumentException e) {
            return ui.printException(e.getMessage());
        }
    }

//    /**
//     * Main function.
//     */
//    public static void main(String[] args) {
//        Mintty mintty = new Mintty("data/mintty.txt");
//        mintty.run();
//    }


}
