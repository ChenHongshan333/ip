package mintty.task;

/**
 * Represents tasks carried out by end-users
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    // here des = arg
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the status in the representation of string of the current {@code Task}, based on {@code isDone}
     *
     * @return A string that indicates if the current {@code Task} is done or not
     */
    public String taskStatus() {
        return (isDone ? "X" : " "); // mark done mintty.task with X
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }

    public void setUndone() {
        isDone = false;
    }


    @Override
    public String toString() {
        return "[" + taskStatus() + "] " + getDescription();
    }

    public abstract String toStorageString();

}
