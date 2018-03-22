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
    private RateCommandParser parser = new RateCommandParser();
    private final int nonEmptyRating = -1;

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // have RATING
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATING.toString() + nonEmptyRating;
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_BOOK, new Rating(nonEmptyRating));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no RATING
        userInput = targetIndex.getOneBased() + " " + PREFIX_RATING.toString();
        expectedCommand = new RateCommand(INDEX_FIRST_BOOK, new Rating(-1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RateCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RateCommand.COMMAND_WORD + " " + nonEmptyRating, expectedMessage);
    }
}
