package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
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
    private static final int NON_EMPTY_RATING = 5;
    private EditCommandParser parser = new EditCommandParser();

    @Test

    public void parse_indexSpecified_success() throws Exception {
        // add rating
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + NON_EMPTY_RATING + " "
                + PREFIX_PRIORITY
                + "NONE" + PREFIX_STATUS + "UNREAD";
        EditCommand expectedCommand = new EditCommand(INDEX_FIRST_BOOK, new Rating(NON_EMPTY_RATING),
                Priority.DEFAULT_PRIORITY, Status.DEFAULT_STATUS);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delete rating
        userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + EMPTY_RATING + " " + PREFIX_PRIORITY
                + "LOW" + PREFIX_STATUS + "UNREAD";
        expectedCommand = new EditCommand(INDEX_FIRST_BOOK, new Rating(EMPTY_RATING),
                Priority.LOW, Status.UNREAD);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, EditCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, EditCommand.COMMAND_WORD + " " + NON_EMPTY_RATING + " "
                + Priority.DEFAULT_PRIORITY + " " + Status.DEFAULT_STATUS, expectedMessage);
    }
}
