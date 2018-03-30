package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BABYLON;
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
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;
import seedu.address.network.NetworkManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());

    @Test
    public void execute_invalidBookIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayBookList().size() + 1);
        EditCommand editCommand = prepareCommand(outOfBoundIndex, VALID_RATING_BABYLON, VALID_PRIORITY_BABYLON,
                VALID_STATUS_BABYLON);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    /**
     * Test with index larger than size of filtered list
     * but smaller than size of book shelf
     */
    @Test
    public void execute_invalidBookIndexFilteredList_failure() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookShelf().getBookList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex, VALID_RATING_BABYLON, VALID_PRIORITY_BABYLON,
                VALID_STATUS_BABYLON);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_ARTEMIS),
                Priority.DEFAULT_PRIORITY, Status.DEFAULT_STATUS);

        // same values -> returns true
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_ARTEMIS),
                Priority.DEFAULT_PRIORITY, Status.DEFAULT_STATUS);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BOOK, new Rating(VALID_RATING_ARTEMIS),
                Priority.DEFAULT_PRIORITY, Status.DEFAULT_STATUS)));

        // different Rating -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BOOK, new Rating(VALID_RATING_BABYLON),
                Priority.DEFAULT_PRIORITY, Status.DEFAULT_STATUS)));
    }

    /**
     * Returns an {@code EditCommand} with the specified parameters.
     */
    private EditCommand prepareCommand(Index index, int rating, String priority, String status) {
        EditCommand editCommand = new EditCommand(index, new Rating(rating),
                Priority.findPriority(priority), Status.findStatus(status));
        editCommand.setData(model, mock(NetworkManager.class), new CommandHistory(), new UndoStack());
        return editCommand;
    }
}
