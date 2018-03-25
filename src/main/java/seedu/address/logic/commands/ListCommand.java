package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToBookListRequestEvent;
import seedu.address.model.book.Book;

/**
 * Lists all books in the book shelf to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a listing of your books, with optional filtering and sorting.\n"
            + "Parameters: "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]"
            + "[" + PREFIX_STATUS + "STATUS]"
            + "[" + PREFIX_PRIORITY + "PRIORITY]"
            + "[" + PREFIX_RATING + "RATING]"
            + "[" + PREFIX_SORT_BY + "SORT_BY]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_STATUS + " reading "
            + PREFIX_SORT_BY + "priority";

    public static final String MESSAGE_SUCCESS = "Listed %s books.";
    public static final String MESSAGE_INVALID_STATUS = "Invalid status entered. "
            + "Allowed values are: READ, R, UNREAD, U, READING, and RD.";
    public static final String MESSAGE_INVALID_PRIORITY = "Invalid priority entered. "
            + "Allowed values are: NONE, N, LOW, L, MEDIUM, M, HIGH, and H.";
    public static final String MESSAGE_INVALID_RATING = "Invalid rating entered. "
            + "Please enter a valid integer between -1 and 5 (both inclusive).";
    public static final String MESSAGE_INVALID_SORT_BY = "Invalid sorting mode entered. "
            + "Allowed values are: TITLE, T, STATUS, S, PRIORITY, P, RATING, and R. "
            + "Append a 'D' to sort in descending order.";

    private final Predicate<Book> bookFilter;
    private final Comparator<Book> bookComparator;

    /**
     * Creates a ListCommand to show a listing of books.
     *
     * @param bookFilter the filter used for filtering the books to be displayed.
     * @param bookComparator the comparator used for sorting the books to be displayed.
     */
    public ListCommand(Predicate<Book> bookFilter, Comparator<Book> bookComparator) {
        requireAllNonNull(bookFilter, bookComparator);
        this.bookFilter = bookFilter;
        this.bookComparator = bookComparator;
    }

    @Override
    public CommandResult execute() {
        model.updateBookListFilter(bookFilter);
        model.updateBookListSorter(bookComparator);
        EventsCenter.getInstance().post(new SwitchToBookListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getDisplayBookList().size()));
    }
}
