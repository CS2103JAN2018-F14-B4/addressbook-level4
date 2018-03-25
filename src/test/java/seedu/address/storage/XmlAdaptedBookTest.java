package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Description;
import seedu.address.model.book.Gid;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Priority;
import seedu.address.model.book.PublicationDate;
import seedu.address.model.book.Publisher;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;
import seedu.address.model.book.Title;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {

    private static final String VALID_TITLE = ARTEMIS.getTitle().toString();
    private static final String VALID_DESCRIPTION = ARTEMIS.getDescription().toString();
    private static final List<XmlAdaptedAuthor> VALID_AUTHORS = ARTEMIS.getAuthors().stream()
            .map(XmlAdaptedAuthor::new).collect(Collectors.toList());
    private static final List<XmlAdaptedCategory> VALID_CATEGORIES = ARTEMIS.getCategories().stream()
            .map(XmlAdaptedCategory::new).collect(Collectors.toList());
    private static final Status VALID_STATUS = Status.DEFAULT_STATUS;
    private static final Priority VALID_PRIORITY = Priority.DEFAULT_PRIORITY;
    private static final int VALID_RATING = Rating.DEFAULT_RATING;
    private static final String VALID_GID = ARTEMIS.getGid().toString();
    private static final String VALID_ISBN = ARTEMIS.getIsbn().toString();
    private static final String VALID_PUBLISHER = ARTEMIS.getPublisher().toString();
    private static final String VALID_PUBLICATION_DATE = ARTEMIS.getPublicationDate().toString();

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        XmlAdaptedBook book = new XmlAdaptedBook(ARTEMIS);
        assertEquals(ARTEMIS, book.toModelType());
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, null, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, null, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, null, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, null, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, null, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullGid_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(null, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gid.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullIsbn_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, null, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullPublisher_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, null, VALID_PUBLICATION_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Publisher.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullPublicationDate_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE, VALID_DESCRIPTION, VALID_AUTHORS,
                VALID_CATEGORIES, VALID_STATUS, VALID_PRIORITY, VALID_RATING, VALID_PUBLISHER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PublicationDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

}
