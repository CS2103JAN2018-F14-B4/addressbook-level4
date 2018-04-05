package seedu.address.network;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Param;

import seedu.address.commons.core.LogsCenter;

//@@author takuyakanbr
/**
 * A wrapper around the {@link AsyncHttpClient} class from async-http-client.
 */
public class HttpClient {

    private static final Logger logger = LogsCenter.getLogger(HttpClient.class);
    private static final int CONNECTION_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds
    private static final int READ_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds
    private static final int REQUEST_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds

    private final AsyncHttpClient asyncHttpClient;

    protected HttpClient() {
        this.asyncHttpClient = Dsl.asyncHttpClient(Dsl.config()
                .setConnectTimeout(CONNECTION_TIMEOUT_MILLIS)
                .setReadTimeout(READ_TIMEOUT_MILLIS)
                .setRequestTimeout(REQUEST_TIMEOUT_MILLIS));
    }

    protected HttpClient(AsyncHttpClient asyncHttpClient) {
        requireNonNull(asyncHttpClient);
        this.asyncHttpClient = asyncHttpClient;
    }

    /**
     * Asynchronously executes a HTTP GET request to the specified url.
     */
    public CompletableFuture<HttpResponse> makeGetRequest(String url) {
        return asyncHttpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(HttpResponse::new);
    }

    /**
     * Asynchronously executes a HTTP POST request to the specified url with {@code params} as query parameters.
     */
    public CompletableFuture<HttpResponse> makePostRequest(String url, Map<String, String> params) {
        return asyncHttpClient
                .preparePost(url)
                .setFormParams(makeParamList(params))
                .execute()
                .toCompletableFuture()
                .thenApply(HttpResponse::new);
    }

    private List<Param> makeParamList(Map<String, String> paramMap) {
        return paramMap.keySet().stream().map(key -> new Param(key, paramMap.get(key))).collect(Collectors.toList());
    }

    /**
     * Stops and closes the underlying {@link AsyncHttpClient}.
     */
    public void close() {
        try {
            if (!asyncHttpClient.isClosed()) {
                asyncHttpClient.close();
            }
        } catch (IOException e) {
            logger.warning("Failed to shut down AsyncHttpClient.");
        }
    }
}
