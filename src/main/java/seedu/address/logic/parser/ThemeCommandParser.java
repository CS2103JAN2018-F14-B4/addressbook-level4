package seedu.address.logic.parser;

import seedu.address.commons.core.Theme;
import seedu.address.commons.exceptions.InvalidThemeException;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ThemeCommand object.
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ThemeCommand parse(String args) throws ParseException {
        try {
            Theme newTheme = Theme.getThemeByName(args.trim());
            return new ThemeCommand(newTheme);
        } catch (InvalidThemeException e) {
            throw new ParseException(String.format(ThemeCommand.MESSAGE_INVALID_THEME, args.trim()));
        }
    }
}
