package systemtests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.logic.commands.AddAliasCommand;
import seedu.address.logic.commands.AliasesCommand;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.DeleteAliasCommand;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.alias.Alias;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

import java.util.concurrent.CompletableFuture;

//@@author takuyakanbr
public class DeleteAliasCommandSystemTest extends BibliotekSystemTest {

    @Test
    public void deleteAlias() {
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
        executeCommand(AddAliasCommand.COMMAND_WORD + " s cmd/select");
        executeCommand(AddAliasCommand.COMMAND_WORD + " read cmd/list s/read by/title");

        /* --------------------------------- Performing valid delete operation -------------------------------------- */

        /* case: delete an existing alias -> deleted */
        Alias alias = new Alias("s", "select", "");
        assertCommandSuccess(DeleteAliasCommand.COMMAND_WORD + " s", alias.getName(),
                String.format(DeleteAliasCommand.MESSAGE_SUCCESS, alias));

        executeCommand(AliasesCommand.COMMAND_WORD);
        assertTrue(getAliasListPanel().isVisible());

        /* case: delete an existing alias -> deleted and alias list updated */
        alias = new Alias("read", "list", "s/read by/title");
        assertCommandSuccess(DeleteAliasCommand.COMMAND_WORD + "   ReaD   ", alias.getName(),
                String.format(DeleteAliasCommand.MESSAGE_SUCCESS, alias));

        /* case: delete a non-existing alias -> ignored */
        alias = new Alias("notfound", "notfound", "notfound");
        assertCommandSuccess(DeleteAliasCommand.COMMAND_WORD + " notfound", alias.getName(),
                String.format(DeleteAliasCommand.MESSAGE_NOT_FOUND, "notfound"));
    }

    /**
     * Executes the deletealias {@code command} and verifies that,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the expected message.<br>
     * 4. {@code Model} and {@code Storage} equal to the corresponding components after deleting.<br>
     * 5. If the alias list is visible, the aliases in the list matches the expected alias list.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String toDelete, String expectedMessage) {
        Model expectedModel = getModel();
        expectedModel.removeAlias(toDelete);

        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();

        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        if (getAliasListPanel().isVisible()) {
            assertAliasListDisplaysExpected(expectedModel);
        }

        assertStatusBarUnchanged();
    }
}
