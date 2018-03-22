package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.Rating;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.book.exceptions.BookNotFoundException;

/**
 * Changes the rating of an existing Book in the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the book identified "
            + "by the index number used in the last book listing. "
            + "Existing rating will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATING + "[RATING]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "5";

    public static final String MESSAGE_ADD_RATING_SUCCESS = "Added rating to Book: %1$s";
    public static final String MESSAGE_DELETE_RATING_SUCCESS = "Removed rating from Book: %1$s";

    private final Index index;
    private final Rating rating;

    private Book BookToEdit;
    private Book editedBook;

    /**
     * @param index of the book in the filtered book list to edit the rating
     * @param rating of the book to be updated to
     */
    public RateCommand(Index index, Rating rating) {
        requireNonNull(index);
        requireNonNull(rating);

        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(BookToEdit);
        requireNonNull(editedBook);

        try {
            model.updateBook(BookToEdit, editedBook);
        } catch (DuplicateBookException dpe) {
            throw new AssertionError("Changing target Book's rating should not result in a duplicate");
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target Book cannot be missing");
        }
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        return new CommandResult(generateSuccessMessage(editedBook));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        BookToEdit = lastShownList.get(index.getZeroBased());
        editedBook = new Book(BookToEdit.getGid(), BookToEdit.getIsbn(), BookToEdit.getAuthors(),
                BookToEdit.getTitle(),BookToEdit.getCategories(), BookToEdit.getDescription(), rating,
                BookToEdit.getPublisher(), BookToEdit.getPublicationDate());
    }

    /**
     * Generates a command execution success message based on whether the rating is added to or removed from
     * {@code BookToEdit}.
     */
    private String generateSuccessMessage(Book BookToEdit) {
        String message = !(rating.value == -1) ? MESSAGE_ADD_RATING_SUCCESS : MESSAGE_DELETE_RATING_SUCCESS;
        return String.format(message, BookToEdit);
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
