package mintty.command;

import mintty.storage.Storage;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Represents a command that signals the application to terminate.
 *
 * <p>When executed, this command displays a farewell message to the user
 * and flags the program to stop its main execution loop.</p>
 */
public class ExitCommand extends AbstractCommand {

    /**
     * Executes the exit command by displaying the goodbye message via the UI.
     *
     * @param list    The task list (not modified by this command).
     * @param ui      The user interface to handle output.
     * @param storage The storage to handle file saving (not used by this command).
     * @return A string containing the farewell message.
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        return ui.printGoodbye();
    }

    /**
     * Indicates that this command should terminate the application.
     *
     * @return {@code true} always.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}