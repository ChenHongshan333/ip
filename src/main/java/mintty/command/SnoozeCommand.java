package mintty.command;

import java.time.LocalDateTime;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Handles snooze command for deadline and event tasks.
 */
public class SnoozeCommand extends AbstractCommand {

    private final int index;
    private final LocalDateTime by;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public SnoozeCommand(int index, LocalDateTime by,
                         LocalDateTime from, LocalDateTime to) {
        this.index = index;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        Task snoozed = list.snooze(index, by, from, to);
        storage.save(list.getList());
        return "Snoozed: " + snoozed;
    }
}