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
    List<Task> list = new ArrayList<>();

    public void run() {
        // start
        printGreeting();
        
        // entering events
        while (true) {
            // get user input
            String userInput = readUserInput();

            // Parse the input
            String command = CommandParser.lineParser(userInput).command();
            String arg = CommandParser.lineParser(userInput).arg();

            // exit
            if (command.equals("bye") || command.equals("exit")) {
                handleExit();
                break;
            }

            // show the list
            if (command.equals("list")) {
                handleList();
                continue;
            }

            // mark a task
            if (command.equals("mark")) {
                // convert the arg to an integer
                int n = Integer.parseInt(arg.trim());
                handleMark(n);
                continue;
            }

            // unmark a task
            if (command.equals("unmark")) {
                int n = Integer.parseInt(arg.trim());
                handleUnmark(n);
                continue;
            }

            // otherwise, treat it as a new task
            Task task = new Task(userInput);
            handleTask(task);

        }
    }

    public void handleExit() {
        printGoodbye();
        closeResources();
    }

    public void handleList() {
        printList();
    }

    public void handleMark(int n) {
        markTask(n);
        printMarkedTask(n);
    }

    public void handleUnmark(int n) {
        unmarkTask(n);
        printUnmarkedTask(n);
    }

    public void handleTask(Task task) {
        // print user msg
        printAddedMsg(task);

        // add user input to the list
        addUserInput(task);
    }

    
    public void printGreeting() {
        System.out.println(separator);
        System.out.println("Heyyy this is Mintty ๐•ᴗ•๐ \nWhat can I do for you?");
        System.out.println(separator);
    }

    public String readUserInput() {
        return  sc.nextLine();
    }

    public void addUserInput(Task task) {
        list.add(task);
    }

    public void echo(String userInput) {
        System.out.println(separator);
        System.out.println(userInput);
        System.out.println(separator);
    }

    public void printGoodbye() {
        System.out.println(separator);
        System.out.println("Nice to talk to you ˗ˋˏ ♡ ˎˊ˗\nSee you!");
        System.out.println(separator);
    }

    public void closeResources() {
        sc.close();
    }

    public void printAddedMsg(Task task) {
        System.out.println(separator);
        System.out.println("added: " + task.getDescription());
        System.out.println(separator);
    }

    public void printList() {
        int index = 1;
        System.out.println(separator);
        System.out.println("Here are the tasks in your list: ");
        for (Task t : list) {
            System.out.println(index + "." + list.get(index-1).printStatus());
            index++;
        }
        System.out.println(separator);
    }

    public void markTask(int number) {
        int index = number - 1;
        if (index >= 0 && index < list.size()) {
            Task t = list.get(index);
            t.setDone();
            return;
        }
        System.out.println("Invalid task number!");
    }

    public void printMarkedTask(int n) {
        System.out.println(separator);
        System.out.println("Niceee! I've marked this task as done: \n" + list.get(n - 1).printStatus());
        System.out.println(separator);
    }

    public void unmarkTask(int number) {
        int index = number - 1;
        if (index >= 0 && index < list.size()) {
            Task t = list.get(index);
            t.setUndone();
            return;
        }
        System.out.println("Invalid task number!");
    }

    public void printUnmarkedTask(int n) {
        System.out.println(separator);
        System.out.println("Okie, I've marked this task as not done yet: \n" + list.get(n - 1).printStatus());
        System.out.println(separator);
    }
}
