package mintty.command;

import mintty.storage.Storage;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Command that terminates the program.
 */
public class ExitCommand extends AbstractCommand {

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        return ui.printGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}