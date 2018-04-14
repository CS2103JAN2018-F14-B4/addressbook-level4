package seedu.address.logic.parser;
//@@author 592363789
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetPasswordCommand;

public class SetPasswordCommandParserTest {

    private SetPasswordCommandParser parser = new SetPasswordCommandParser();

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {

        String expectedinvalidMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedinvalidMessage);

        // no one of the key
        assertParseFailure(parser, " ",
                expectedinvalidMessage);

    }

    @Test
    public void parse_validCompulsoryField_success() throws Exception {
        assertParseSuccess(parser, SetPasswordCommand.COMMAND_WORD + " " + PREFIX_OLD + " " + "oldkey"
                        + " " + PREFIX_NEW + " " + "newkey",
                new SetPasswordCommand("oldkey", "newkey"));
        // no one of the key
        assertParseSuccess(parser, SetPasswordCommand.COMMAND_WORD + " " + PREFIX_NEW + "newkey",
                new SetPasswordCommand("", "newkey"));
    }

}
