package seedu.address.logic.commands;
//@@author 592363789
import seedu.address.logic.LogicManager;

/**
 * Decrypts the Book shelf.
 */
public class DecryptCommand extends Command {

    public static final String COMMAND_WORD = "decrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Decrypt book shelf.\n"
            + "Parameters: PASSWORD\n"
            + "Example: " + COMMAND_WORD + " 123456";

    public static final String MESSAGE_SUCCESS = "Book shelf is decrypted";

    public static final String MESSAGE_WRONG_PASSWORD = "Incorrect key. Please try again.";

    private String key;
    private boolean isTesting;

    public DecryptCommand(String key) {
        this.key = key;
    }

    @Override
    public CommandResult execute() {
        if (isTesting == true) {
            key = "testing";
        }

        if (!LogicManager.getEncrypt()) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        if (key.equals(LogicManager.getKey())) {
            LogicManager.decrypt();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    public String getKey() {
        return key;
    }

    public void setTesting() {
        isTesting = true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DecryptCommand // instanceof handles nulls
                && this.key.equals(((DecryptCommand) other).getKey())); // state check
    }
}
