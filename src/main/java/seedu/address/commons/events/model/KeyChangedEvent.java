package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

public class KeyChangedEvent extends BaseEvent {

    public final String key;

    public KeyChangedEvent(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "key " + key;
    }
}