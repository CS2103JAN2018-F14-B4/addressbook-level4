package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;
import static seedu.address.testutil.TypicalBooks.BABYLON_ASHES;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookDetailsPanelHandle;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.ui.testutil.GuiTestAssert;

public class BookDetailsPanelTest extends GuiUnitTest {

    private BookDetailsPanelHandle bookDetailsPanelHandle;

    @Before
    public void setUp() {
        BookDetailsPanel bookDetailsPanel = new BookDetailsPanel();
        uiPartRule.setUiPart(bookDetailsPanel);
        bookDetailsPanelHandle = new BookDetailsPanelHandle(bookDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        // hidden by default
        assertFalse(bookDetailsPanelHandle.isVisible());

        // check that the correct details are displayed
        postNow(new BookListSelectionChangedEvent(ARTEMIS));
        assertDetailsPanelDisplaysBook(ARTEMIS);

        // check that the correct details are displayed
        postNow(new BookListSelectionChangedEvent(BABYLON_ASHES));
        assertDetailsPanelDisplaysBook(BABYLON_ASHES);

        // no categories
        Book bookWithNoCategories = new BookBuilder().withCategories().build();
        postNow(new BookListSelectionChangedEvent(bookWithNoCategories));
        assertDetailsPanelDisplaysBook(bookWithNoCategories);

        // no authors
        Book bookWithNoAuthors = new BookBuilder().withAuthors().build();
        postNow(new BookListSelectionChangedEvent(bookWithNoAuthors));
        assertDetailsPanelDisplaysBook(bookWithNoAuthors);

        // empty book
        Book emptyBook = new BookBuilder().withGid("").withTitle("").withAuthors().withCategories()
                .withDescription("").withIsbn("").withPublicationDate("").withPublisher("").build();
        postNow(new BookListSelectionChangedEvent(emptyBook));
        assertDetailsPanelDisplaysBook(emptyBook);
    }

    /**
     * Asserts that the {@code BookDetailsPanel} displays the details of {@code expectedBook} correctly
     * and is visible.
     */
    private void assertDetailsPanelDisplaysBook(Book expectedBook) {
        guiRobot.pauseForHuman();
        assertTrue(bookDetailsPanelHandle.isVisible());
        GuiTestAssert.assertDetailsPanelDisplaysBook(expectedBook, bookDetailsPanelHandle);
    }
}
