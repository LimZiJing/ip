public class TaskList {
    private Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    public void addTask(String taskName) {
        tasks[taskCount] = new Task(taskName);
        taskCount++;
    }

    public Task getTasks(int taskId) {
        return tasks[taskId - 1]; // Convert 1-indexed taskId to 0-index for accessing task
    }


    public int getTaskCount() {
        return taskCount;
    }

    public void printTasks() {
        for (int i = 0; i < taskCount; i++) {
            System.out.printf("%d.[%s] %s\n", i + 1, tasks[i].getStatusIcon(), tasks[i].getTaskName());
        }
    }
}
