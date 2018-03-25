package seedu.address.model.book;

/**
 * Represents a book's status (whether it is read, being read, or unread).
 */
public enum Status {
    READ("\uD83D\uDCD6 Read", "status-read"),
    UNREAD("\uD83D\uDCD7 Unread", "status-unread"),
    READING("\uD83D\uDCD6 Reading", "status-reading");

    public static final Status DEFAULT_STATUS = UNREAD;

    private final String displayText;
    private final String styleClass;

    Status(String displayText, String styleClass) {
        this.displayText = displayText;
        this.styleClass = styleClass;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
