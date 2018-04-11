package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

public class EncryptCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    }

    @Test
    public void equals() {

        EncryptCommand encryptCommand = new EncryptCommand();

        // same object -> returns true
        assertTrue(encryptCommand.equals(encryptCommand));

        // null -> returns false
        assertFalse(encryptCommand.equals(null));

        // different commandtypes -> returns false
        assertFalse(encryptCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(encryptCommand.equals(0));

    }

    @Test
    public void execute_exit_success() {
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
            public void stop() {

            }
        };
        EncryptCommand encryptCommand = new EncryptCommand();
        encryptCommand.setData(model, network, new CommandHistory(), new UndoStack());
        CommandResult result = encryptCommand.execute();
        String successMessage = EncryptCommand.MESSAGE_SUCCESS;

        assertEquals(successMessage, result.feedbackToUser);
    }
}
