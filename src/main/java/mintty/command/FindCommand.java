package mintty.command;

import java.util.List;

import mintty.storage.Storage;
import mintty.task.Task;
import mintty.task.TaskList;
import mintty.ui.Ui;

/**
 * Represents a command that searches for tasks containing a specific keyword.
 *
 * <p>The search is typically case-sensitive or case-insensitive depending on the
 * implementation of {@link TaskList#find(String)}.</p>
 */
public class FindCommand extends AbstractCommand {
    private final String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified search keyword.
     *
     * @param keyword The string to be searched for within the task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the search logic by filtering the task list and displaying the results.
     *
     * @param list    The task list to search within.
     * @param ui      The user interface to display the list of found tasks.
     * @param storage The storage component (not used by this command).
     * @return A formatted string listing all tasks that match the keyword.
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        List<Task> found = list.find(keyword);
        return ui.printFind(found);
    }
}