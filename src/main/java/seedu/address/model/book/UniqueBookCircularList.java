package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a unique collection of books, with maximum of a pre-set number of books.
 * When the limit is reached, the earliest added book is removed to add the new book.
 * Does not allow nulls.
 *
 * Supports a minimal set of operations.
 */
public class UniqueBookCircularList {

    private final ObservableList<Book> internalList = FXCollections.observableArrayList();
    private final int size;

    /**
     * Constructs a list where the maximum number of books in the list is {@code size}.
     */
    public UniqueBookCircularList(int size) {
        this.size = size;
    }

    /**
     * Adds a book to the list if the book is not already in the list.
     * Removes the earliest added book if the list is full before adding the new book.
     */
    public void add(Book toAdd) {
        requireNonNull(toAdd);
        if (internalList.contains(toAdd)) {
            return;
        }
        if (internalList.size() >= size) {
            internalList.remove(0);
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookCircularList // instanceof handles nulls
                && this.internalList.equals(((UniqueBookCircularList) other).internalList));
    }

}
