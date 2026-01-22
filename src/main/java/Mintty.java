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
    List<String> list = new ArrayList<>();

    public void run() {
        // start
        printGreeting();
        
        // entering events
        while (true) {
            String userInput = readUserInput();

            // exit
            if (userInput.equals("bye")) {
                printGoodbye();
                closeResources();
                break;
            }

            // add to list
            if (userInput.equals("list")) {
                printList();
                continue;
            }

            // print user msg
            printAddedMsg(userInput);

            // add user input to the list
            addUserInput(userInput);

        }
    }

    public void printGreeting() {
        System.out.println(separator);
        System.out.println("Heyyy this is Mintty ๐•ᴗ•๐ \nWhat can I do for you?");
        System.out.println(separator);
    }

    public String readUserInput() {
        return  sc.nextLine();
    }

    public void addUserInput(String userInput) {
        list.add(userInput);
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

    public void printAddedMsg(String userInput) {
        System.out.println(separator);
        System.out.println("added: " + userInput);
        System.out.println(separator);
    }

    public void printList() {
        int len = list.size();
        System.out.println(separator);
        for (int i = 0; i < len; i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
        System.out.println(separator);
    }
}
