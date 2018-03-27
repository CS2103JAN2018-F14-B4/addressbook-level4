package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's Status.
 * Guarantees: immutable.
 */
public class Status {

    public final String status;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A book status.
     */
    public Status(String status) {
        requireNonNull(status);
        this.status = status;
    }


    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

}
