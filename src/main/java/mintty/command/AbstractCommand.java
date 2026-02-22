package mintty.command;

/**
 * Provides a default implementation for isExit().
 * Most commands do NOT terminate the program,
 * so we default isExit() to false.
 */
public abstract class AbstractCommand implements Command {

    @Override
    public boolean isExit() {
        return false;
    }
}