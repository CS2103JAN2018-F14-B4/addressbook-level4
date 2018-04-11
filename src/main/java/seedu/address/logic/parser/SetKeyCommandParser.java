package seedu.address.logic.parser;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SetKeyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_KEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_KEY;

public class SetKeyCommandParser implements Parser<SetKeyCommand> {

    public SetKeyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLD_KEY, PREFIX_NEW_KEY);

        String oldKey = null, newKey = null;

        if (argMultimap.getValue(PREFIX_OLD_KEY).isPresent()) {
            oldKey = String.valueOf(ParserUtil.parseKey(argMultimap.getValue(PREFIX_OLD_KEY).get()));
        }

        if (argMultimap.getValue(PREFIX_NEW_KEY ).isPresent()) {
            newKey = String.valueOf(ParserUtil.parseKey(argMultimap.getValue(PREFIX_NEW_KEY).get()));
        }

        boolean isValid = CollectionUtil.isAnyNonNull(oldKey) && CollectionUtil.isAnyNonNull(newKey);

        if (!isValid) {
            throw new ParseException(SetKeyCommand.MESSAGE_NO_PARAMETERS);
        }

        return new SetKeyCommand(oldKey, newKey);
    }
}
