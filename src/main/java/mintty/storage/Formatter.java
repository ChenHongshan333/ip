package mintty.storage;

import mintty.task.Deadline;
import mintty.task.Event;
import mintty.task.Task;
import mintty.task.Todo;

import java.time.LocalDateTime;

/**
 * Represents a converter that enables the mutual conversion between codes and strings
 */
public class Formatter {

    // type | taskStatus | description (des / des + by / des + from + to)
    private static final String SEP_REGEX = "\\s*\\|\\s*";

    // convert code to string (which can be recognised by files in disk)
    public String encode(Task task) {
        return task.toStorageString();
    }

    // convert string in files into code that can be recognized by mintty.Mintty

    /**
     * Parses a string line stored in hard disk into a {@code Task} consisting of
     * a command, a status and a corresponding description.
     *
     * @param line
     * @return A {@code Task}
     * @throws IllegalArgumentException if the line is incomplete, or the type of the task is uncategorized
     */
    public Task decode(String line) {
        String[] decoded = line.split(SEP_REGEX);
        if (decoded.length < 3) {
            throw new IllegalArgumentException("Missing components in the file TT: " + line);
        }

        String type = decoded[0].trim();
        boolean status = parseStatus(decoded[1].trim());
        String arg = decoded[2].trim();

        Task t;
        switch (type) {
        case "T":
            t = new Todo(arg);
            break;
        case "D":
            // expected: D | status | des | by
            if (decoded.length < 4) {
                throw new IllegalArgumentException("Missing components in the file TT: " + line);
            }
            String d1 = decoded[2].trim();
            String t1 = decoded[3].trim();
            LocalDateTime by = LocalDateTime.parse(t1);
            t = new Deadline(d1, by);
            break;
        case "E":
            // expected: E | status | des | from | to
            if (decoded.length < 5) {
                throw new IllegalArgumentException("Missing components in the file TT: " + line);
            }
            String d2 = decoded[2].trim();
            String t2 = decoded[3].trim();
            String t3 = decoded[4].trim();
            LocalDateTime from = LocalDateTime.parse(t2);
            LocalDateTime to = LocalDateTime.parse(t3);
            t = new Event(d2, from, to);
            break;
        default:
            throw new IllegalArgumentException("Uncategorized mintty.task TT: " + line);
        }

        if (status) {
            t.setDone();
        } else {
            t.setUndone();
        }
        return t;
    }

    /**
     * Parses a string representing status to boolean values,
     * where true implies a successful recognition of user-input string, and vice versa.
     *
     * @param s
     * @return A boolean value
     * @throws IllegalArgumentException if {@code s} does not match any of the existing format
     */
    public boolean parseStatus(String s) {
        if (s.equals("true") || s.equals("X") || s.equals("1")) {
            return true;
        } else if (s.equals("false") || s.isEmpty() || s.equals("0")) {
            return false;
        } else {
            throw new IllegalArgumentException("I cannot recognize the status TT ... is there a typo?");
        }
    }
}
