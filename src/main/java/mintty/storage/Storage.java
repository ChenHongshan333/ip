package mintty.storage;

import mintty.task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a storage space, and bridges the code and the hard disk storage
 */
public class Storage {
    private Path filePath;
    private Formatter formatter;
    private String separator = "-".repeat(50);

    public Storage(Path filePath, Formatter formatter) {

        assert filePath != null : "filePath should have been initialized";
        assert formatter != null : "formatter should have been initialized";

        this.filePath = filePath;
        this.formatter = formatter;
    }

    /**
     * Loads files from the disk.
     *
     * @return A list of {@code Tasks}
     * @throws Exception if there are some corrupted lines in the file
     */
    public List<Task> load() {
        assert filePath != null : "filePath should have been initialized";
        assert formatter != null : "formatter should have been initialized";

        // load files from the disk
        try {
            // running the 1st time, the list is empty
            if (Files.notExists(filePath)) {
                return new ArrayList<>();
            }

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                assert line != null : "Input file contains a null line (unexpected)";
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                try {
                    Task t = formatter.decode(trimmed);
                    assert t != null : "formatter.decode() returned null for line: " + trimmed;
                    tasks.add(t);
                } catch (Exception corruptedLine) {
                    // stretch: corrupted line handling
                    System.out.println(separator);
                    System.out.println("! Warning: " + corruptedLine.getMessage());
                    System.out.println(separator);
                }
            }
            return tasks;
        } catch (IOException e) {
            // if it failed to read files
            // return an empty arrayList
            return new ArrayList<>();
        }
    }

    /**
     * Saves all changes made by end-users (e.g. add tasks; mark/ unmark tasks, etc.)
     * Creates the folder if needed.
     * Overwrites the modified tasks.
     *
     * @param tasks A list of {@code Task}
     */
    public void save(List<Task> tasks) {

        assert tasks != null : "save() expects a non-null task list";
        assert filePath != null : "filePath should have been initialized";
        assert formatter != null : "formatter should have been initialized";

        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                assert t != null : "tasks list must not contain null";

                String encoded = formatter.encode(t);

                assert encoded != null : "formatter.encode() returned null for task: " + t;
                assert !encoded.contains("\n") && !encoded.contains("\r")
                        : "Encoded task must be single-line, but got: " + encoded;

                lines.add(encoded);
            }

            Files.write(filePath, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.out.println(separator);
            System.out.println("! Warning: " + e.getMessage());
            System.out.println(separator);
        }
    }
}
