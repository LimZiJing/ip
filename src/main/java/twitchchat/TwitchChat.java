package twitchchat;

import twitchchat.tasks.Task;
import twitchchat.tasks.TaskList;
import twitchchat.tasks.Todo;
import twitchchat.tasks.Event;
import twitchchat.tasks.Deadline;

public class TwitchChat {

    private static final TaskList TASKS = new TaskList();

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            ui.showLine();
            if (!handleCommand(userInput, ui)) {
                return;
            }
            ui.showLine();
        }
    }

    private static boolean handleCommand(String userInput, Ui ui) {
        if (userInput.startsWith("todo ")) {
            addTodo(userInput.substring(5), ui);
        } else if (userInput.startsWith("deadline ")) {
            addDeadline(userInput.substring(9), ui);
        } else if (userInput.startsWith("event ")) {
            addEvent(userInput.substring(6), ui);
        } else if (userInput.contains("unmark")) {
            handleMarkCommand(userInput, ui, false);
        } else if (userInput.contains("mark")) {
            handleMarkCommand(userInput, ui, true);
        } else {
            return handleOtherCommand(userInput, ui);
        }
        return true;
    }

    private static void addTodo(String taskName, Ui ui) {
        addTask(new Todo(taskName), ui);
    }

    private static void addDeadline(String taskDetails, Ui ui) {
        String[] arguments = taskDetails.split(" /by ", 2);
        addTask(new Deadline(arguments[0], arguments[1]), ui);
    }

    private static void addEvent(String taskDetails, Ui ui) {
        String[] arguments = taskDetails.split(" /from ", 2);
        String[] timeArguments = arguments[1].split(" /to ", 2);
        addTask(new Event(arguments[0], timeArguments[0], timeArguments[1]), ui);
    }

    private static void addTask(Task task, Ui ui) {
        TASKS.addTask(task);
        ui.showAddedTask(task, TASKS.getTaskCount());
    }

    private static void handleMarkCommand(String userInput, Ui ui, boolean isMarkingDone) {
        String[] arguments = userInput.split(" ", 2);

        if (arguments.length == 1) {
            if (isMarkingDone) {
                ui.showMarkTaskPrompt();
            } else {
                ui.showUnmarkTaskPrompt();
            }
            return;
        }

        int taskId = Integer.parseInt(arguments[1]);
        if (taskId > TASKS.getTaskCount() || taskId <= 0) {
            ui.showNoTaskFound(taskId);
            return;
        }

        Task currentTask = TASKS.getTask(taskId);
        if (isMarkingDone) {
            currentTask.markAsDone();
            ui.showTaskMarkedAsDone(currentTask);
        } else {
            currentTask.markAsNotDone();
            ui.showTaskMarkedAsNotDone(currentTask);
        }
    }

    private static boolean handleOtherCommand(String userInput, Ui ui) {
        switch (userInput) {
            case "bye":
                ui.showGoodbye();
                return false;
            case "list":
                TASKS.printTasks();
                return true;
            default:
                addTodo(userInput, ui);
                return true;
        }
    }
}
