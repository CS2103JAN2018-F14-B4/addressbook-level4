package seedu.address.logic.parser;
//@@author 592363789
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_KEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_KEY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetKeyCommand;

public class SetKeyCommandParserTest {

    private SetKeyCommandParser parser = new SetKeyCommandParser();

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {

        String expectedinvalidMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetKeyCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedinvalidMessage);

        // no one of the key
        assertParseFailure(parser, " ",
                expectedinvalidMessage);

    }

    @Test
    public void parse_validCompulsoryField_success() throws Exception {
        assertParseSuccess(parser, SetKeyCommand.COMMAND_WORD + " " + PREFIX_OLD_KEY + " " + "oldkey"
                        + " " + PREFIX_NEW_KEY + " " + "newkey",
                new SetKeyCommand("oldkey", "newkey"));
        // no one of the key
        assertParseSuccess(parser, SetKeyCommand.COMMAND_WORD + " " + PREFIX_NEW_KEY + "newkey",
                new SetKeyCommand("", "newkey"));
    }

}
