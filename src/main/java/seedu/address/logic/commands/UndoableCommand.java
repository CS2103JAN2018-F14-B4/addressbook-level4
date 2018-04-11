package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command which can be undone.
 */
public abstract class UndoableCommand extends Command {

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Store what is required of the current state of {@code model#bookShelf}.
     * {@code UndoableCommand}s that needs to save some data should override this method.
     */
    protected void saveSnapshot() {}

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Revert the state of {@code model#bookShelf} back to before this command was executed.
     * @return success or failure message.
     */
    protected abstract String undoLogic();

    /**
     * Executes undo and updates the filtered book list to show all books.
     * @return success or failure message.
     */
    protected final String undo() {
        requireNonNull(model);
        String message = undoLogic();
        model.updateBookListFilter(Model.PREDICATE_SHOW_ALL_BOOKS);
        return message;
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
