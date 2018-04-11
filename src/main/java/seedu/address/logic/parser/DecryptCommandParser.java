package seedu.address.logic.parser;

import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DecryptCommand object
 */
public class DecryptCommandParser implements Parser<DecryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DecryptCommand
     * and returns an DecryptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DecryptCommand parse(String args) throws ParseException {
        if (args == null || args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.COMMAND_WORD));
        }
        String key = ParserUtil.parseKey(args);
        return new DecryptCommand(key);
    }

}
