package mintty.storage;

import mintty.task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class Storage {
    private Path filePath;
    private Formatter formatter;
    private String separator = "-".repeat(50);

    public Storage(Path filePath, Formatter formatter) {
        this.filePath = filePath;
        this.formatter = formatter;
    }

    // load files from the disk
    public List<Task> load() {
        try {
            // running the 1st time, the list is empty
            if (Files.notExists(filePath)) {
                return new ArrayList<>();
            }

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                try {
                    Task t = formatter.decode(trimmed);
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

    //save all changes made by end-users
    //create the folder if needed
    // Overwrite the tasks
    public void save(List<Task> tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(formatter.encode(t));
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
