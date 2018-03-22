package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Rating;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RateCommandTest {

    public static final int RATING_STUB = -1;

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());

    @Test
    public void execute_invalidBookIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING_BABYLON);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidBookIndexFilteredList_failure() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookShelf().getBookList().size());

        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING_BABYLON);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_ARTEMIS));

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_ARTEMIS));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_BOOK, new Rating(VALID_RATING_ARTEMIS))));

        // different Rating -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_BABYLON))));
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code Rating}.
     */
    private RateCommand prepareCommand(Index index, int rating) {
        RateCommand rateCommand = new RateCommand(index, new Rating(rating));
        rateCommand.setData(model, new CommandHistory(), new UndoStack());
        return rateCommand;
    }
}
