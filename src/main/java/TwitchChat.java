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

        // Get user input
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            String userInput = inputScanner.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            // Echo user input
            System.out.println(horizontalLine);
            System.out.println(userInput);
            System.out.println(horizontalLine);
        }


        System.out.println("See you next time, bye bye!");
    }
}
