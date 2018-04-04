package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchResultsSelectionChangedEvent;
import seedu.address.commons.events.ui.SwitchToBookListRequestEvent;
import seedu.address.commons.events.ui.SwitchToRecentBooksRequestEvent;
import seedu.address.commons.events.ui.SwitchToSearchResultsRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BookShelfParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ActiveListType;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Network network;
    private final CommandHistory history;
    private final BookShelfParser bookShelfParser;
    private final UndoStack undoStack;
    private static boolean isLock = false;
    private static String key;

    public LogicManager(Model model, Network network) {
        this.model = model;
        this.network = network;
        history = new CommandHistory();
        bookShelfParser = new BookShelfParser();
        undoStack = new UndoStack();
        isLock = true;
        key = model.getPassword();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = bookShelfParser.parseCommand(commandText);
            command.setData(model, network, history, undoStack);
            CommandResult result;
            if (isLock == true) {
                if (command instanceof DecryptCommand) {
                    DecryptCommand decryptCommand = (DecryptCommand) command;
                    result = decryptCommand.execute();
                } else {
                    result = new CommandResult("Bibliotek is locked," +
                            " please unlock it first!");
                }
            } else {
                result = command.execute();
                undoStack.push(command);
            }
            return result;
        } finally {
            history.add(commandText);
        }
    }

    public static String getKey() {
        return key;
    }

    public static boolean getLock() {
        return isLock;
    }

    public static void setKey(String word) {
        key = word;
    }

    public static void lock() {
        isLock = true;
    }

    public static void unLock() {
        isLock = false;
    }

    @Override
    public ObservableList<Book> getDisplayBookList() {
        return model.getDisplayBookList();
    }

    @Override
    public ObservableList<Book> getSearchResultsList() {
        return model.getSearchResultsList();
    }

    @Override
    public ObservableList<Book> getRecentBooksList() {
        return model.getRecentBooksList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Subscribe
    private void handleShowBookListRequestEvent(SwitchToBookListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.setActiveListType(ActiveListType.BOOK_SHELF);
    }

    @Subscribe
    private void handleShowSearchResultsRequestEvent(SwitchToSearchResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.setActiveListType(ActiveListType.SEARCH_RESULTS);
    }

    @Subscribe
    private void handleSwitchToRecentBooksRequestEvent(SwitchToRecentBooksRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.setActiveListType(ActiveListType.RECENT_BOOKS);
    }

    @Subscribe
    private void handleSearchResultsSelectionChangedEvent(SearchResultsSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.addRecentBook(event.getNewSelection());
    }

    @Subscribe
    private void handleBookListSelectionChangedEvent(BookListSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.addRecentBook(event.getNewSelection());
    }
}
