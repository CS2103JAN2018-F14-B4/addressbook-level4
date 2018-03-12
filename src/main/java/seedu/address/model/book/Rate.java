package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's rate.
 * Guarantees: immutable.
 */
public class Rate {

    public final String rate;

    /**
     * Constructs a {@code Description}.
     *
     * @param rate A book rate.
     */
    public Rate(String rate) {
        requireNonNull(rate);
        this.rate = rate;
    }


    @Override
    public String toString() {
        return rate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.rate.equals(((Rate) other).rate)); // state check
    }

    @Override
    public int hashCode() {
        return rate.hashCode();
    }

}
