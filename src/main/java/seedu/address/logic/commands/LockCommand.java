package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.HideAllBooks;

public class LockCommand extends Command{

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lock the Bibliotek.\n";

    public static final String MESSAGE_SUCCESS = "Bibliotek is locked!";

    private final HideAllBooks predicate = new HideAllBooks();

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        model.updateBookListFilter(predicate);
        LogicManager.lock();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
