package mintty.command;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

public class MarkCommand extends AbstractCommand {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task task = list.setTask(index, true);
        storage.save(list.getList());
        return ui.printMarkedTask(task);
    }
}