package mintty.ui;

import mintty.task.Task;
import mintty.task.TaskList;

import java.util.List;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Ui {
    // protected static final String separator = "-".repeat(50);

    public String printGreeting() {
        return "Heyyy this is Mintty ~\nWhat can I do for you?\n";
    }

    public String printGoodbye() {
        return "Nice to talk to you ^^\nSee you!\n";
    }

    public String printAddedMsg(Task task, int newSize) {
        return "Okie!! I've added this to the task list:\n"
                + task.toString()
                + "\nNow you have " + newSize + " tasks in total ^^";
    }

    public String printList(TaskList list) {
        StringBuilder sb = new StringBuilder();

        if (list.size() == 0) {
            sb.append("There is no task in your list QAQ\n");
            return sb.toString();
        }

        sb.append("Here are the tasks in your list: \n");
        int index = 1;
        for (Task t : list.getList()) {
            sb.append(index).append(".").append(t.toString()).append("\n");
            index++;
        }

        return sb.toString().trim();
    }


    public String printMarkedTask(Task t) {
        if (t.getStatus()) {
            return "Niceee! I've marked this task as done: \n" + t.toString();
        } else {
            return "Okie, I've marked this task as not done yet: \n" + t.toString();
        }
    }

    public String printDelete(Task task, int newSize) {

        return "Okie!! I've removed this to from the task list:\n"
                + task.toString()
                + "\nNow you have "
                + newSize
                + " tasks in total. \n";
    }

    public String printException(String msg) {
        return msg;
    }

    public String printFind(List<Task> foundList) {
        if (foundList.isEmpty()) {
            return "Sorry I did not find any task that matches your keyword TT... \n"
                    + "Is there a typo?? \n";
        }
        int index = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Heyy! I've matched the following tasks in your list: \n");
        for (Task t : foundList) {
            sb.append(index).append(".").append(t.toString()).append("\n");
            index++;
        }
        return sb.toString();
    }

    public String readUserInput(Scanner sc) {
        if (!sc.hasNextLine()) {
            return null;
        }
        return sc.nextLine();
    }

}
