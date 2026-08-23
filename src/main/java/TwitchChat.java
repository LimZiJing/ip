import java.util.Scanner;

public class TwitchChat {
    public static TaskList tasks = new TaskList();

    public static void main(String[] args) {
        String banner = "+-----------------------------+\n"
                + "|         TwitchChat          |\n"
                + "+-----------------------------+\n";
        String name = "TwitchChat";
        String horizontalLine = "____________________________________________________________";

        System.out.println(banner);
        System.out.println("Hello, my name is " + name + ".");
        System.out.println("How can I help you?");
        System.out.println(horizontalLine);

        Scanner inputScanner = new Scanner(System.in);
        // Get user input
        while (true) {
            String userInput = inputScanner.nextLine();

            System.out.println(horizontalLine);
            switch (userInput) { // handle input
                case "bye":
                    System.out.println("See you next time, bye bye!");
                    System.out.println(horizontalLine);
                    return;
                case "list":
                    tasks.printTasks();
                    break;
                default:
                    tasks.addTask(userInput);
                    System.out.printf("added: %s\n", userInput);
                    break;
            }
            System.out.println(horizontalLine);
        }
    }
}
