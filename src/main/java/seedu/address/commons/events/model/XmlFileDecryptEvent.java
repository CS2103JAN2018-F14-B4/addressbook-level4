package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/** Indicates the xmlfile has decrypted */
public class XmlFileDecryptEvent extends BaseEvent {

    public final ReadOnlyBookShelf data;

    public XmlFileDecryptEvent(ReadOnlyBookShelf data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "xml file changed";
    }
}
