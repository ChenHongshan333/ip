package mintty;

import java.nio.file.Paths;
import java.util.Scanner;

import mintty.command.Command;
import mintty.parser.CommandParser;
import mintty.storage.Formatter;
import mintty.storage.Storage;
import mintty.task.TaskList;
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
     *
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
     * Initialize the taskList.
     *
     * @return a {@code TaskList}
     */
    private TaskList initializeTaskList() {
        try {
            TaskList loaded = new TaskList();
            loaded.load(storage.load());
            return loaded;
        } catch (IllegalArgumentException e) {
            ui.printException(e.getMessage());
            return new TaskList();
        }
    }

    // an exit flag
    public boolean isShouldExit() {
        return shouldExit;
    }

    public String getResponse(String userInput) {
        try {
            // 1. 解析器直接返回一个具体的 Command 对象 (如 AddCommand, DeleteCommand)
            Command command = parser.parse(userInput);

            // 2. 无脑调用 execute，Mintty 根本不需要知道具体是什么命令
            String response = command.execute(tasks, ui, storage);

            // 3. 更新退出状态
            shouldExit = command.isExit();

            return response;

        } catch (IllegalArgumentException e) {
            return ui.printException(e.getMessage());
        }
    }

    /**
     * Starts the conversation.
     * Actively listens to users' inputs to respond to that.
     */
    public void run() {
        System.out.println(ui.printGreeting());
        Scanner sc = new Scanner(System.in);

        while (!isShouldExit()) {
            String userInput = ui.readUserInput(sc);
            String response = getResponse(userInput);
            System.out.println(response);
        }
        sc.close();
    }
}

