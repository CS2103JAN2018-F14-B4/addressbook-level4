package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to reselect a book, if it was previously deselected using {@link DeselectBookRequestEvent}.
 */
public class ReselectBookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
