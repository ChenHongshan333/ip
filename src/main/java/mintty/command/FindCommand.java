package mintty.command;

import java.util.List;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

public class FindCommand extends AbstractCommand {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        List<Task> found = list.find(keyword);
        return ui.printFind(found);
    }
}