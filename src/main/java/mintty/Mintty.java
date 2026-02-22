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
 * Represents the Mintty chatbot application.
 *
 * <p>Mintty is a CLI-based task management chatbot that supports
 * adding, deleting, marking, finding, and modifying tasks.</p>
 *
 * <p>This class acts as the application entry point and orchestrator.
 * It is responsible for:</p>
 * <ul>
 *     <li>Initializing core components (UI, Storage, Parser, TaskList)</li>
 *     <li>Reading user input</li>
 *     <li>Delegating command parsing</li>
 *     <li>Executing commands</li>
 *     <li>Managing application lifecycle</li>
 * </ul>
 *
 * <p>No business logic is implemented here. All business logic is
 * encapsulated inside concrete {@link Command} implementations.</p>
 *
 * @author Hongshan
 * @version 2.0
 * @since 0.1
 */
public class Mintty {

    /** Handles user interaction formatting and input/output. */
    private final Ui ui;

    /** Handles persistent storage of tasks. */
    private final Storage storage;

    /** Maintains the in-memory list of tasks. */
    private final TaskList taskList;

    /** Responsible for parsing user input into Command objects. */
    private final CommandParser parser;

    /** Indicates whether the application should terminate. */
    private boolean shouldExit;

    /**
     * Constructs a Mintty application instance.
     *
     * <p>Initializes all core components and loads previously
     * saved tasks from storage if available.</p>
     *
     * @param filePath Path to the persistent storage file.
     */
    public Mintty(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(Paths.get(filePath), new Formatter());
        this.parser = new CommandParser();
        this.taskList = loadTasks();
        this.shouldExit = false;
    }

    /**
     * Loads tasks from storage into a TaskList.
     *
     * <p>If loading fails due to invalid stored data,
     * an empty TaskList will be created instead.</p>
     *
     * @return Initialized TaskList.
     */
    private TaskList loadTasks() {
        TaskList list = new TaskList();
        try {
            list.load(storage.load());
        } catch (IllegalArgumentException e) {
            ui.printException(e.getMessage());
        }
        return list;
    }

    /**
     * Checks if the application should exit based on the last executed command.
     */
    public boolean hasExit() {
        return this.shouldExit;
    }

    /**
     * Processes a single user input and returns a response string.
     *
     * <p>The workflow is:
     * <ol>
     *     <li>Parse input into a concrete Command</li>
     *     <li>Execute the Command</li>
     *     <li>Update exit state if necessary</li>
     * </ol>
     *
     * @param userInput Raw input from user.
     * @return Response string to be displayed.
     */
    public String getResponse(String userInput) {
        try {
            Command command = parser.parse(userInput);
            String response = command.execute(taskList, ui, storage);
            shouldExit = command.isExit();
            return response;
        } catch (IllegalArgumentException e) {
            return ui.printException(e.getMessage());
        }
    }
}