package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.logic.commands.AliasesCommand;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.ReviewsCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

import java.util.concurrent.CompletableFuture;

public class AliasesCommandSystemTest extends BibliotekSystemTest {

    @Test
    public void aliases() {
        Model model = getModel();
        String key = model.getKey();
        Network network = new Network() {
            @Override
            public CompletableFuture<ReadOnlyBookShelf> searchBooks(String parameters) {
                return null;
            }

            @Override
            public CompletableFuture<Book> getBookDetails(String bookId) {
                return null;
            }

            @Override
            public CompletableFuture<String> searchLibraryForBook(Book book) {
                return null;
            }

            @Override
            public void stop() {

            }
        };
        DecryptCommand decryptCommand = new DecryptCommand(key);
        decryptCommand.setData(model, network, new CommandHistory(), new UndoStack());
        decryptCommand.execute();
        assertAliasCommandSuccess();

        /* selecting a book should hide the alias list panel */
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased());
        assertTrue(getBookDetailsPanel().isVisible());
        assertFalse(getAliasListPanel().isVisible());

        /* executing aliases command should hide all other main panels */
        executeCommand(ReviewsCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased());
        assertAliasCommandSuccess();
    }

    /**
     * Executes the aliases command and verifies that,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the expected message.<br>
     * 4. {@code Model}, {@code Storage}, {@code SearchResultsPanel},
     *    and {@code RecentBooksPanel} remain unchanged.<br>
     * 5. Any selections in {@code BookListPanel}, {@code SearchResultsPanel},
     *    and {@code RecentBooksPanel} are all deselected.<br>
     * 6. {@code AliasListPanel} is visible, and {@code BookReviewsPanel} and {@code BookDetailsPanel} are hidden.<br>
     * 7. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertAliasCommandSuccess() {
        Model expectedModel = getModel();

        executeCommand(AliasesCommand.COMMAND_WORD);
        assertCommandBoxShowsDefaultStyle();

        assertApplicationDisplaysExpected("",
                String.format(AliasesCommand.MESSAGE_SUCCESS, expectedModel.getAliasList().size()), expectedModel);
        assertAliasListDisplaysExpected(expectedModel);

        assertSelectedBookListCardDeselected();
        assertSelectedSearchResultsCardDeselected();
        assertSelectedRecentBooksCardDeselected();
        assertAliasListPanelVisible();
        assertStatusBarUnchanged();
    }

    /**
     * Checks that {@code AliasListPanel} is visible, and that {@code BookDetailsPanel}
     * and {@code BookReviewsPanel} are not visible.
     */
    private void assertAliasListPanelVisible() {
        assertTrue(getAliasListPanel().isVisible());
        assertFalse(getBookDetailsPanel().isVisible());
        assertFalse(getBookReviewsPanel().isVisible());
    }
}
