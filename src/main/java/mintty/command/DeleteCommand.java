package mintty.command;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Deletes a task by index.
 */
public class DeleteCommand extends AbstractCommand {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task removed = list.remove(index);
        storage.save(list.getList());
        return ui.printDelete(removed, list.size());
    }
}