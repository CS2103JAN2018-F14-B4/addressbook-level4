package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;
import seedu.address.model.book.Rating;

public class RateCommandParserTest {
    private static final int EMPTY_RATING = -1;
    private static final int NON_EMPTY_RATING = 5;
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // add rating
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + NON_EMPTY_RATING;
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_BOOK, new Rating(NON_EMPTY_RATING));
        assertParseSuccess(parser, userInput, expectedCommand);

        // delete rating
        userInput = targetIndex.getOneBased() + " " + PREFIX_RATING + EMPTY_RATING;
        expectedCommand = new RateCommand(INDEX_FIRST_BOOK, new Rating(EMPTY_RATING));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RateCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RateCommand.COMMAND_WORD + " " + NON_EMPTY_RATING, expectedMessage);
    }
}
