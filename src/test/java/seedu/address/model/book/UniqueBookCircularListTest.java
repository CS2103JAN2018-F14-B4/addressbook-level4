package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.TypicalBooks;

public class UniqueBookCircularListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addBook_null_failure() throws Exception {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(5);
        thrown.expect(NullPointerException.class);
        uniqueBookCircularList.add(null);
    }

    @Test
    public void addBook_validBook_success() throws Exception {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(5);
        uniqueBookCircularList.add(TypicalBooks.ARTEMIS);
        assertEquals(true, uniqueBookCircularList.asObservableList().contains(TypicalBooks.ARTEMIS));
    }

    @Test
    public void addBook_repeatedBook_success() throws Exception {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(5);
        uniqueBookCircularList.add(TypicalBooks.ARTEMIS);
        uniqueBookCircularList.add(TypicalBooks.ARTEMIS);

        ObservableList<Book> list = uniqueBookCircularList.asObservableList();
        assertEquals(true, list.contains(TypicalBooks.ARTEMIS));
        assertEquals(1, list.size());
    }

    @Test
    public void addBook_tooManyBooks_success() throws Exception {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(2);
        uniqueBookCircularList.add(TypicalBooks.ARTEMIS);
        uniqueBookCircularList.add(TypicalBooks.BABYLON_ASHES);
        uniqueBookCircularList.add(TypicalBooks.COLLAPSING_EMPIRE);

        ObservableList<Book> list = uniqueBookCircularList.asObservableList();
        assertEquals(2, list.size()); // max size 2
        assertEquals(false, list.contains(TypicalBooks.ARTEMIS)); // replaced
        assertEquals(true, list.contains(TypicalBooks.BABYLON_ASHES));
        assertEquals(true, list.contains(TypicalBooks.COLLAPSING_EMPIRE));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(5);
        thrown.expect(UnsupportedOperationException.class);
        uniqueBookCircularList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameContent_returnsTrue() throws Exception {
        UniqueBookCircularList uniqueBookCircularList = new UniqueBookCircularList(5);
        uniqueBookCircularList.add(TypicalBooks.ARTEMIS);
        uniqueBookCircularList.add(TypicalBooks.BABYLON_ASHES);
        UniqueBookCircularList uniqueBookCircularList2 = new UniqueBookCircularList(5);
        uniqueBookCircularList2.add(TypicalBooks.ARTEMIS);
        uniqueBookCircularList2.add(TypicalBooks.BABYLON_ASHES);
        assertEquals(true, uniqueBookCircularList.equals(uniqueBookCircularList2));
    }

}
