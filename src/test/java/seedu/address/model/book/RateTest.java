package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rate(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Rate("Desc 1").hashCode(), new Rate("Desc 1").hashCode());
        assertEquals(new Rate("Desc x").hashCode(), new Rate("Desc x").hashCode());
    }
}
