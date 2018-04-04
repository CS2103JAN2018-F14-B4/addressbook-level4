package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

public class KeyChangedEvent extends BaseEvent {

    public final ReadOnlyBookShelf data;

    public final String key;

    public KeyChangedEvent(String key, ReadOnlyBookShelf data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of books " + data.size();
    }
}