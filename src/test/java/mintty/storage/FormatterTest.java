package mintty.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatterTest {
    @Test
    public void decode_missing_description() {
        String line = "ddl | X";
        Formatter formatter = new Formatter();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> formatter.decode(line));
        assertEquals("Missing components in the file TT: ddl | X", e.getMessage());
    }

    @Test
    public void decode_uncategorizedTask() {
        String line = "H | X | submit HW1";
        Formatter formatter = new Formatter();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> formatter.decode(line));
        assertEquals("Uncategorized mintty.task TT: H | X | submit HW1", e.getMessage());
    }

    @Test
    public void parseStatus_uncategorizedStatus() {
        String status = "#";
        Formatter formatter = new Formatter();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> formatter.parseStatus(status));
        assertEquals("I cannot recognize the status TT ... is there a typo?", e.getMessage());
    }
}