package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_SUCCESS = "Remark success!";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }
}
