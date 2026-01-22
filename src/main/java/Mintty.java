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
        // wait the user input
//        String userInput = readUserInput();
        // exit
        printGoodbye();
        closeResources();
    }

    public void printGreeting() {
        System.out.println("Heyyy this is Mintty ๐•ᴗ•๐ \nWhat can I do for you?");
    }

    public String readUserInput() {
        String input = sc.nextLine();
        return null;
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
