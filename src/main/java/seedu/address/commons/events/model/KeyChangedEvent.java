package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/** Indicates the KEY in the model has changed */
public class KeyChangedEvent extends BaseEvent {

    public final ReadOnlyBookShelf bs;

    public KeyChangedEvent(ReadOnlyBookShelf bookShelf) {
        bs = bookShelf;
    }

    @Override
    public String toString() {
        return "Set key processing";
    }
}
