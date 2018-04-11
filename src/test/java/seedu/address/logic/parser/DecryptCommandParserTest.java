package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DecryptCommand;

//@@author 592363789
public class DecryptCommandParserTest {

    private DecryptCommandParser parser = new DecryptCommandParser();

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {

        String expectedinvalidMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedinvalidMessage);

    }

    @Test
    public void parse_validCompulsoryField_success() throws Exception {
        assertParseSuccess(parser,  "oldkey",
                new DecryptCommand("oldkey"));
    }

}
