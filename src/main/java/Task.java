public abstract class Task {
    protected String description;
    protected boolean isDone;

    // here des = arg
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String taskStatus() {
        return (isDone ? "X" : " "); // mark done task with X
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
