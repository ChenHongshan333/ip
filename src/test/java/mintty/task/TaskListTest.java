package mintty.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    @Test
    public void add_emptyDescription_throwsIllegalArgumentException() {
        TaskList taskList = new TaskList();
        Task task = new Todo("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> taskList.add(task));
        assertEquals("Ooops... missing mintty.task description! TT", e.getMessage());

    }

    @Test
    public void setTask_invalidNumber_throwsIllegalArgumentException() {
        TaskList taskList = new TaskList();
        Task task = new Todo("A");
        taskList.add(task);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> taskList.setTask(0, true));
        assertEquals("Oops... It is illegal to enter: 0 ... plz enter a valid mintty.task number again!",
                e.getMessage());
    }

}
