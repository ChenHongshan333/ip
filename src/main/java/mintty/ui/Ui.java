package mintty.ui;

import mintty.task.Task;
import mintty.task.TaskList;

import java.util.List;
import java.util.Scanner;

public class Ui {
    protected static final String separator = "-".repeat(50);

    public void printGreeting() {
        System.out.println(separator);
        System.out.println("Heyyy this is mintty.Mintty ~\nWhat can I do for you?");
        System.out.println(separator);
    }

    public void printGoodbye() {
        System.out.println(separator);
        System.out.println("Nice to talk to you ^^\nSee you!");
        System.out.println(separator);
    }

    public void printAddedMsg(Task task, int newSize) {
        System.out.println(separator);
        System.out.println("Okie!! I've added this to the mintty.task list:\n"
                + task.toString()
                + "\nNow you have " + newSize + " tasks in total");
        System.out.println(separator);
    }

    public void printList(TaskList list) {
        int index = 1;
        System.out.println(separator);
        if (list.size() == 0) {
            System.out.println("There is no mintty.task in your list QAQ");
            System.out.println(separator);
            return;
        }

        System.out.println("Here are the tasks in your list: ");
        for (Task t : list.getList()) {
            System.out.println(index + "." + t.toString());
            index++;
        }
        System.out.println(separator);
    }

    public void printMarkedTask(Task t) {
        System.out.println(separator);
        if (t.getStatus()) {
            System.out.println("Niceee! I've marked this mintty.task as done: \n" + t.toString());
        } else {
            System.out.println("Okie, I've marked this mintty.task as not done yet: \n" + t.toString());
        }
        System.out.println(separator);
    }

    public void printDelete(Task task, int newSize) {
        System.out.println(separator);
        System.out.println("Okie!! I've removed this to from the mintty.task list:\n"
                + task.toString()
                + "\nNow you have " + newSize + " tasks in total.");
        System.out.println(separator);
    }

    public void printException(String msg) {
        System.out.println(separator);
        System.out.println(msg);
        System.out.println(separator);
    }

    public void printFind(List<Task> foundList) {
        System.out.println(separator);
        if (foundList.isEmpty()) {
            System.out.println("Sorry I did not find any task that matches your keyword TT... \n"
                    + "Is there a typo??");
            System.out.println(separator);
            return;
        }
        int index = 1;
        System.out.println("Heyy! I've matched the following tasks in your list: ");
        for (Task t : foundList) {
            System.out.println(index + "." + t.toString());
            index++;
        }
        System.out.println(separator);
    }

    public String readUserInput(Scanner sc) {
        if (!sc.hasNextLine()) {
            return null;
        }
        return sc.nextLine();
    }

}
