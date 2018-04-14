package seedu.address.logic.commands;
//@@author 592363789
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Before;
import org.junit.Test;

import seedu.address.TestApp;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.KeyControl;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.network.Network;

public class EncryptCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    }

    @Test
    public void equals() {

        EncryptCommand encryptCommand = new EncryptCommand();

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
        TestApp.getLogicManager(model);
        EncryptCommand encryptCommand = new EncryptCommand();
        encryptCommand.setData(model, mock(Network.class), new CommandHistory(), new UndoStack());
        CommandResult result = encryptCommand.execute();
        if (KeyControl.getInstance().getKey().equals("")) {
            String failmessage = EncryptCommand.MESSAGE_FAIL;
            assertEquals(failmessage, result.feedbackToUser);
        } else {
            String successMessage = EncryptCommand.MESSAGE_SUCCESS;
            assertEquals(successMessage, result.feedbackToUser);
        }
    }
}
