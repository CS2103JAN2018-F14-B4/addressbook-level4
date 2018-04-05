package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.book.Book;

/**
 * An event requesting to view the availability of a book in the library.
 */
public class ShowBookInLibraryRequestEvent extends BaseEvent {

    private final Book book;

    public ShowBookInLibraryRequestEvent(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Book getBook() {
        return book;
    }
}
