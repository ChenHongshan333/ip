package mintty.command;

import mintty.storage.Storage;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Displays all tasks.
 */
public class ListCommand extends AbstractCommand {

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        return ui.printList(list);
    }
}