package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_KEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_KEY;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.SetKeyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetKeyCommand object
 */
public class SetKeyCommandParser implements Parser<SetKeyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetKeyCommand
     * and returns an SetKeyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetKeyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLD_KEY, PREFIX_NEW_KEY);

        String oldKey = null;
        if (argMultimap.getValue(PREFIX_OLD_KEY).isPresent()) {
            oldKey = String.valueOf(ParserUtil.parseKey(argMultimap.getValue(PREFIX_OLD_KEY).get()));
        }

        String newKey = null;
        if (argMultimap.getValue(PREFIX_NEW_KEY).isPresent()) {
            newKey = String.valueOf(ParserUtil.parseKey(argMultimap.getValue(PREFIX_NEW_KEY).get()));
        }

        boolean isValid = CollectionUtil.isAnyNonNull(oldKey) || CollectionUtil.isAnyNonNull(newKey);
        boolean isV = CollectionUtil.isAnyNonNull(oldKey) && CollectionUtil.isAnyNonNull(newKey);

        if (args.trim().isEmpty() || !isV) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetKeyCommand.MESSAGE_USAGE));
        }

        if (!isValid) {
            throw new ParseException(SetKeyCommand.MESSAGE_NO_PARAMETERS);
        }

        return new SetKeyCommand(oldKey, newKey);
    }
}
