package seedu.address.ui;

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

    private static WebViewManager webViewManager;
    private static WebView browser;
    private static WebEngine engine;

    private WebViewManager() {
        browser = new WebView();
        engine = browser.getEngine();
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
        addBrowserToPane(container);
        loadBasedOnContent(toLoad);
    }

    /**
     * Adds {@code WebView} as a child of {@code container}, if it is not already.
     *
     * @param container Parent to contain the {@code WebView}.
     */
    private void addBrowserToPane(Pane container) {
        if (!container.getChildren().contains(browser)) {
            container.getChildren().add(browser);
        }
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
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (root.isVisible() && newState == Worker.State.SUCCEEDED) {
                runnable.run();
            }
        });
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
        engine.getLoadWorker().progressProperty().addListener(n -> {
            System.out.println(engine.getLoadWorker().getProgress());
            if (root.isVisible() && engine.getLoadWorker().getProgress() > progress) {
                runnable.run();
            }
        });
    }

    /**
     * Returns the {@code WebView}, for testing purposes. It is not recommended to interact directly with
     * the {@code WebView} contained in this class.
     */
    public WebView getWebView() {
        return browser;
    }
}
