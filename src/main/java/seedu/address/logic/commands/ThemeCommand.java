package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Theme;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the application theme.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme used by the application.\n"
            + "Parameters: THEME_NAME\n"
            + "Example: " + COMMAND_WORD + " dark";

    public static final String MESSAGE_SUCCESS = "Application theme changed to: %1$s";
    public static final String MESSAGE_INVALID_THEME = "Invalid application theme: %1$s";

    private final Theme newTheme;

    public ThemeCommand(Theme newTheme) {
        this.newTheme = newTheme;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(newTheme));
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTheme.getThemeName()));
    }
}
