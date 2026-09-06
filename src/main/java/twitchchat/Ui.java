package twitchchat;

import java.util.Scanner;

import twitchchat.tasks.Task;

public class Ui {

    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String BANNER = "+-----------------------------+\n"
            + "|         TwitchChat          |\n"
            + "+-----------------------------+\n";

    private final Scanner inputScanner;

    public Ui() {
        this.inputScanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(BANNER);
        System.out.println("Hello, my name is TwitchChat.");
        System.out.println("How can I help you?");
        showLine();
    }

    public String readCommand() {
        return inputScanner.nextLine();
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void showUnmarkTaskPrompt() {
        System.out.println("Please specify a task to unmark");
    }

    public void showMarkTaskPrompt() {
        System.out.println("Please specify a task to mark");
    }

    public void showNoTaskFound(int taskId) {
        System.out.printf("No task found at id: %d\n", taskId);
    }

    public void showTaskMarkedAsNotDone(Task task) {
        System.out.println("OK, I've marked this task as not done:");
        task.printTask();
    }

    public void showTaskMarkedAsDone(Task task) {
        System.out.println("Nice, I've marked this task as done:");
        task.printTask();
    }

    public void showGoodbye() {
        System.out.println("See you next time, bye bye!");
        showLine();
    }

    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        task.printTask();
        System.out.printf("Now you have %d tasks in the list.\n", taskCount);
    }
}
