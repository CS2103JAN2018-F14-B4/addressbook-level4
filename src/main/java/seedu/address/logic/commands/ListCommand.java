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
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToBookListRequestEvent;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

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

    private final FilterDescriptor filterDescriptor;
    private final Comparator<Book> bookComparator;

    /**
     * Creates a ListCommand to show a listing of books.
     *
     * @param filterDescriptor the filters used for filtering the books to be displayed.
     * @param bookComparator the comparator used for sorting the books to be displayed.
     */
    public ListCommand(FilterDescriptor filterDescriptor, Comparator<Book> bookComparator) {
        requireAllNonNull(filterDescriptor, bookComparator);
        this.filterDescriptor = filterDescriptor;
        this.bookComparator = bookComparator;
    }

    @Override
    public CommandResult execute() {
        model.updateBookListFilter(filterDescriptor.buildCombinedFilter());
        model.updateBookListSorter(bookComparator);
        EventsCenter.getInstance().post(new SwitchToBookListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getDisplayBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.filterDescriptor.equals(((ListCommand) other).filterDescriptor) // state check
                && this.bookComparator.equals(((ListCommand) other).bookComparator));
    }

    /**
     * Stores the filters used for filtering the books to be displayed.
     */
    public static class FilterDescriptor {
        private final List<Predicate<Book>> filters;
        private String filterCode; // used internally for equality testing

        public FilterDescriptor() {
            filters = new LinkedList<>();
            filterCode = "";
        }

        public FilterDescriptor(FilterDescriptor descriptor) {
            filters = new LinkedList<>(descriptor.filters);
            filterCode = descriptor.filterCode;
        }

        /** Adds a filter that selects books with titles containing the given {@code title}. */
        public void addTitleFilter(String title) {
            filters.add(book -> book.getTitle().title.toLowerCase().contains(title.toLowerCase()));
            filterCode += "t[" + title.toLowerCase() + "]";
        }

        /** Adds a filter that selects books with authors containing the given {@code author}. */
        public void addAuthorFilter(String author) {
            filters.add(book -> book.getAuthors().stream()
                    .anyMatch(bookAuthor -> bookAuthor.fullName.toLowerCase().contains(author.toLowerCase())));
            filterCode += "a[" + author.toLowerCase() + "]";
        }

        /** Adds a filter that selects books with categories containing the given {@code category}. */
        public void addCategoryFilter(String category) {
            filters.add(book -> book.getCategories().stream()
                    .anyMatch(bookCategory -> bookCategory.category.toLowerCase().contains(category.toLowerCase())));
            filterCode += "c[" + category.toLowerCase() + "]";
        }

        /** Adds a filter that selects books with status matching the given {@code status}. */
        public void addStatusFilter(Status status) {
            filters.add(book -> status.equals(book.getStatus()));
            filterCode += "s[" + status + "]";
        }

        /** Adds a filter that selects books with priority matching the given {@code priority}. */
        public void addPriorityFilter(Priority priority) {
            filters.add(book -> priority.equals(book.getPriority()));
            filterCode += "p[" + priority + "]";
        }

        /** Adds a filter that selects books with ratings matching the given {@code rating}. */
        public void addRatingFilter(Rating rating) {
            filters.add(book -> rating.equals(book.getRating()));
            filterCode += "r[" + rating + "]";
        }

        protected Predicate<Book> buildCombinedFilter() {
            List<Predicate<Book>> partialFilters = new LinkedList<>(filters);
            return book -> partialFilters.stream().allMatch(filter -> filter.test(book));
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof FilterDescriptor // instanceof handles nulls
                    && this.filterCode.equals(((FilterDescriptor) other).filterCode)); // state check
        }
    }
}
