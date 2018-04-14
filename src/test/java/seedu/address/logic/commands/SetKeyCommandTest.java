package seedu.address.logic.commands;
//@@author 592363789
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.KeyChangedEvent;
import seedu.address.logic.KeyControl;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SetKeyCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        KeyControl.getInstance().setKey("testing");
        EventsCenter.getInstance().post(new KeyChangedEvent("testing"));
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

}
