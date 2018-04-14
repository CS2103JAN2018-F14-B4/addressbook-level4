package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates the BookShelf in the model has changed */
public class FileEncryptEvent extends BaseEvent {


    public FileEncryptEvent() {}

    @Override
    public String toString() {
        return "file encrypt";
    }
}
