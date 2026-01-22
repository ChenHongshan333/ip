import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mintty {

    public static void main(String[] args) {
        Mintty mintty = new Mintty();
        mintty.run();
    }

    Scanner sc = new Scanner(System.in);
    String separator = "-".repeat(50);

    // a list of tasks
    List<Task> list = new ArrayList<>();

    public void run() {
        // start
        printGreeting();

        // entering events
        while (true) {
            // get user input
            String userInput = readUserInput();

            // Parse the input
            var parsed = CommandParser.lineParser(userInput);
            String command = parsed.command();
            String arg = parsed.arg();

            // based on different prompt, do different things
            switch (command) {
                case "bye", "exit" :
                    handleExit();
                    return;

                case "list" :
                    handleList();
                    break;

                case "mark" :
                    int n = CommandParser.parseTaskNumber(arg);
                    handleMark(n, true);
                    break;

                case "unmark" :
                    int m = CommandParser.parseTaskNumber(arg);
                    handleMark(m, false);
                    break;

                case "todo" :
                    Task todoTask = new Todo(arg);
                    handleTask(todoTask);
                    break;

                case "deadline", "ddl" :
                    var dParts = CommandParser.deadlineParser(arg);
                    Task ddlTask = new Deadline(arg, dParts.by());
                    handleTask(ddlTask);
                    break;

                case "event" :
                    var eParts = CommandParser.eventParser(arg);
                    Task eventTask = new Event(arg, eParts.from(), eParts.to());
                    handleTask(eventTask);
                    break;

                default:
                    Task task = new Task(arg);
                    handleTask(task);
                    break;
            }
        }


    }

    public void handleExit() {
        printGoodbye();
        closeResources();
    }

    public void handleList() {
        printList();
    }

    public void handleMark(int n, boolean b) {
        Task t = updateTaskStatus(n, b);
        if (t == null) {
            printInvalid();
        } else {
            printMarkedTask(t);
        }
    }


    public void handleTask(Task task) {
        // print user msg
        printAddedMsg(task);

        // add user input to the list
        addUserInput(task);
    }


    public void printGreeting() {
        System.out.println(separator);
        System.out.println("Heyyy this is Mintty ~\nWhat can I do for you?");
        System.out.println(separator);
    }

    public String readUserInput() {
        return  sc.nextLine();
    }

    public void addUserInput(Task task) {
        list.add(task);
    }

    public void printGoodbye() {
        System.out.println(separator);
        System.out.println("Nice to talk to you ^^\nSee you!");
        System.out.println(separator);
    }

    public void closeResources() {
        sc.close();
    }

    public void printAddedMsg(Task task) {
        System.out.println(separator);
        System.out.println("Okie!! I've added this to the task list:\n"
                + task.toString()
                + "\nNow you have " + (list.size()+1) + " tasks in total");
        System.out.println(separator);
    }

    public void printList() {
        int index = 1;
        System.out.println(separator);
        System.out.println("Here are the tasks in your list: ");
        for (Task t : list) {
            System.out.println(index + "." + t.toString());
            index++;
        }
        System.out.println(separator);
    }

    public Task updateTaskStatus(int number, boolean b) {
        int index = number - 1;
        if (index >= 0 && index < list.size()) {
            Task t = list.get(index);
            if (b) {
                t.setDone();
            } else {
                t.setUndone();
            }
            return t;
        }
        return null;
    }

    public void printMarkedTask(Task t) {
        System.out.println(separator);
        if (t.getStatus()) {
            System.out.println("Niceee! I've marked this task as done: \n" + t.toString());
        } else {
            System.out.println("Okie, I've marked this task as not done yet: \n" + t.toString());
        }
        System.out.println(separator);
    }

    public void printInvalid() {
        System.out.println("Your input is invalid!!!");
    }
}
