package mintty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {

    protected LocalDateTime by;
    //    static final DateTimeFormatter OUT_DATE =
    //            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
    static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("yyyy-MMM-d HH:mm", Locale.ENGLISH); // 26 Jan 26 20:00

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() { return by; }

    public void setBy(LocalDateTime by) { this.by = by; }


    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUT_DATETIME) + ")";
    }

    @Override
    public String toStorageString() {
        return "D | " + taskStatus() + " | " + getDescription() + " | " + by.toString();
    }
}
