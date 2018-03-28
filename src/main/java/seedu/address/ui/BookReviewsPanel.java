package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowBookReviewsRequestEvent;
import seedu.address.logic.commands.ReviewsCommand;
import seedu.address.model.book.Book;

/**
 * The panel showing book reviews.
 */
public class BookReviewsPanel extends UiPart<Region> {
    public static final String SEARCH_PAGE_URL =
            "https://www.goodreads.com/search?q=%isbn#other_reviews";

    private static final String FXML = "BookReviewsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BookReviewsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        getRoot().setVisible(false);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Show the web page when loading is done
        WebEngine engine = browser.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                raise(new NewResultAvailableEvent(ReviewsCommand.MESSAGE_SUCCESS));
                getRoot().setVisible(true);
            } else if (newState == Worker.State.FAILED) {
                raise(new NewResultAvailableEvent(ReviewsCommand.MESSAGE_FAIL));
            }
        });
    }

    private void loadPageForBook(Book book) {
        loadPage(SEARCH_PAGE_URL.replace("%isbn", book.getIsbn().isbn));
    }

    private void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    protected void clear() {
        getRoot().setVisible(false);
    }

    @Subscribe
    private void handleShowBookReviewsRequestEvent(ShowBookReviewsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPageForBook(event.getBook());
    }
}
