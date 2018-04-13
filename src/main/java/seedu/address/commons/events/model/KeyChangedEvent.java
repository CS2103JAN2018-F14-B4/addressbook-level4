package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/** Indicates the KEY in the model has changed */
public class KeyChangedEvent extends BaseEvent {

    public final String key;

    public KeyChangedEvent(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Set key processing";
    }
}
