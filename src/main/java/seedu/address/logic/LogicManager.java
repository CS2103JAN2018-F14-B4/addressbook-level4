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
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BookShelfParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ActiveListType;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;
import seedu.address.model.book.Book;
import seedu.address.network.Network;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private static String key;
    private static boolean isEncrypt = false;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Network network;
    private final CommandHistory history;
    private final BookShelfParser bookShelfParser;
    private final UndoStack undoStack;

    public LogicManager(Model model, Network network) {
        this.model = model;
        this.network = network;
        history = new CommandHistory();
        bookShelfParser = new BookShelfParser(model.getAliasList());
        undoStack = new UndoStack();
        key = model.getKey();
        if (key == null) {
            isEncrypt = false;
        } else {
            isEncrypt = true;
        }
    }
    //@@author 592363789
    /**
     * Constructor for test use.
     */
    public LogicManager(Model model, Network network, boolean isEncrypt) {
        this.model = model;
        this.network = network;
        history = new CommandHistory();
        bookShelfParser = new BookShelfParser(model.getAliasList());
        undoStack = new UndoStack();
        key = model.getKey();
        this.isEncrypt = isEncrypt;
    }
    //@@author
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        String processedText = bookShelfParser.applyCommandAlias(commandText);
        logger.info("----------------[USER COMMAND][" + processedText + "]");

        try {
            Command command = bookShelfParser.parseCommand(commandText);
            CommandResult result;
            if (isEncrypt == true && !(command instanceof HelpCommand)) {
                if (command instanceof DecryptCommand) {
                    DecryptCommand decryptCommand = (DecryptCommand) command;
                    result = decryptCommand.execute();
                } else {
                    result = new CommandResult("Bibliotek is encrypted,"
                            + " please decrypt it first!");
                }
            } else {
                command.setData(model, network, history, undoStack);
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

    public static boolean getEncrypt() {
        return isEncrypt;
    }

    public static void setKey(String word) {
        key = word;
    }

    public static void encrypt() {
        isEncrypt = true;
    }

    public static void decrypt() {
        isEncrypt = false;
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
    public ObservableList<Alias> getDisplayAliasList() {
        return model.getDisplayAliasList();
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
