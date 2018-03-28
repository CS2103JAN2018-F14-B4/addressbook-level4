package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;

/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";
    private static final String DEFAULT_LABEL_STYLE_CLASS = "label";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Book book;

    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label rating;
    @FXML
    private FlowPane authors;
    @FXML
    private FlowPane categories;
    @FXML
    private Label status;
    @FXML
    private Label priority;
    @FXML
    private Label rating;

    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        this.book = book;
        id.setText(displayedIndex + ". ");
        title.setText(book.getTitle().title);
        rating.setText(String.valueOf(book.getRating().value));
        book.getAuthors().forEach(author -> authors.getChildren()
                .add(new Label(author.fullName)));
        book.getCategories().forEach(category -> categories.getChildren()
                .add(new Label(category.toString())));
        status.setText(book.getStatus().getDisplayText());
        status.getStyleClass().setAll(DEFAULT_LABEL_STYLE_CLASS, book.getStatus().getStyleClass());
        priority.setText(book.getPriority().getDisplayText());
        priority.getStyleClass().setAll(DEFAULT_LABEL_STYLE_CLASS, book.getPriority().getStyleClass());
        rating.setText(book.getRating().getDisplayText());
        rating.getStyleClass().setAll(DEFAULT_LABEL_STYLE_CLASS, book.getRating().getStyleClass());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
                && book.equals(card.book);
    }
}
