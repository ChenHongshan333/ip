package mintty.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the enumeration of different commands.
 */
public enum Command {
    BYE("bye", "exit", "quit"),
    LIST("list"),
    TODO("todo", "td"),
    DEADLINE("deadline", "ddl"),
    EVENT("event", "e"),
    MARK("mark", "m"),
    UNMARK("unmark", "u"),
    DELETE("delete","del"),
    FIND("find", "f"),
    SNOOZE("snooze", "s"),
    UNKNOWN();

    private final Set<String> aliases;

    Command(String... aliases) {
        this.aliases = new HashSet<>();
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public static Command from(String token) {
        if (token == null || token.trim().isEmpty()) return UNKNOWN;
        String t = token.trim().toLowerCase();

        for (Command c : values()) {
            if (c.aliases.contains(t)) return c;
        }
        return UNKNOWN;
    }
}
