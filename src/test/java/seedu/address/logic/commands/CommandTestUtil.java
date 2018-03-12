package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_ARTEMIS = "Artemis";
    public static final String VALID_TITLE_BABYLON = "Babylon's Ashes";
    public static final String VALID_AUTHOR_ARTEMIS = "Andy Weir";
    public static final String VALID_AUTHOR_BABYLON = "James S. A. Corey";
    public static final String VALID_CATEGORY_ARTEMIS = "Fiction";
    public static final String VALID_CATEGORY_BABYLON = "Fiction";
    public static final String VALID_DESCRIPTION_ARTEMIS = "This is Artemis.";
    public static final String VALID_DESCRIPTION_BABYLON = "This is Babylon's Ashes.";
    public static final String VALID_RATE_ARTEMIS = "This is Artemis.";
    public static final String VALID_RATE_BABYLON = "This is Babylon's Ashes.";

    public static final String TITLE_DESC_ARTEMIS = " " + PREFIX_TITLE + VALID_TITLE_ARTEMIS;
    public static final String TITLE_DESC_BABYLON = " " + PREFIX_TITLE + VALID_TITLE_BABYLON;
    public static final String AUTHOR_DESC_ARTEMIS = " " + PREFIX_AUTHOR + VALID_AUTHOR_ARTEMIS;
    public static final String AUTHOR_DESC_BABYLON = " " + PREFIX_AUTHOR + VALID_AUTHOR_BABYLON;
    public static final String CATEGORY_DESC_ARTEMIS = " " + PREFIX_CATEGORY + VALID_CATEGORY_ARTEMIS;
    public static final String CATEGORY_DESC_BABYLON = " " + PREFIX_CATEGORY + VALID_CATEGORY_BABYLON;
    public static final String DESCRIPTION_DESC_ARTEMIS = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ARTEMIS;
    public static final String DESCRIPTION_DESC_BABYLON = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BABYLON;
    public static final String RATE_DESC_ARTEMIS = " " + PREFIX_RATE + VALID_RATE_ARTEMIS;
    public static final String RATE_DESC_BABYLON = " " + PREFIX_RATE + VALID_RATE_BABYLON;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the book shelf and the filtered book list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BookShelf expectedBookShelf = new BookShelf(actualModel.getBookShelf());
        List<Book> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedBookShelf, actualModel.getBookShelf());
            assertEquals(expectedFilteredList, actualModel.getFilteredBookList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s book shelf.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        model.updateFilteredBookList(thisBook -> thisBook.equals(book));

        assertEquals(1, model.getFilteredBookList().size());
    }

    /**
     * Deletes the first book in {@code model}'s filtered list from {@code model}'s book shelf.
     */
    public static void deleteFirstBook(Model model) {
        Book firstBook = model.getFilteredBookList().get(0);
        try {
            model.deleteBook(firstBook);
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("Book in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
