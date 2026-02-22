package mintty.command;

import mintty.storage.Storage;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Represents a generic executable command.
 *
 * <p>All concrete commands must implement this interface.
 * Each command encapsulates both:
 * 1. The data needed for execution
 * 2. The execution logic itself
 *
 * This is the core of the Command Pattern.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param list The task list to operate on.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @return A response string to be displayed.
     */
    String execute(TaskList list, Ui ui, Storage storage);

    /**
     * Whether this command should terminate the program.
     *
     * @return true if program should exit.
     */
    boolean isExit();
}