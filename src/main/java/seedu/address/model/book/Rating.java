package seedu.address.model.book;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a book's rating.
 * Guarantees: immutable.
 */
public class Rating {

    public static final String MESSAGE_RATING_CONSTRAINTS = "Rating must be between -1 and 5 (both inclusive).";
    public static final int DEFAULT_RATING = -1;
    public static final int MIN_RATING = -1;
    public static final int MAX_RATING = 5;

    public final int rating;

    /**
     * Contructs a {@code Rating} with the default rating.
     */
    public Rating() {
        this.rating = DEFAULT_RATING;
    }

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A book rating.
     */
    public Rating(int rating) {
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        this.rating = rating;
    }

    /**
     * Returns true if the given integer is a valid rating.
     */
    public static boolean isValidRating(int rating) {
        return rating >= MIN_RATING && rating <= MAX_RATING;
    }

    @Override
    public String toString() {
        return Integer.toString(rating);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && this.rating == ((Rating) other).rating); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(rating);
    }

}
