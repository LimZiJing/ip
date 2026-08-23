import java.util.Scanner;

public class TwitchChat {
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
        String[] tasks = new String[100]; // Array to store tasks
        int taskCount = 0;
        // Get user input
        while (true) {
            String userInput = inputScanner.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {

                System.out.println(horizontalLine);

                // Print out tasks stored
                if (taskCount == 0) {
                    System.out.println("No tasks currently.");
                } else {
                    System.out.println("Here are your tasks: ");
                    for (int i = 0; i < taskCount; i++){
                        System.out.printf("%d: %s\n", i + 1, tasks[i]);
                    }
                }

                System.out.println(horizontalLine);
            } else {
                // Store user input as task
                tasks[taskCount] = userInput;
                taskCount++;
                System.out.println(horizontalLine);
                System.out.printf("added: %s\n", userInput);
                System.out.println(horizontalLine);
            }

        }


        System.out.println("See you next time, bye bye!");
    }
}
