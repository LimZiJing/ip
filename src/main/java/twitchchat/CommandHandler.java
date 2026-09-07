package twitchchat;

import twitchchat.commands.Command;
import twitchchat.tasks.Deadline;
import twitchchat.tasks.Event;
import twitchchat.tasks.Task;
import twitchchat.tasks.TaskList;
import twitchchat.tasks.Todo;

/**
 * Executes parsed commands and updates the task list.
 */
public class CommandHandler {

    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a command handler with the task list and UI it should use.
     *
     * @param tasks task list to update
     * @param ui UI used to display command results
     */
    public CommandHandler(TaskList tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Executes a parsed command.
     *
     * @param command command to execute
     * @return true if the application should continue, or false after a bye command
     */
    public boolean executeCommand(Command command) {
        String[] arguments = command.getArguments();
        int taskId = command.getId();
        switch (command.getType()) {
        case TODO:
            addTodo(arguments[0]);
            break;
        case MARK:
            handleMarkCommand(true, taskId);
            break;
        case UNMARK:
            handleMarkCommand(false, taskId);
            break;
        case EVENT:
            addEvent(arguments);
            break;
        case DEADLINE:
            addDeadline(arguments);
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
        }
        return true;
    }

    private void addTodo(String taskName) {
        addTask(new Todo(taskName));
    }

    private void addDeadline(String[] arguments) {
        addTask(new Deadline(arguments[0], arguments[1]));
    }

    private void addEvent(String[] arguments) {
        addTask(new Event(arguments[0], arguments[1], arguments[2]));
    }

    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showAddedTask(task, tasks.getTaskCount());
    }

    private void handleMarkCommand(boolean isMarkingDone, int taskId) {
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
