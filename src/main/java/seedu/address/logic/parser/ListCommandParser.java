package seedu.address.logic.parser;

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
import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AUTHOR,
                PREFIX_CATEGORY, PREFIX_STATUS, PREFIX_PRIORITY, PREFIX_RATING, PREFIX_SORT_BY);

        BookListFilterBuilder filterBuilder = new BookListFilterBuilder();
        argMultimap.getValue(PREFIX_TITLE).ifPresent(filterBuilder::addTitleFilter);
        argMultimap.getValue(PREFIX_AUTHOR).ifPresent(filterBuilder::addAuthorFilter);
        argMultimap.getValue(PREFIX_CATEGORY).ifPresent(filterBuilder::addCategoryFilter);

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            Status status = Status.findStatus(argMultimap.getValue(PREFIX_STATUS).get());
            if (status == null) {
                throw new ParseException(ListCommand.MESSAGE_INVALID_STATUS);
            }
            filterBuilder.addStatusFilter(status);
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            Priority priority = Priority.findPriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            if (priority == null) {
                throw new ParseException(ListCommand.MESSAGE_INVALID_PRIORITY);
            }
            filterBuilder.addPriorityFilter(priority);
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            Rating rating = parseRating(argMultimap.getValue(PREFIX_RATING).get());
            filterBuilder.addRatingFilter(rating);
        }

        Comparator<Book> comparator = Model.DEFAULT_BOOK_COMPARATOR;
        if (argMultimap.getValue(PREFIX_SORT_BY).isPresent()) {
            SortMode sortMode = SortMode.findSortMode(argMultimap.getValue(PREFIX_SORT_BY).get());
            if (sortMode == null) {
                throw new ParseException(ListCommand.MESSAGE_INVALID_SORT_BY);
            }
            comparator = sortMode.comparator;
        }

        return new ListCommand(filterBuilder.buildCombinedFilter(), comparator);
    }

    /**
     * Parses the given {@code ratingString} and returns a {@code Rating}.
     *
     * @throws ParseException if the string does not represent a valid rating.
     */
    private static Rating parseRating(String ratingString) throws ParseException {
        try {
            return new Rating(Integer.parseInt(ratingString));
        } catch (IllegalArgumentException e) {
            throw new ParseException(ListCommand.MESSAGE_INVALID_RATING);
        }
    }

    /**
     * Builds the filter that is used to filter the display book list.
     */
    private static class BookListFilterBuilder {
        private final List<Predicate<Book>> filters = new LinkedList<>();

        private void addTitleFilter(String title) {
            filters.add(book -> title.equalsIgnoreCase(book.getTitle().title));
        }

        private void addAuthorFilter(String author) {
            filters.add(book -> book.getAuthors().stream()
                    .anyMatch(bookAuthor -> author.equalsIgnoreCase(bookAuthor.fullName)));
        }

        private void addCategoryFilter(String category) {
            filters.add(book -> book.getCategories().stream()
                    .anyMatch(bookCategory -> category.equalsIgnoreCase(bookCategory.category)));
        }

        private void addStatusFilter(Status status) {
            filters.add(book -> status.equals(book.getStatus()));
        }

        private void addPriorityFilter(Priority priority) {
            filters.add(book -> priority.equals(book.getPriority()));
        }

        private void addRatingFilter(Rating rating) {
            filters.add(book -> rating.equals(book.getRating()));
        }

        private Predicate<Book> buildCombinedFilter() {
            List<Predicate<Book>> partialFilters = new LinkedList<>(filters);
            return book -> partialFilters.stream().allMatch(filter -> filter.test(book));
        }
    }

    /**
     * Represents a sorting mode, which specifies the comparator used to sort the display book list.
     */
    private enum SortMode {
        TITLE(Comparator.comparing(Book::getTitle), "t", "titlea", "ta"),
        TITLED((book1, book2) -> book2.getTitle().compareTo(book1.getTitle()), "td"),
        STATUS(Comparator.comparing(Book::getStatus), "s", "statusa", "sa"),
        STATUSD((book1, book2) -> book2.getStatus().compareTo(book1.getStatus()), "sd"),
        PRIORITY(Comparator.comparing(Book::getPriority), "p", "prioritya", "pa"),
        PRIORITYD((book1, book2) -> book2.getPriority().compareTo(book1.getPriority()), "pd"),
        RATING(Comparator.comparing(Book::getRating), "r", "ratinga", "ra"),
        RATINGD((book1, book2) -> book2.getRating().compareTo(book1.getRating()), "rd");

        private final Comparator<Book> comparator;
        private final String[] aliases;

        SortMode(Comparator<Book> comparator, String... aliases) {
            this.comparator = comparator;
            this.aliases = aliases;
        }

        /**
         * Returns the {@code SortMode} with a name or alias that matches the specified {@code searchTerm}.
         */
        private static SortMode findSortMode(String searchTerm) {
            for (SortMode sortMode : values()) {
                if (Stream.of(sortMode.aliases).anyMatch(searchTerm::equalsIgnoreCase)
                        || searchTerm.equalsIgnoreCase(sortMode.toString())) {
                    return sortMode;
                }
            }

            return null;
        }

    }
}
