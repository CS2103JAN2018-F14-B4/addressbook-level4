package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

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

        EditCommand.EditDescriptor editDescriptor = new EditCommand.EditDescriptor();

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            Status status = parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
            editDescriptor.setStatus(status);
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            Priority priority = parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            editDescriptor.setPriority(priority);
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            Rating rating = parseRating(argMultimap.getValue(PREFIX_RATING).get());
            editDescriptor.setRating(rating);
        }

        if (!editDescriptor.isValid()) {
            throw new ParseException(EditCommand.MESSAGE_NO_PARAMETERS);
        }

        return new EditCommand(index, editDescriptor);
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
}
