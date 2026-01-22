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

        try {
            // entering events
            while (true) {
                // get user input
                String userInput = readUserInput();

                // Parse the input
                var parsed = CommandParser.lineParser(userInput);
                String command = parsed.command();
                String arg = parsed.arg();
                int number = parseTaskNumber(arg);

                // based on different prompt, do different things
                switch (command) {
                    case "bye", "exit" :
                        handleExit();
                        break;

                    case "list" :
                        handleList();
                        break;

                    case "mark" :
                        handleMark(number, true);
                        break;

                    case "unmark" :
                        handleMark(number, false);
                        break;

                    default:
                        Task task = new Task(userInput);
                        handleTask(task);
                        break;
                }
            }
        }

        finally {
            closeResources();
        }


    }

    public void handleExit() {
        printGoodbye();
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
        System.out.println("Heyyy this is Mintty ๐•ᴗ•๐ \nWhat can I do for you?");
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
            System.out.println(index + "." + t.printStatus());
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
            System.out.println("Niceee! I've marked this task as done: \n" + t.printStatus());
        } else {
            System.out.println("Okie, I've marked this task as not done yet: \n" + t.printStatus());
        }
        System.out.println(separator);
    }


    public void printInvalid() {
        System.out.println("Your input is invalid!!!");
    }

    public int parseTaskNumber(String s) {
        // if there is no arg
        if (s == "") {
            return -1;
        }
        return Integer.parseInt(s.trim());
    }
}
