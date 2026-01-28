package mintty.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> list = new ArrayList<>();

    public void load(List<Task> l) {
        list.addAll(l);
    }

    public void add(Task t) {
        String des = t.getDescription();
        if (des == null || des.isEmpty()) {
            throw new IllegalArgumentException("Ooops... missing mintty.task description! TT");
        }
        list.add(t);
    }

    public int size() {
        return list.size();
    }

    public List<Task> getList() {
        return this.list;
    }

    private Task getByNumber(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > list.size()) {
            throw new IllegalArgumentException("Oops... It is illegal to enter: " + taskNumber + " ... plz enter a valid mintty.task number again!");
        }
        return list.get(taskNumber - 1);
    }

    public Task setTask(int taskNumber, boolean done) {
        Task t = getByNumber(taskNumber);
        if (done) {
            t.setDone();
        } else {
            t.setUndone();
        }
        return t;
    }

    public Task remove(int taskNumber) {
        Task t = getByNumber(taskNumber);
        list.remove(taskNumber - 1);
        return t;
    }

    public List<Task> find(String keyword) {
        String k = keyword.trim().toLowerCase();
        List<Task> matchedTasks = new ArrayList<>();
        for (Task task : list) {
            if (task.getDescription().toLowerCase().contains(k)) {
                matchedTasks.add(task);
            }
        }
        return matchedTasks;
    }

}
