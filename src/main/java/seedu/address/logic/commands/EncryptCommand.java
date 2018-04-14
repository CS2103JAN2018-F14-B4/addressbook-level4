package seedu.address.logic.commands;
//@@author 592363789
import seedu.address.logic.KeyControl;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Encrypts the book shelf.
 */
public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Encrypt book shelf.\n";

    public static final String MESSAGE_FAIL = "Book Shelf cannot be encrypted now";
    public static final String MESSAGE_SUCCESS = "Book shelf is encrypted.";

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() {
        if (KeyControl.getInstance().getKey().equals("")) {
            return new CommandResult(MESSAGE_FAIL);
        } else {
            model.updateBookListFilter(Model.PREDICATE_HIDE_ALL_BOOKS);
            KeyControl.getInstance().encrypt();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
