package twitchchat;

import java.util.List;
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

    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a warning when saved tasks cannot be loaded.
     */
    public void showLoadingError() {
        System.out.println("Warning: Could not load saved tasks. Starting with an empty task list.");
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

    /**
     * Displays a confirmation after a task is deleted.
     *
     * @param task task that was deleted
     * @param taskCount number of tasks remaining after deletion
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Noted, I've deleted this task:");
        task.printTask();
        System.out.printf("Now you have %d tasks in the list.\n", taskCount);
    }

    /**
     * Displays tasks whose descriptions match a search keyword.
     *
     * @param tasks matching tasks to display
     * @param keyword keyword used for the search
     */
    public void showFoundTasks(List<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for keyword: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d.", i + 1);
                tasks.get(i).printTask();
            }
        }
    }
}
