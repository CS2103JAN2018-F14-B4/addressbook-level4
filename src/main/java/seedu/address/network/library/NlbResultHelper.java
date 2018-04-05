package seedu.address.network.library;

/**
 * Provides utilities to manage result from NLB catalogue search.
 */
public class NlbResultHelper {

    private static String FULL_DISPLAY_STRING = "<span>Full Display</span>";
    private static String URL_PREFIX = "https://catalogue.nlb.gov.sg";
    private static String SEARCH_STRING = "/cgi-bin/spydus.exe/FULL/EXPNOS/BIBENQ/";
    private static String NO_RESULTS_FOUND = "No results found.";

    /**
     * Obtains the URL of the top search result, if {@code result} contains a list of search results,
     * else returns {@code result}.
     * Returns a custom String if the list is empty.
     */
    protected static String getTopResultUrlIfIsList(String result) {
        if (isFullDisplay(result)) {
            return result;
        }
        return getTopResultUrl(result);
    }

    /**
     * Checks whether {@code result} is the full display result page of a single book.
     */
    protected static boolean isFullDisplay(String result) {
        return result.contains(FULL_DISPLAY_STRING);
    }

    /**
     * Assumes: {@code result} is not the full display result page.
     * Obtains the URL of the top search result, if any.
     */
    private static String getTopResultUrl(String result) {
        assert !isFullDisplay(result);

        int index = result.indexOf(SEARCH_STRING);
        if (index == -1) {
            return NO_RESULTS_FOUND;
        }
        return getUrlFromIndex(result, index);
    }

    /**
     * Assumes: {@code index} is valid.
     * Obtains the URL which is given starting at {@code index} in {@code result}.
     */
    private static String getUrlFromIndex(String result, int index) {
        int len = result.length();

        assert index >= 0;
        assert index < len;

        StringBuilder builder = new StringBuilder();
        builder.append(URL_PREFIX);

        for (int i = index; ; i++) {
            if (i >= len || result.charAt(i) == '\"') {
                break;
            }
            builder.append(result.charAt(i));
        }

        return builder.toString();
    }
}
