package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;
import seedu.address.model.book.Rating;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Changes the rating of an existing Book in the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the book identified "
            + "by the index number used in the last book listing. "
            + "Existing rating will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATING + "RATING\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "-1";

    public static final String MESSAGE_ADD_RATING_SUCCESS = "Added rating to Book: %1$s";
    public static final String MESSAGE_DELETE_RATING_SUCCESS = "Removed rating from Book: %1$s";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Cannot be rated.";

    private final Index index;
    private final Rating rating;

    private Book bookToEdit;
    private Book editedBook;

    /**
     * @param index of the book in the filtered book list to edit the rating
     * @param rating of the book to be updated to
     */
    public RateCommand(Index index, Rating rating) {
        requireAllNonNull(index, rating);

        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(bookToEdit, editedBook);
        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }

        try {
            model.updateBook(bookToEdit, editedBook);
        } catch (DuplicateBookException dpe) {
            throw new AssertionError("Changing target Book's rating should not result in a duplicate");
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target Book cannot be missing");
        }
        return new CommandResult(generateSuccessMessage(editedBook));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToEdit = lastShownList.get(index.getZeroBased());
        editedBook = new Book(bookToEdit.getGid(), bookToEdit.getIsbn(), bookToEdit.getAuthors(),
                bookToEdit.getTitle(), bookToEdit.getCategories(), bookToEdit.getDescription(), rating,
                bookToEdit.getPublisher(), bookToEdit.getPublicationDate());
    }

    /**
     * Generates a command execution success message based on whether the rating is added to or removed from
     * {@code bookToEdit}.
     */
    private String generateSuccessMessage(Book bookToEdit) {
        String message = (rating.value != -1) ? MESSAGE_ADD_RATING_SUCCESS : MESSAGE_DELETE_RATING_SUCCESS;
        return String.format(message, bookToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && rating.equals(e.rating);
    }
}
