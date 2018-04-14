package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates the BookShelf in the model has changed */
public class FileDecryptEvent extends BaseEvent {


    public FileDecryptEvent() {}

    @Override
    public String toString() {
        return "file change";
    }
}
