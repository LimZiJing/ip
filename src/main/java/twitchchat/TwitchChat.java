package twitchchat;

import twitchchat.commands.Command;
import twitchchat.tasks.Deadline;
import twitchchat.tasks.Event;
import twitchchat.tasks.Task;
import twitchchat.tasks.TaskList;
import twitchchat.tasks.Todo;

public class TwitchChat {

    private static final TaskList TASKS = new TaskList();

    public static void main(String[] args) {
        Ui ui = new Ui();
        InputParser parser = new InputParser();
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            ui.showLine();
            Command command = parser.parse(userInput);
            String[] arguments = command.getArguments();
            switch (command.getType()) {
            case TODO:
                addTodo(arguments[0], ui);
                break;
            case MARK:
                handleMarkCommand(arguments, ui, true);
                break;
            case UNMARK:
                handleMarkCommand(arguments, ui, false);
                break;
            case EVENT:
                addEvent(arguments, ui);
                break;
            case DEADLINE:
                if (arguments.length == 3) {
                    // InputParser currently labels parsed events as deadlines.
                    addEvent(arguments, ui);
                } else {
                    addDeadline(arguments, ui);
                }
                break;
            case LIST:
                TASKS.printTasks();
                break;
            case HI:
                System.out.println("Hello!");
                break;
            case BYE:
                ui.showGoodbye();
                return;
            case INVALID:
                System.out.println(command.getErrorMessage());
                break;
            }
            ui.showLine();
        }
    }


    private static void addTodo(String taskName, Ui ui) {
        addTask(new Todo(taskName), ui);
    }

    private static void addDeadline(String[] arguments, Ui ui) {
        addTask(new Deadline(arguments[0], arguments[1]), ui);
    }

    private static void addEvent(String[] arguments, Ui ui) {
        addTask(new Event(arguments[0], arguments[1], arguments[2]), ui);
    }

    private static void addTask(Task task, Ui ui) {
        TASKS.addTask(task);
        ui.showAddedTask(task, TASKS.getTaskCount());
    }

    private static void handleMarkCommand(String[] arguments, Ui ui, boolean isMarkingDone) {
        if (arguments.length == 0) {
            if (isMarkingDone) {
                ui.showMarkTaskPrompt();
            } else {
                ui.showUnmarkTaskPrompt();
            }
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException exception) {
            ui.showNoTaskFound(-1);
            return;
        }
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

}
