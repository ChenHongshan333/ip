package mintty.command;

import java.time.LocalDateTime;

import mintty.storage.Storage;
import mintty.task.Deadline;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Adds a Deadline task.
 */
public class DeadlineCommand extends AbstractCommand {

    private final String description;
    private final LocalDateTime by;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task task = new Deadline(description, by);
        list.add(task);
        storage.save(list.getList());
        return ui.printAddedMsg(task, list.size());
    }
}