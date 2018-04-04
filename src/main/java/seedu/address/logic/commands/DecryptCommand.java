package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;

public class DecryptCommand extends Command {

    public static final String COMMAND_WORD = "decrypted";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Decrypted the Bibliotek.\n"
            + "Parameters: PASSWORD\n"
            + "Example: " + COMMAND_WORD + " 123456";

    public static final String MESSAGE_SUCCESS = "Bibliotek is decrypted!";

    public static final String MESSAGE_WRONG_PASSWORD = "You have input wrong password, please check again!";

    private String password;

    public DecryptCommand(String word) {
        this.password = word;
    }

    @Override
    public CommandResult execute() {
        if (!LogicManager.getLock()) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        if (this.password.equals(LogicManager.getKey())) {
            LogicManager.unLock();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DecryptCommand // instanceof handles nulls
                && this.password.equals(((DecryptCommand) other).getPassword())); // state check
    }
}
