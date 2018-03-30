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
 * Edits the status, priority, and rating of an existing book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating, status, and priority"
            + " of the book identified "
            + "by the index number used in the last book listing. "
            + "Existing rating will be overwritten by the input.\n"
            + "Parameters: [s/STATUS] [p/PRIORITY] [r/RATING] INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "-1" + PREFIX_PRIORITY + "low" + PREFIX_STATUS
            + "unread";

    public static final String MESSAGE_ADD_EDITING_SUCCESS = "Edited Book: %1$s";
    public static final String MESSAGE_DELETE_EDITING_SUCCESS = "Removed editing from Book: %1$s";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Cannot be edited.";
    public static final String MESSAGE_INVALID_STATUS = "Invalid status entered. "
            + "Allowed values are: READ, R, UNREAD, U, READING, and RD.";
    public static final String MESSAGE_INVALID_PRIORITY = "Invalid priority entered. "
            + "Allowed values are: NONE, N, LOW, L, MEDIUM, M, HIGH, and H.";
    public static final String MESSAGE_INVALID_RATING = "Invalid rating entered. "
            + "Please enter a valid integer between -1 and 5 (both inclusive).";
    public static final String MESSAGE_INVALID_SORT_BY = "Invalid sorting mode entered. "
            + "Allowed values are: RATING, R, STATUS, S, PRIORITY, and P. ";

    private final Index index;
    private final Rating rating;
    private final Priority priority;
    private final Status status;
    private Book bookToEdit;
    private Book editedBook;

    /**
     * @param index of the book in the filtered book list to edit the rating.
     * @param rating of the book to be updated to.
     * @param priority of the book to be updated to.
     * @param  status of the book to be updated to.
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
        requireAllNonNull(bookToEdit, editedBook);
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
        return new CommandResult(String.format(MESSAGE_ADD_EDITING_SUCCESS, editedBook));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getDisplayBookList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToEdit = lastShownList.get(index.getZeroBased());
        editedBook = new Book(bookToEdit.getGid(), bookToEdit.getIsbn(), bookToEdit.getAuthors(),
                bookToEdit.getTitle(), bookToEdit.getCategories(), bookToEdit.getDescription(),
                status, priority, rating,
                bookToEdit.getPublisher(),
                bookToEdit.getPublicationDate());
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
