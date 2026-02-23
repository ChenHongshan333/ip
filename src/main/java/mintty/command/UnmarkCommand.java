package mintty.command;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Represents a command that is marked as not done yet by user.
 */
public class UnmarkCommand extends AbstractCommand {

    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task task = list.setTask(index, false);
        storage.save(list.getList());
        return ui.printMarkedTask(task);
    }
}