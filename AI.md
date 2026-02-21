# Ai assistance

## DialogBox class
To crop the picture file to be a circle

```
 // change the picture file to be a circle
    public void initialize() {
        double radius = 50;

        Circle clip = new Circle(radius, radius, radius);
        displayPicture.setClip(clip);

        displayPicture.setFitWidth(radius * 2);
        displayPicture.setFitHeight(radius * 2);
    }
```

## TaskList class
To add the Snooze feature

```
public Task snooze(int taskNumber, LocalDateTime by, LocalDateTime from, LocalDateTime to) {
        List<Task> tasks = getList();
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException("Noo... task number out of range!");
        }

        Task task = tasks.get(taskNumber - 1);

        if (task instanceof Todo) {
            throw new IllegalArgumentException("TodoTask does not support snooze!");
        }

        if (task instanceof Deadline) {
            if (by == null) {
                throw new IllegalArgumentException("Deadline snooze usage: snooze <index> by <datetime>");
            }
            ((Deadline) task).setBy(by);
            return task;
        }

        if (task instanceof Event) {
            Event e = (Event) task;

            LocalDateTime oldFrom = e.getFrom();
            LocalDateTime oldTo = e.getTo();

            LocalDateTime newFrom = oldFrom;
            LocalDateTime newTo = oldTo;

            if (from != null && to != null) {
                newFrom = from;
                newTo = to;
            } else if (from != null) {
                // only from: keep oldTo
                newFrom = from;
                newTo = oldTo;
            } else if (to != null) {
                // only to: keep oldFrom
                newFrom = oldFrom;
                newTo = to;
            } else {
                throw new IllegalArgumentException("Event snooze usage: snooze <index> from/to ...");
            }

            if (!newTo.isAfter(newFrom)) {
                throw new IllegalArgumentException("Oops.. event end time must be after start time!");
            }

            e.setFrom(newFrom);
            e.setTo(newTo);
            return e;
        }


        throw new IllegalArgumentException("This task type does not support snooze!");
    }
```