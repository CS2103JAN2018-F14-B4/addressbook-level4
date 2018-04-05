package seedu.address.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.base.Charsets;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Resources;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowLibraryResultRequestEvent;
import seedu.address.commons.util.StringUtil;

/**
 * The region showing availability of the book in NLB.
 */
public class BookInLibraryPanel extends UiPart<Region> {

    private static final String FXML = "bookInNlbPanel.fxml";
    private static final URL NLB_RESULT_SCRIPT_FILE = MainApp.class.getResource("/view/nlbResultScript.js");
    private static final URL CLEAR_PAGE_SCRIPT_FILE = MainApp.class.getResource("/view/clearPageScript.js");

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final String nlbResultScript;
    private final String clearPageScript;

    @FXML
    private WebView browser;

    public BookInLibraryPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
        getRoot().setVisible(false);

        try {
            nlbResultScript = Resources.toString(NLB_RESULT_SCRIPT_FILE, Charsets.UTF_8);
            clearPageScript = Resources.toString(CLEAR_PAGE_SCRIPT_FILE, Charsets.UTF_8);
        } catch (IOException e) {
            throw new AssertionError("Missing script file: " + e.getMessage());
        }

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        browser.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            // run custom javascript when loading is complete
            logger.info(newState.toString());
            if (newState == Worker.State.SUCCEEDED) {
                browser.getEngine().executeScript(nlbResultScript);
            }
        });
    }

    private void loadPageWithResult(String result) {
        if (StringUtil.isValidUrl(result)) {
            browser.getEngine().load(result);
        } else {
            browser.getEngine().loadContent(result);
        }
    }

    protected void hide() {
        getRoot().setVisible(false);
    }

    @Subscribe
    private void handleShowBookInLibraryRequestEvent(ShowLibraryResultRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            browser.getEngine().executeScript(clearPageScript);
            loadPageWithResult(event.getResult());
            getRoot().setVisible(true);
        });
    }
}
