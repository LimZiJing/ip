package twitchchat;

public class TwitchChat {

    private static final TaskList TASKS = new TaskList();

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            ui.showLine();
            if (userInput.contains("unmark")) {
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
                        TASKS.addTask(userInput);
                        ui.showAddedTask(userInput);
                        break;
                }
            }
            ui.showLine();
        }
    }
}
