package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class RateCommand extends UndoableCommand{
    public static final String COMMAND_WORD = "rate";
    public static final String MESSAGE_SUCCESS = "Rate successfully";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }
}
