package mintty.command;

import mintty.storage.Storage;
import mintty.task.TaskList;
import mintty.task.Task;
import mintty.task.Todo;
import mintty.ui.Ui;

/**
 * Adds a Todo task.
 */
public class TodoCommand extends AbstractCommand {

    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task task = new Todo(description);
        list.add(task);
        storage.save(list.getList());
        return ui.printAddedMsg(task, list.size());
    }
}