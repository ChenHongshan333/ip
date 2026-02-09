package mintty.storage;

import mintty.task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        try {
            if (Files.notExists(filePath)) {
                return new ArrayList<>();
            }

            try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
                return lines
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(this::decodeOrWarn)          // Optional<Task>
                        .flatMap(Optional::stream)        // Optional -> Task stream
                        .collect(Collectors.toList());
            }

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private Optional<Task> decodeOrWarn(String trimmedLine) {
        assert trimmedLine != null : "trimmedLine should not be null";
        assert !trimmedLine.isBlank() : "decodeOrWarn expects non-blank line";

        try {
            Task t = formatter.decode(trimmedLine);
            assert t != null : "formatter.decode() returned null for line: " + trimmedLine;
            return Optional.of(t);
        } catch (Exception corruptedLine) {
            System.out.println(separator);
            System.out.println("! Warning: " + corruptedLine.getMessage());
            System.out.println(separator);
            return Optional.empty();
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
