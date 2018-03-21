package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.commons.events.ui.RecentBooksSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchResultsSelectionChangedEvent;
import seedu.address.model.book.Book;

/**
 * The Book Details Panel of the App.
 */
public class BookDetailsPanel extends UiPart<Region> {

    private static final String FXML = "BookDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label title;
    @FXML
    private Label isbn;
    @FXML
    private FlowPane authors;
    @FXML
    private FlowPane categories;
    @FXML
    private Label publisher;
    @FXML
    private Label publicationDate;
    @FXML
    private Label description;

    public BookDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        getRoot().setVisible(false);
    }

    /** Update this panel to show details about the specified book. */
    public void showBook(Book book) {
        Platform.runLater(() -> {
            title.setText(book.getTitle().toString());
            isbn.setText(book.getIsbn().toString());
            publisher.setText(book.getPublisher().toString());
            publicationDate.setText(book.getPublicationDate().toString());
            description.setText(book.getDescription().toString());

            authors.getChildren().clear();
            categories.getChildren().clear();
            book.getAuthors().forEach(author -> authors.getChildren()
                    .add(new Label(author.fullName)));
            book.getCategories().forEach(category -> categories.getChildren()
                    .add(new Label(category.toString())));

            getRoot().setVisible(true);
        });
    }

    public void clear() {
        getRoot().setVisible(false);
    }

    @Subscribe
    private void handleSearchResultsSelectionChangedEvent(SearchResultsSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showBook(event.getNewSelection().book);
    }

    @Subscribe
    private void handleBookListSelectionChangedEvent(BookListSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showBook(event.getNewSelection().book);
    }

    @Subscribe
    private void handleRecentBooksSelectionChangedEvent(RecentBooksSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showBook(event.getNewSelection().book);
    }
}
