package seedu.address.logic.commands;
//@@author 592363789
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.KeyControl;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.network.Network;

public class SetKeyCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        KeyControl.getInstance().setKey("testing");
    }

    @Test
    public void equals() {

        SetKeyCommand setKeyCommand = new SetKeyCommand("testing", "newkey");

        SetKeyCommand thesameCommand = new SetKeyCommand("testing", "newkey");

        // same value -> returns true
        assertTrue(setKeyCommand.equals(thesameCommand));

        // same object -> returns true
        assertTrue(setKeyCommand.equals(setKeyCommand));

        // null -> returns false
        assertFalse(setKeyCommand.equals(null));

        // different commandtypes -> returns false
        assertFalse(setKeyCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(setKeyCommand.equals(0));

    }

    @Test
    public void sameKeyTest() {
        SetKeyCommand skc = new SetKeyCommand("testing", "newkey");
        skc.setData(model, mock(Network.class), new CommandHistory(), new UndoStack());
        String expect = SetKeyCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = skc.execute();

        assertEquals(expect, commandResult.feedbackToUser);
    }

    @Test
    public void differentKeyTest() {
        SetKeyCommand skc = new SetKeyCommand("wrongtesting", "newkey");
        skc.setData(model, mock(Network.class), new CommandHistory(), new UndoStack());
        String expect = SetKeyCommand.WRONG_OLDKEY;
        CommandResult commandResult = skc.execute();

        assertEquals(expect, commandResult.feedbackToUser);
    }

}
