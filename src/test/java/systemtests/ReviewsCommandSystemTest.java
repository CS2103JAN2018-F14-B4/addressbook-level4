package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RecentCommand;
import seedu.address.logic.commands.ReviewsCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

public class ReviewsCommandSystemTest extends BibliotekSystemTest {

    @Test
    public void reviews() {
        /* ------------ Perform reviews operations on the shown book list ------------ */
        String command = "   " + ReviewsCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + "   ";
        assertCommandSuccess(command, getModel().getDisplayBookList().get(INDEX_FIRST_BOOK.getZeroBased()));

        /* ------------ Perform reviews operations on the filtered book list ------------ */
        executeCommand(ListCommand.COMMAND_WORD + " s/unread");
        Index bookCount = Index.fromOneBased(getModel().getDisplayBookList().size());
        command = ReviewsCommand.COMMAND_WORD + " " + bookCount.getOneBased();
        assertCommandSuccess(command, getModel().getDisplayBookList().get(bookCount.getZeroBased()));

        /* ------------ Perform invalid reviews operations ------------ */
        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ReviewsCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewsCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getDisplayBookList().size() + 1;
        assertCommandFailure(ReviewsCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ReViEwS 1", MESSAGE_UNKNOWN_COMMAND);

        /* ------------ Perform reviews operations on the shown search results list ------------ */
        executeBackgroundCommand(SearchCommand.COMMAND_WORD + " hello", SearchCommand.MESSAGE_SEARCHING);
        assertCommandSuccess(ReviewsCommand.COMMAND_WORD + " 1", getModel().getSearchResultsList().get(0));
        assertCommandFailure(ReviewsCommand.COMMAND_WORD + " 39", MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* ------------ Perform reviews operations on the shown recent books list ------------ */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        executeCommand(RecentCommand.COMMAND_WORD);
        assertCommandSuccess(ReviewsCommand.COMMAND_WORD + " 1", getModel().getRecentBooksList().get(0));
        assertCommandFailure(ReviewsCommand.COMMAND_WORD + " 51", MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that, after the reviews page has loaded,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the load successful message.<br>
     * 4. {@code Model}, {@code Storage}, {@code BookListPanel}, {@code SearchResultsPanel},
     * and {@code RecentBooksPanel} remain unchanged.<br>
     * 5. Selected books card remain unchanged.<br>
     * 6. {@code BookReviewsPanel} is visible and {@code BookDetailsPanel} is hidden.
     * 7. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see BibliotekSystemTest#assertSelectedBookListCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Book toLoad) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();

        String expectedResultMessage = String.format(ReviewsCommand.MESSAGE_SUCCESS, toLoad);

        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedBookListCardUnchanged();
        assertSelectedSearchResultsCardUnchanged();
        assertSelectedRecentBooksCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertBookReviewsPanelVisible();
        assertStatusBarUnchanged();
    }

    /**
     * Checks that {@code BookReviewsPanel} is visible and {@code BookDetailsPanel}
     * is not visible.
     */
    private void assertBookReviewsPanelVisible() {
        assertTrue(getBookReviewsPanel().isVisible());
        assertFalse(getBookDetailsPanel().isVisible());
    }
}
