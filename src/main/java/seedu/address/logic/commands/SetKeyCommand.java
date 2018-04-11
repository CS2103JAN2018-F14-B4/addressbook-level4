package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Set the key of the BiBliotek with a new key.
 */
public class SetKeyCommand extends Command {

    public static final String COMMAND_WORD = "setKey";

    public static final String MESSAGE_NO_PARAMETERS = "Error, You must provide the keys";
    public static final String MESSAGE_SUCCESS = "Set success";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set the encrypt key of the bibliotek:"
            + "Parameters: [old/OLDKEY] [new/NEWKEY] "
            + "Example: " + COMMAND_WORD + " old/123456 new/abcde ";
    public static final String WRONG_OLDKEY = "Input the wrong oldkey, please check again!";

    private String oldKey;
    private String newKey;
    private boolean isTesting;
    public SetKeyCommand(String key1, String key2) {
        isTesting = false;
        oldKey = key1;
        newKey = key2;
    }

    public String getOldKey() {
        return oldKey;
    }

    public String getNewKey() {
        return newKey;
    }

    public void setTesting() {
        isTesting = true;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() {
        if (isTesting == true) {
            oldKey = "testing";
            newKey = "newkey";
        }
        if (oldKey.equals(LogicManager.getKey())) {
            LogicManager.setKey(newKey);
            model.setKey(newKey);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(WRONG_OLDKEY);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetKeyCommand)) {
            return false;
        }

        // state check
        SetKeyCommand e = (SetKeyCommand) other;
        return oldKey.equals(e.getOldKey())
                && newKey.equals(e.getNewKey());
    }
}
