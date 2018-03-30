package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new {@code EditCommand} object.
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditCommand}
     * and returns a {@code EditCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATING, PREFIX_PRIORITY,
                PREFIX_STATUS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        int rating = Integer.valueOf(argMultimap.getValue(PREFIX_RATING).orElse("-1"));
        String priority = argMultimap.getValue(PREFIX_PRIORITY).orElse("n");
        String status = argMultimap.getValue(PREFIX_STATUS).orElse("u");

        Comparator<Book> comparator = Model.DEFAULT_BOOK_COMPARATOR;
        if (argMultimap.getValue(PREFIX_SORT_BY).isPresent()) {
            SortMode sortMode = SortMode.findSortMode(argMultimap.getValue(PREFIX_SORT_BY).get());
            if (sortMode == null) {
                throw new ParseException(EditCommand.MESSAGE_INVALID_SORT_BY);
            }
            comparator = sortMode.comparator;
        }
        return new EditCommand(index, new Rating(rating), Priority.findPriority(priority),
                Status.findStatus(status));
    }


    /**
     * Parses the given {@code statusString} and returns a {@code Status}.
     *
     * @throws ParseException if the string does not represent a valid status.
     */
    protected static Status parseStatus(String statusString) throws ParseException {
        Status status = Status.findStatus(statusString);
        if (status == null) {
            throw new ParseException(EditCommand.MESSAGE_INVALID_STATUS);
        }
        return status;
    }

    /**
     * Parses the given {@code priorityString} and returns a {@code Priority}.
     *
     * @throws ParseException if the string does not represent a valid priority.
     */
    protected static Priority parsePriority(String priorityString) throws ParseException {
        Priority priority = Priority.findPriority(priorityString);
        if (priority == null) {
            throw new ParseException(EditCommand.MESSAGE_INVALID_PRIORITY);
        }
        return priority;
    }

    /**
     * Parses the given {@code ratingString} and returns a {@code Rating}.
     *
     * @throws ParseException if the string does not represent a valid rating.
     */
    protected static Rating parseRating(String ratingString) throws ParseException {
        try {
            return new Rating(Integer.parseInt(ratingString));
        } catch (IllegalArgumentException e) {
            throw new ParseException(EditCommand.MESSAGE_INVALID_RATING);
        }
    }

    /**
     * Represents a sorting mode, which specifies the comparator used to sort the display book list.
     */
    public enum SortMode {
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

        public Comparator<Book> getComparator() {
            return comparator;
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
