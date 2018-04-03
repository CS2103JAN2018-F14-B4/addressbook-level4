package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.HideAllBooks;

public class LockCommand extends Command{

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lock the Bibliotek.\n"
            + "Parameters: PASSWORD (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 123456";

    public static final String MESSAGE_SUCCESS = "Bibliotek is locked!";

    private static String password;

    private final HideAllBooks predicate = new HideAllBooks();

    public LockCommand(String word) {
        this.password = word;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        model.updateBookListFilter(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
