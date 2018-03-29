package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Changes the rating of an existing Book in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the book identified "
            + "by the index number used in the last book listing. "
            + "Existing rating will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATING + "RATING\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "-1" + PREFIX_PRIORITY + Priority.DEFAULT_PRIORITY + PREFIX_STATUS
            + Status.DEFAULT_STATUS;

    public static final String MESSAGE_ADD_EDITING_SUCCESS = "Editing to Book: %1$s";
    public static final String MESSAGE_DELETE_EDITING_SUCCESS = "Removed editing from Book: %1$s";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Cannot be edited.";

    private final Index index;
    private final Rating rating;
    private final Priority priority;
    private final Status status;
    private Book bookToEdit;
    private Book editedBook;

    /**
     * @param index of the book in the filtered book list to edit the rating
     * @param rating of the book to be updated to
     */
    public EditCommand(Index index, Rating rating, Priority priority, Status status) {
        requireAllNonNull(index, rating, priority, status);

        this.index = index;
        this.rating = rating;
        this.priority = priority;
        this.status = status;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }

        try {
            model.updateBook(bookToEdit, editedBook);
        } catch (DuplicateBookException dpe) {
            throw new AssertionError(
                    "Changing target Book's editing should not result in a duplicate");
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target Book cannot be missing");
        }
        return new CommandResult(generateSuccessMessage(editedBook));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireAllNonNull(bookToEdit, editedBook);
        List<Book> lastShownList = model.getDisplayBookList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToEdit = lastShownList.get(index.getZeroBased());
        editedBook = new Book(bookToEdit.getGid(), bookToEdit.getIsbn(), bookToEdit.getAuthors(),
                bookToEdit.getTitle(), bookToEdit.getCategories(), bookToEdit.getDescription(),
                bookToEdit.getStatus(), bookToEdit.getPriority(), rating,
                bookToEdit.getPublisher(),
                bookToEdit.getPublicationDate());
    }

    /**
     * Generates a command execution success message based on whether the rating is added to or removed from
     * {@code bookToEdit}.
     */
    private String generateSuccessMessage(Book bookToEdit) {
        String message = (rating.rating != -1) ? MESSAGE_ADD_EDITING_SUCCESS : MESSAGE_DELETE_EDITING_SUCCESS;
        return String.format(message, bookToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && rating.equals(e.rating) && priority.equals(e.priority) && status.equals(e.status);
    }
}
