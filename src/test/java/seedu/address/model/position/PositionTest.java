package seedu.address.model.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPositionName_throwsIllegalArgumentException() {
        String invalidPositionName = "";
        assertThrows(IllegalArgumentException.class, () -> new Position(invalidPositionName));
    }

    @Test
    public void constructor_validPositionName_success() {
        // Valid alphanumeric position
        Position position = new Position("FW");
        assertEquals("FW", position.getName());
    }

    @Test
    public void isValidPositionName() {
        // null position name - returns false
        assertFalse(Position.isValidPositionName(null));

        // invalid position names
        assertFalse(Position.isValidPositionName("")); // empty string
        assertFalse(Position.isValidPositionName(" ")); // spaces only
        assertFalse(Position.isValidPositionName("^")); // non-alphanumeric characters
        assertFalse(Position.isValidPositionName("For ward")); // space in between
        assertFalse(Position.isValidPositionName("FW@")); // contains special character
        assertFalse(Position.isValidPositionName("FW-MF")); // contains dash

        // valid position names
        assertTrue(Position.isValidPositionName("FW")); // uppercase
        assertTrue(Position.isValidPositionName("fw")); // lowercase
        assertTrue(Position.isValidPositionName("GK")); // 2 letters
        assertTrue(Position.isValidPositionName("CB")); // different position
        assertTrue(Position.isValidPositionName("123")); // numbers only
        assertTrue(Position.isValidPositionName("FW2")); // alphanumeric
        assertTrue(Position.isValidPositionName("Striker")); // long name
    }

    @Test
    public void isSamePosition() {
        Position fw = new Position("FW");

        // same object -> returns true
        assertTrue(fw.isSamePosition(fw));

        // null -> returns false
        assertFalse(fw.isSamePosition(null));

        // same name, different case -> returns true (case-insensitive)
        Position fwLowercase = new Position("fw");
        assertTrue(fw.isSamePosition(fwLowercase));

        // different position -> returns false
        Position gk = new Position("GK");
        assertFalse(fw.isSamePosition(gk));
    }

    @Test
    public void equals() {
        Position fw = new Position("FW");

        // same object -> returns true
        assertTrue(fw.equals(fw));

        // null -> returns false
        assertFalse(fw.equals(null));

        // different type -> returns false
        assertFalse(fw.equals(5));

        // same name -> returns true
        Position fwCopy = new Position("FW");
        assertTrue(fw.equals(fwCopy));

        // same name, different case -> returns true (case-insensitive)
        Position fwLowercase = new Position("fw");
        assertTrue(fw.equals(fwLowercase));

        // different position -> returns false
        Position gk = new Position("GK");
        assertFalse(fw.equals(gk));
    }

    @Test
    public void hashCode_samePosition_sameHashCode() {
        Position fw1 = new Position("FW");
        Position fw2 = new Position("FW");
        assertEquals(fw1.hashCode(), fw2.hashCode());

        // case-insensitive hash code
        Position fwLower = new Position("fw");
        assertEquals(fw1.hashCode(), fwLower.hashCode());
    }

    @Test
    public void toString_validPosition_correctFormat() {
        Position position = new Position("FW");
        String expected = "seedu.address.model.position.Position{name=FW}";
        assertEquals(expected, position.toString());
    }
}
