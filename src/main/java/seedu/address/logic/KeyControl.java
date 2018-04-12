package seedu.address.logic;

/**
 * Stores the key information.
 */
public class KeyControl {

    private static String key;
    private Boolean isEncrypt = false;

    public KeyControl(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public boolean getEncrypt() {
        return isEncrypt;
    }

    public void setKey(String word) {
        key = word;
    }

    public void encrypt() {
        isEncrypt = true;
    }

    public void decrypt() {
        isEncrypt = false;
    }

    public void setIsEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

}
