package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.commons.util.StringUtil;

//@@author qiu-siqi
/**
 * Exposes required functionalities of {@link WebView}.
 *
 * There can only be one instance of {@code WebViewManager}, allowing the internal limiting
 * of number of WebViews used.
 */
public class WebViewManager {

    private static final Random RAND = new Random();
    private static final double RELOAD_CHANCE = 0.5;

    private static WebViewManager webViewManager;
    private WebView browser;
    private WebEngine engine;
    private List<InvalidationListener> invalidationListeners;
    private List<ChangeListener> changeListeners;
    private List<Pane> parents;

    private WebViewManager() {
        browser = new WebView();
        engine = browser.getEngine();
        invalidationListeners = new ArrayList<>();
        changeListeners = new ArrayList<>();
        parents = new ArrayList<>();
    }

    /**
     * Obtains the instance of {@code WebViewManager}.
     */
    public static WebViewManager getInstance() {
        if (webViewManager == null) {
            webViewManager = new WebViewManager();
        }
        return webViewManager;
    }

    /**
     * Loads {@code WebView} with the given content and shows it in {@code container}.
     *
     * @param container Parent to contain the loaded {@code WebView}.
     * @param toLoad content to load in {@code WebView}.
     */
    protected void load(Pane container, String toLoad) {
        recreateWebView();
        addBrowserToPane(container);
        loadBasedOnContent(toLoad);
    }

    /**
     * Makes a new {@code WebView} with probability specified by RELOAD_CHANCE constant.
     * Prevents {@code WebView} from using up memory indefinitely.
     */
    private void recreateWebView() {
        if (RAND.nextDouble() > RELOAD_CHANCE) {
            return;
        }

        removeBrowserFromPanes();
        browser = new WebView();
        engine = browser.getEngine();
        invalidationListeners.forEach(engine.getLoadWorker().progressProperty()::addListener);
        changeListeners.forEach(engine.getLoadWorker().stateProperty()::addListener);

        // Garbage collect the previous WebView
        System.gc();
    }

    /**
     * Adds {@code WebView} as a child of {@code container}, if it is not already.
     *
     * @param container Parent to contain the {@code WebView}.
     */
    private void addBrowserToPane(Pane container) {
        if (!container.getChildren().contains(browser)) {
            container.getChildren().add(browser);
            parents.add(container);
        }
    }

    /**
     * Removes {@code WebView} as a child of all containers.
     */
    private void removeBrowserFromPanes() {
        parents.forEach(parent -> parent.getChildren().remove(browser));
        parents.clear();
    }

    /**
     * Loads {@code WebView} with content depending on type of {@code toLoad}.
     *
     * @param toLoad content to load in {@code WebView}.
     */
    private void loadBasedOnContent(String toLoad) {
        if (StringUtil.isValidUrl(toLoad)) {
            engine.load(toLoad);
        } else {
            engine.loadContent(toLoad);
        }
    }

    /**
     * Executes a script in the context of the current page in the {@code WebView}.
     *
     * @param scriptPath Path of the script to be called.
     */
    protected void executeScript(String scriptPath) {
        engine.executeScript(scriptPath);
    }

    /**
     * Specifies an action to be performed upon successfully loading any page, given that
     * {@code root} is visible.
     * This method should only be called once for each action, during initialization
     * of a class that will use a {@code WebView}.
     *
     * @param root Node which visibility determines whether to perform this action.
     * @param runnable Action to perform upon successfully loading a page.
     */
    protected void onLoadSuccess(Node root, Runnable runnable) {
        ChangeListener<? super Worker.State> newListener = (obs, oldState, newState) -> {
            if (root.isVisible() && newState == Worker.State.SUCCEEDED) {
                runnable.run();
            }
        };
        engine.getLoadWorker().stateProperty().addListener(newListener);
        changeListeners.add(newListener);
    }

    /**
     * Specifies an action to be performed if the loading of a page exceeds a given percentage, given that
     * {@code root} is visible. This action is performed multiple times until loading is completed.
     * This method should only be called once for each action, during initialization
     * of a class that will use a {@code WebView}.
     *
     * @param root Node which visibility determines whether to perform this action.
     * @param progress Percentage of the page loaded, from 0 to 1.
     * @param runnable Action to perform upon successfully loading a page.
     */
    protected void onLoadProgress(Node root, double progress, Runnable runnable) {
        InvalidationListener newListener = n -> {
            if (root.isVisible() && engine.getLoadWorker().getProgress() > progress) {
                runnable.run();
            }
        };
        engine.getLoadWorker().progressProperty().addListener(newListener);
        invalidationListeners.add(newListener);
    }

    /**
     * Returns the {@code WebView}, for testing purposes. It is not recommended to interact directly with
     * the {@code WebView} contained in this class.
     */
    public WebView getWebView() {
        return browser;
    }

    /**
     * Free up unneeded resources.
     */
    public void cleanUp() {
        webViewManager = null;
        Platform.runLater(() -> {
            invalidationListeners.forEach(engine.getLoadWorker().progressProperty()::removeListener);
            changeListeners.forEach(engine.getLoadWorker().stateProperty()::removeListener);
        });
    }
}
