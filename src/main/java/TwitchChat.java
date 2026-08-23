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
            if (userInput.equalsIgnoreCase("bye")) {
                // exit program
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                // print tasks
                System.out.println(horizontalLine);
                tasks.printTasks();
                System.out.println(horizontalLine);
            } else {
                // Add task to tasks
                tasks.addTask(userInput);
                System.out.println(horizontalLine);
                System.out.printf("added: %s\n", userInput);
                System.out.println(horizontalLine);
            }

        }


        System.out.println("See you next time, bye bye!");
    }
}
