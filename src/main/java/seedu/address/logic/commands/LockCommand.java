package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class LockCommand extends Command{

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lock the Bibliotek.\n"
            + "Parameters: PASSWORD (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 123456";

    public static final String MESSAGE_SUCCESS = "Bibliotek is locked!";

    private static String
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
