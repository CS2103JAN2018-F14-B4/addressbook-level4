package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/** Indicates the xmlfile has encrypted */
public class XmlFileEncryptEvent extends BaseEvent {

    public final ReadOnlyBookShelf data;

    public XmlFileEncryptEvent(ReadOnlyBookShelf data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "xml file changed";
    }
}
