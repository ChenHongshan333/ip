package mintty.storage;

import mintty.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for Storage.
 *
 * Focus:
 * - File I/O behaviour
 * - Formatter interaction
 * - Corrupted line handling
 *
 * Does NOT test Task logic.
 */
public class StorageTest {

    @TempDir
    Path tempDir;

    private Formatter formatter;
    private Storage storage;
    private Path filePath;

    @BeforeEach
    public void setUp() {
        formatter = mock(Formatter.class);
        filePath = tempDir.resolve("data.txt");
        storage = new Storage(filePath, formatter);
    }

    // =====================================================
    // load() tests
    // =====================================================

    @Test
    public void load_fileNotExist_returnsEmptyList() {
        List<Task> result = storage.load();
        assertTrue(result.isEmpty());
    }

    @Test
    public void load_validFile_returnsDecodedTasks() throws Exception {
        Files.write(filePath, List.of("line1", "line2"));

        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);

        when(formatter.decode("line1")).thenReturn(task1);
        when(formatter.decode("line2")).thenReturn(task2);

        List<Task> result = storage.load();

        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));
    }

    @Test
    public void load_corruptedLine_skipsLine() throws Exception {
        Files.write(filePath, List.of("good", "bad"));

        Task task = mock(Task.class);

        when(formatter.decode("good")).thenReturn(task);
        when(formatter.decode("bad")).thenThrow(new RuntimeException("corrupted"));

        List<Task> result = storage.load();

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    // =====================================================
    // save() tests
    // =====================================================

    @Test
    public void save_writesEncodedTasksToFile() throws Exception {

        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);

        when(formatter.encode(task1)).thenReturn("encoded1");
        when(formatter.encode(task2)).thenReturn("encoded2");

        storage.save(Arrays.asList(task1, task2));

        List<String> lines = Files.readAllLines(filePath);

        assertEquals(2, lines.size());
        assertEquals("encoded1", lines.get(0));
        assertEquals("encoded2", lines.get(1));
    }

    @Test
    public void save_overwritesExistingFile() throws Exception {

        Files.write(filePath, List.of("old content"));

        Task task = mock(Task.class);
        when(formatter.encode(task)).thenReturn("new content");

        storage.save(List.of(task));

        List<String> lines = Files.readAllLines(filePath);

        assertEquals(1, lines.size());
        assertEquals("new content", lines.get(0));
    }

    @Test
    public void save_createsParentDirectoryIfNeeded() throws Exception {

        Path nested = tempDir.resolve("nested/folder/data.txt");
        Storage nestedStorage = new Storage(nested, formatter);

        Task task = mock(Task.class);
        when(formatter.encode(task)).thenReturn("hello");

        nestedStorage.save(List.of(task));

        assertTrue(Files.exists(nested));
    }
}