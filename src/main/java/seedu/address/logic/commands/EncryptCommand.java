package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.HideAllBooks;

public class EncryptCommand extends Command{

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Encrypt the Bibliotek.\n";

    public static final String MESSAGE_SUCCESS = "Bibliotek is encrypted!";

    private final HideAllBooks predicate = new HideAllBooks();

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() {
        model.updateBookListFilter(predicate);
        LogicManager.encrypt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
