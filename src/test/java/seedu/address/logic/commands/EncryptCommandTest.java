package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

public class EncryptCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    }

    @Test
    public void equals() {

        EncryptCommand encryptCommand = new EncryptCommand();

        EncryptCommand thesameCommand = new EncryptCommand();

        // same value -> returns true
        assertTrue(encryptCommand.equals(thesameCommand));

        // same object -> returns true
        assertTrue(encryptCommand.equals(encryptCommand));

        // null -> returns false
        assertFalse(encryptCommand.equals(null));

        // different commandtypes -> returns false
        assertFalse(encryptCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(encryptCommand.equals(0));

    }

    @Test
    public void execute_exit_success() {
        EncryptCommand encryptCommand = new EncryptCommand();
        CommandResult result = encryptCommand.execute();
        String successMessage = EncryptCommand.MESSAGE_SUCCESS;

        assertEquals(successMessage, result.feedbackToUser);
    }
}
