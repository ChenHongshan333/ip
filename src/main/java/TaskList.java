import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> list = new ArrayList<>();

    public void load(List<Task> l) {
        list.addAll(l);
    }

    public void add(Task t) {
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
            throw new IllegalArgumentException("Oops... It is illegal to enter: " + taskNumber + "... plz enter a valid task number again!");
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

}
