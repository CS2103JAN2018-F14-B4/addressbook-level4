package seedu.address.logic.parser;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SetKeyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_KEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_KEY;

public class SetKeyCommandParser implements Parser<SetKeyCommand> {
    public SetKeyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLD_KEY, PREFIX_NEW_KEY);

        String oldKey = String.valueOf(argMultimap.getValue(PREFIX_OLD_KEY).get());
        String newKey = String.valueOf(argMultimap.getValue(PREFIX_NEW_KEY).get());

        return new SetKeyCommand(oldKey, newKey);
    }
}
