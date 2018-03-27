package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

public class EditCommandParserTest {
    private static final int EMPTY_RATING = -1;
    private static final String EMPTY_PRIORITY = "";
    private static final String EMPTY_STATUS  = "";
    private static final int NON_EMPTY_RATING = 5;
    private static final String NON_EMPTY_PRIORITY = "LOW";
    private static final String NON_EMPTY_STATUS = "UNREAD";
    private EditCommandParser parser = new EditCommandParser();

    @Test

    public void parse_indexSpecified_success() throws Exception {
        // add rating
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + NON_EMPTY_RATING;
        EditCommand expectedCommand = new EditCommand(INDEX_FIRST_BOOK, new Rating(NON_EMPTY_RATING),
                new Priority(NON_EMPTY_PRIORITY), new Status(NON_EMPTY_STATUS));
        assertParseSuccess(parser, userInput, expectedCommand);

        // delete rating
        userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + EMPTY_RATING;
        expectedCommand = new EditCommand(INDEX_FIRST_BOOK, new Rating(EMPTY_RATING),
                new Priority(EMPTY_PRIORITY), new Status(EMPTY_STATUS));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, EditCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, EditCommand.COMMAND_WORD + " " + NON_EMPTY_RATING + " "
                + NON_EMPTY_PRIORITY + " " + NON_EMPTY_STATUS, expectedMessage);
    }
}
