package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowBookInLibraryRequestEvent;
import seedu.address.model.book.Book;

/**
 * The region showing availability of the book in NLB.
 */
public class BookInLibraryPanel extends UiPart<Region> {
    protected static final String SEARCH_PAGE_URL =
            "https://catalogue.nlb.gov.sg/cgi-bin/spydus.exe/MSGTRN/EXPNOS/COMB?HOMEPRMS=COMBPARAMS";

    private static final String FXML = "bookInNlbPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BookInLibraryPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
        getRoot().setVisible(false);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
    }

    protected void loadPageForBook(Book book) {
        loadPage(SEARCH_PAGE_URL);
    }

    private void loadPage(String url) {
        browser.getEngine().load(url);
    }

    protected void hide() {
        getRoot().setVisible(false);
    }

    @Subscribe
    private void handleShowBookInLibraryRequestEvent(ShowBookInLibraryRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            loadPageForBook(event.getBook());
            getRoot().setVisible(true);
        });
    }
}
