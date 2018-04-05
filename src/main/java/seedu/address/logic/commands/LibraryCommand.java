package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowBookInLibraryRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;

public class LibraryCommand extends Command {

    public static final String COMMAND_WORD = "library";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the availability of the book identified by the index number in the library.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Showing availability of book: %1$s.";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Cannot view availability of books "
            + "in the current list.";

    private final Index targetIndex;

    public LibraryCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }

        List<Book> lastShownList = model.getDisplayBookList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        Book book = model.getDisplayBookList().get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new ShowBookInLibraryRequestEvent(book));
        return new CommandResult(String.format(MESSAGE_SUCCESS, book));
    }
}
