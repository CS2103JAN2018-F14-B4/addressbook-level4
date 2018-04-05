package seedu.address.network.library;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import seedu.address.model.book.Book;
import seedu.address.network.HttpClient;
import seedu.address.network.HttpResponse;

/**
 * Provides access to the NLB catalogue.
 */
public class NlbCatalogueApi {

    protected static final String URL_SEARCH_BOOKS =
            "https://catalogue.nlb.gov.sg/cgi-bin/spydus.exe/ENQ/EXPNOS/BIBENQ";
    private static final String CONTENT_TYPE_HTML = "text/html";
    private static final int HTTP_STATUS_OK = 200;

    private final HttpClient httpClient;

    public NlbCatalogueApi(HttpClient httpClient) {
        requireNonNull(httpClient);
        this.httpClient = httpClient;
    }

    /**
     * Searches for a book in NLB catalogue.
     *
     * @param book book to search for.
     * @return CompletableFuture that resolves to a String containing the search results as HTML content.
     */
    public CompletableFuture<String> searchForBook(Book book) {
        return execute(URL_SEARCH_BOOKS, makeParamsMap(book));
    }

    /**
     * Obtain all parameters as key value pairs, which is used to search for the specific book.
     *
     * @param book book to search for.
     * @return Map<String, String> with all the key value pairs.
     */
    private Map<String, String> makeParamsMap(Book book) {
        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put("ENTRY1_NAME", "TI");
        paramsMap.put("ENTRY1", book.getTitle().toString());
        paramsMap.put("ENTRY2_NAME", "AU");
        paramsMap.put("ENTRY2", book.getAuthorsAsString());
        paramsMap.put("PD", makePublicationYearParam(book.getPublicationDate().getYear()));

        return paramsMap;
    }

    /**
     * Asynchronously executes a HTTP POST request to the specified url with the specified parameters.
     *
     * @param url the url used for the POST request.
     * @param params the query parameters for the POST request.
     * @return CompleteableFuture that resolves to a String containing the search results as HTML content.
     */
    private CompletableFuture<String> execute(String url, Map<String, String> params) {
        return httpClient
                .makePostRequest(url, params)
                .thenApply(NlbCatalogueApi::requireHtmlContentType)
                .thenApply(NlbCatalogueApi::requireHttpStatusOk)
                .thenApply(HttpResponse::getResponseBody);
    }

    /**
     * Gives a range of years to search for given a particular year.
     */
    private static String makePublicationYearParam(int year) {
        return year == -1 ? "1500-2500" : (year - 2) + "-" + (year + 2);
    }

    /**
     * Throws a {@link CompletionException} if the content type of the response is not HTML.
     */
    private static HttpResponse requireHtmlContentType(HttpResponse response) {
        if (!response.getContentType().startsWith(CONTENT_TYPE_HTML)) {
            throw new CompletionException(
                    new IOException("Unexpected content type " + response.getContentType()));
        }
        return response;
    }

    /**
     * Throws a {@link CompletionException} if the HTTP status code of the response is not {@code 200: OK}.
     */
    private static HttpResponse requireHttpStatusOk(HttpResponse response) {
        if (response.getStatusCode() != HTTP_STATUS_OK) {
            throw new CompletionException(
                    new IOException("Get request failed with status code " + response.getStatusCode()));
        }
        return response;
    }
}
