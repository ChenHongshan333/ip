package mintty.command;

import java.time.LocalDateTime;

import mintty.storage.Storage;
import mintty.task.Event;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Adds an Event task.
 */
public class EventCommand extends AbstractCommand {

    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task task = new Event(description, from, to);
        list.add(task);
        storage.save(list.getList());
        return ui.printAddedMsg(task, list.size());
    }
}