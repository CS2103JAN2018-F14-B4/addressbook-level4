package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BookShelfStorage bookShelfStorage;
    private UserPrefsStorage userPrefsStorage;
    private RecentBooksStorage recentBooksStorage;

    public StorageManager(BookShelfStorage bookShelfStorage, UserPrefsStorage userPrefsStorage,
                          RecentBooksStorage recentBooksStorage) {
        super();
        this.bookShelfStorage = bookShelfStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.recentBooksStorage = recentBooksStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ BookShelf methods ===============================

    @Override
    public String getBookShelfFilePath() {
        return bookShelfStorage.getBookShelfFilePath();
    }

    @Override
    public Optional<ReadOnlyBookShelf> readBookShelf() throws DataConversionException, IOException {
        return readBookShelf(bookShelfStorage.getBookShelfFilePath());
    }

    @Override
    public Optional<ReadOnlyBookShelf> readBookShelf(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookShelfStorage.readBookShelf(filePath);
    }

    @Override
    public void saveBookShelf(ReadOnlyBookShelf bookShelf) throws IOException {
        saveBookShelf(bookShelf, bookShelfStorage.getBookShelfFilePath());
    }

    @Override
    public void saveBookShelf(ReadOnlyBookShelf bookShelf, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookShelfStorage.saveBookShelf(bookShelf, filePath);
    }

    @Override
    @Subscribe
    public void handleBookShelfChangedEvent(BookShelfChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveBookShelf(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ RecentBooks methods ===============================

    @Override
    public String getRecentBooksFilePath() {
        return recentBooksStorage.getRecentBooksFilePath();
    }

    @Override
    public Optional<ReadOnlyBookShelf> readRecentBooksList() throws DataConversionException, IOException {
        return recentBooksStorage.readRecentBooksList();
    }

    @Override
    public void saveRecentBooksList(ReadOnlyBookShelf recentBooksList) throws IOException {
        recentBooksStorage.saveRecentBooksList(recentBooksList);
    }
}
