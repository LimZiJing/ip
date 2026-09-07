package twitchchat;

import twitchchat.commands.Command;
import twitchchat.tasks.Deadline;
import twitchchat.tasks.Event;
import twitchchat.tasks.Task;
import twitchchat.tasks.TaskList;
import twitchchat.tasks.Todo;

public class TwitchChat {

    public static void main(String[] args) {
        Ui ui = new Ui();
        InputParser parser = new InputParser();
        TaskList tasks = new TaskList();
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();
            ui.showLine();
            Command command = parser.parse(userInput);

            if (!executeCommand(command, tasks, ui)) {
                return;
            }
            ui.showLine();
        }
    }

    private static boolean executeCommand(Command command, TaskList tasks, Ui ui) {
        String[] arguments = command.getArguments();
        switch (command.getType()) {
        case TODO:
            addTodo(arguments[0], tasks, ui);
            break;
        case MARK:
            handleMarkCommand(arguments, tasks, ui, true);
            break;
        case UNMARK:
            handleMarkCommand(arguments, tasks, ui, false);
            break;
        case EVENT:
            addEvent(arguments, tasks, ui);
            break;
        case DEADLINE:
            if (arguments.length == 3) {
                // InputParser currently labels parsed events as deadlines.
                addEvent(arguments, tasks, ui);
            } else {
                addDeadline(arguments, tasks, ui);
            }
            break;
        case LIST:
            tasks.printTasks();
            break;
        case HI:
            System.out.println("Hello!");
            break;
        case BYE:
            ui.showGoodbye();
            return false;
        case INVALID:
            System.out.println(command.getErrorMessage());
            break;
        }
        return true;
    }

    private static void addTodo(String taskName, TaskList tasks, Ui ui) {
        addTask(new Todo(taskName), tasks, ui);
    }

    private static void addDeadline(String[] arguments, TaskList tasks, Ui ui) {
        addTask(new Deadline(arguments[0], arguments[1]), tasks, ui);
    }

    private static void addEvent(String[] arguments, TaskList tasks, Ui ui) {
        addTask(new Event(arguments[0], arguments[1], arguments[2]), tasks, ui);
    }

    private static void addTask(Task task, TaskList tasks, Ui ui) {
        tasks.addTask(task);
        ui.showAddedTask(task, tasks.getTaskCount());
    }

    private static void handleMarkCommand(String[] arguments, TaskList tasks, Ui ui, boolean isMarkingDone) {
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
        if (taskId > tasks.getTaskCount() || taskId <= 0) {
            ui.showNoTaskFound(taskId);
            return;
        }

        Task currentTask = tasks.getTask(taskId);
        if (isMarkingDone) {
            currentTask.markAsDone();
            ui.showTaskMarkedAsDone(currentTask);
        } else {
            currentTask.markAsNotDone();
            ui.showTaskMarkedAsNotDone(currentTask);
        }
    }

}
