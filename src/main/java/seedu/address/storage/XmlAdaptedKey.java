package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;
import java.security.Key;

/**
 * JAXB-friendly adapted version of the Key.
 */
public class XmlAdaptedKey {

    @XmlValue
    private String key;

    /**
     * Constructs an XmlAdaptedKey.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedKey() {}

    /**
     * Constructs a {@code XmlAdaptedKey} with the given {@code key}.
     */
    public XmlAdaptedKey(String key1) {
        key = key1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedKey)) {
            return false;
        }

        return key.equals(((XmlAdaptedKey) other).key);
    }


}
