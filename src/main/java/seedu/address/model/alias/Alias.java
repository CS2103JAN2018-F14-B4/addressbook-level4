package seedu.address.model.alias;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Contains data about a single alias.
 * Guarantees: immutable.
 */
public class Alias {
    private final String name;
    private final String prefix;
    private final String arguments;

    /**
     * Creates an {@code Alias} with the specified {@code name}, {@code prefix}, and {@code arguments}.
     * Every field must be present and non-null.
     */
    public Alias(String name, String prefix, String arguments) {
        requireAllNonNull(name, prefix, arguments);
        this.name = name.trim().toLowerCase();
        this.prefix = prefix.trim();
        this.arguments = arguments.trim();
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return name + " - " + prefix + " " + arguments;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias // instanceof handles nulls
                && this.name.equals(((Alias) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
