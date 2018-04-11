package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

public class SetKeyCommandTest {

    private Model model;

    @Test
    public void equals() {

        SetKeyCommand setKeyCommand = new SetKeyCommand("testing", "newkey");

        SetKeyCommand thesameCommand = new SetKeyCommand("testing", "newkey");

        // same value -> returns true
        assertTrue(setKeyCommand.equals(thesameCommand));

        // same object -> returns true
        assertTrue(setKeyCommand.equals(setKeyCommand));

        // null -> returns false
        assertFalse(setKeyCommand.equals(null));

        // different commandtypes -> returns false
        assertFalse(setKeyCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(setKeyCommand.equals(0));

    }

    @Test
    public void sameKeyTest() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        model.setKey("testing");
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
        SetKeyCommand skc = new SetKeyCommand("testing", "newkey");
        LogicManager lm = new LogicManager(model, network);
        skc.setTesting();
        skc.setData(model, network, new CommandHistory(), new UndoStack());
        String expect = SetKeyCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = skc.execute();

        assertEquals(expect, commandResult.feedbackToUser);
    }

    @Test
    public void differentKeyTest() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        model.setKey("wrongtesting");
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
        SetKeyCommand skc = new SetKeyCommand("wrongtesting", "newkey");
        LogicManager lm = new LogicManager(model, network);
        skc.setTesting();
        skc.setData(model, network, new CommandHistory(), new UndoStack());
        String expect = SetKeyCommand.WRONG_OLDKEY;
        CommandResult commandResult = skc.execute();

        assertEquals(expect, commandResult.feedbackToUser);
    }

}
