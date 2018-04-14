package seedu.address.logic.parser;

import seedu.address.logic.commands.UnlockCommand;

//@@author 592363789
/**
 * Parses input arguments and creates a new UnlockCommand object.
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of UnlockCommand
     * and returns an UnlockCommand object for execution.
     */
    public UnlockCommand parse(String args) {
        String key = ParserUtil.parseKey(args);
        return new UnlockCommand(key);
    }

}
