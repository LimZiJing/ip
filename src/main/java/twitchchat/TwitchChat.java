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
            if (userInput.startsWith("todo ")) {
                String taskName = userInput.substring(5);
                Todo todo = new Todo(taskName);
                TASKS.addTask(todo);
                ui.showAddedTask(todo, TASKS.getTaskCount());
            } else if (userInput.startsWith("deadline ")) {
                String taskDetails = userInput.substring(9);
                String[] arguments = taskDetails.split(" /by ", 2);
                Deadline deadline = new Deadline(arguments[0], arguments[1]);
                TASKS.addTask(deadline);
                ui.showAddedTask(deadline, TASKS.getTaskCount());
            } else if (userInput.startsWith("event ")) {
                String taskDetails = userInput.substring(6);
                String[] arguments = taskDetails.split(" /from ", 2);
                String[] timeArguments = arguments[1].split(" /to ", 2);
                Event event = new Event(arguments[0], timeArguments[0], timeArguments[1]);
                TASKS.addTask(event);
                ui.showAddedTask(event, TASKS.getTaskCount());
            } else if (userInput.contains("unmark")) {
                String[] arguments = userInput.split(" ", 2);
                int taskId;

                if (arguments.length == 1) {
                    ui.showUnmarkTaskPrompt();
                } else if ((taskId = Integer.parseInt(arguments[1])) > TASKS.getTaskCount() || taskId <= 0) {
                    ui.showNoTaskFound(taskId);
                } else {
                    taskId = Integer.parseInt(arguments[1]);
                    Task currentTask = TASKS.getTask(taskId);
                    currentTask.markAsNotDone();
                    ui.showTaskMarkedAsNotDone(currentTask);
                }
            } else if (userInput.contains("mark")) {
                String[] arguments = userInput.split(" ", 2);
                int taskId;

                if (arguments.length == 1) {
                    ui.showMarkTaskPrompt();
                } else if ((taskId = Integer.parseInt(arguments[1])) > TASKS.getTaskCount() || taskId <= 0) {
                    ui.showNoTaskFound(taskId);
                } else {
                    taskId = Integer.parseInt(arguments[1]);
                    Task currentTask = TASKS.getTask(taskId);
                    currentTask.markAsDone();
                    ui.showTaskMarkedAsDone(currentTask);
                }
            } else {
                switch (userInput) {
                    case "bye":
                        ui.showGoodbye();
                        return;
                    case "list":
                        TASKS.printTasks();
                        break;
                    default:
                        Todo todo = new Todo(userInput);
                        TASKS.addTask(todo);
                        ui.showAddedTask(todo, TASKS.getTaskCount());
                        break;
                }
            }
            ui.showLine();
        }
    }
}
