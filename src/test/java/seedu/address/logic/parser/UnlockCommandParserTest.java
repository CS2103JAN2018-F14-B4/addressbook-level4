package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;

//@@author 592363789
public class UnlockCommandParserTest {

    private UnlockCommandParser parser = new UnlockCommandParser();

    @Test
    public void parse_validCompulsoryField_success() throws Exception {
        assertParseSuccess(parser,  "oldkey",
                new UnlockCommand("oldkey"));
    }

}
