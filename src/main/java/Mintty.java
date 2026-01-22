import java.util.Scanner;

public class Mintty {
    public static void main(String[] args) {
        Mintty mintty = new Mintty();
        mintty.run();
    }

    Scanner sc = new Scanner(System.in);
    String separator = "-".repeat(50);

    public void run() {
        // start
        printGreeting();
        
        // entering events
        while (true) {
            String userInput = readUserInput();
            if (userInput.equals("bye")) {
                printGoodbye();
                closeResources();
                break;
            }
            echo(userInput);
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
}
