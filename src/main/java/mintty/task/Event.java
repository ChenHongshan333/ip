package mintty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;
    static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("yyyy-MMM-d HH:mm", Locale.ENGLISH); // 2026 Jan 26 20:00

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUT_DATETIME) + " , to: " + to.format(OUT_DATETIME) + ")";
    }

    @Override
    public String toStorageString() {
        return "E | " + taskStatus() + " | " + getDescription() + " | " + from.toString() + " | " + to.toString();
    }
}
