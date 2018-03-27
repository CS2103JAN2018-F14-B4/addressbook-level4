package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's Priority.
 * Guarantees: immutable.
 */
public class Priority {

    public final String priority;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A book priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        this.priority = priority;
    }


    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

}
