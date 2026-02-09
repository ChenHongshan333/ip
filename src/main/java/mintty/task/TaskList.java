package mintty.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@code Task}.
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    public void load(List<Task> l) {
        tasks.addAll(l);
    }

    /**
     * Adda a {@code Task} to the taskList.
     *
     * @param task
     * @throws IllegalArgumentException if the task description is {@code null}.
     */
    public void add(Task task) {
        String des = task.getDescription();
        if (des == null || des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }
        tasks.add(task);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getList() {
        return this.tasks;
    }

    private Task getByNumber(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException("Oops... It is illegal to enter: "
                    + taskNumber
                    + " ... plz enter a valid mintty.task number again!");
        }
        return tasks.get(taskNumber - 1);
    }

    /**
     * Sets the status of a {@code Task} specified by {@code taskNumber} to be done (i.e. true) or undone (i.e. false).
     *
     * @param taskNumber An integer that represents the order of the task in taskList
     * @param done A boolean value that indicates whether this is a mark action (true) or unmark action (false)
     * @return A {@code Task} that is marked or unmarked by user
     * @throws IllegalArgumentException if user enters an invalid {@code taskNumber}
     */
    public Task setTask(int taskNumber, boolean done) {
        Task t = getByNumber(taskNumber);
        if (done) {
            t.setDone();
        } else {
            t.setUndone();
        }
        return t;
    }

    /**
     * Removes a {@code Task} specified by {@code taskNumber} from the taskList
     *
     * @param taskNumber An integer that represents the order of the task in taskList
     * @return A {@code Task} that is removed by user
     * @throws IllegalArgumentException if user enters an invalid {@code taskNumber}
     */
    public Task remove(int taskNumber) {
        Task t = getByNumber(taskNumber);
        tasks.remove(taskNumber - 1);
        return t;
    }

    /**
     * Finds a list of {@code Task} whose description contains {@code keyword}
     *
     * @param keyword A user-specified keyword
     * @return A list of {@code Task} that contains {@code keyword}
     * @throws IllegalArgumentException if {@code keyword} is null or blank after trimmed
     */
    public List<Task> find(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Heyy you have to tell me what you want to find QwQ!");
        }

        String k = keyword.trim().toLowerCase();
        List<Task> matchedTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(k)) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }

}
