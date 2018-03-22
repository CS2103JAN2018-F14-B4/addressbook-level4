package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's rating.
 * Guarantees: immutable.
 */
public class Rating {

    public final Integer value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A book rating.
     */
    public Rating(Integer rating) {
        requireNonNull(rating);
        this.value = rating;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && this.value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
