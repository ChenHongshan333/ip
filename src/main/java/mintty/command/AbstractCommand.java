package mintty.command;

/**
 * Represents a base class for all executable commands.
 *
 * <p>This abstract class provides a default implementation for {@link #isExit()},
 * assuming that most commands do not trigger the termination of the application.
 * Subclasses that represent an exit action should override this method to return {@code true}.</p>
 */
public abstract class AbstractCommand implements Command {

    /**
     * Returns {@code false} by default, indicating that this command does not
     * terminate the application.
     *
     * @return {@code false} as the default behavior.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}