package seedu.address.logic;
//@@author 592363789

/**
 * Stores the key information.
 */
public class KeyControl {

    private static KeyControl keyControl;

    private static String key;
    private static boolean isEncrypted;

    private KeyControl() {
        key = "";
        isEncrypted = false;
    }

    public static KeyControl getInstance() {
        if (keyControl == null) {
            keyControl = new KeyControl();
        }
        return keyControl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String word) {
        key = word;
    }

    public void encrypt() {
        isEncrypted = true;
    }

    public void decrypt() {
        isEncrypted = false;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

}
