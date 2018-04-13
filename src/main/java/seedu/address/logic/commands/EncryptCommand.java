package seedu.address.logic.commands;
//@@author 592363789
import seedu.address.logic.KeyControl;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.HideAllBooks;

/**
 * Encrypts the book shelf.
 */
public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Encrypt book shelf.\n";

    public static final String MESSAGE_SUCCESS = "Book shelf is encrypted.";

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() {
        model.updateBookListFilter(Model.PREDICATE_HIDE_ALL_BOOKS);
        KeyControl.getInstance().encrypt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
