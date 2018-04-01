package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String[] COMMAND_ARRAY = {"add", "clear", "delete", "exit", "help", "history", "list", "search", "select", "theme", "undoable", "undo"};
    protected Model model;
    protected CommandHistory history;
    protected UndoStack undoStack;


    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of books.
     *
     * @param displaySize used to generate summary
     * @return summary message for books displayed
     */
    public static String getMessageForBookListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoStack undoStack) {
        this.model = model;
    }
}
