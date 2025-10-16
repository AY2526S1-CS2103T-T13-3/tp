package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.position.Position;

public class JsonAdaptedPositionTest {

    private static final String VALID_NAME = "FW";
    private static final String INVALID_NAME = ""; // empty string is invalid
    private static final String INVALID_NAME_WITH_SPACE = "FOR WARD"; // space is invalid
    private static final String INVALID_NAME_WITH_SPECIAL_CHAR = "FW@"; // special character is invalid

    @Test
    public void toModelType_validPositionDetails_returnsPosition() throws Exception {
        JsonAdaptedPosition position = new JsonAdaptedPosition(VALID_NAME);
        assertEquals(new Position(VALID_NAME), position.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPosition position = new JsonAdaptedPosition(INVALID_NAME);
        String expectedMessage = Position.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, position::toModelType);
    }

    @Test
    public void toModelType_invalidNameWithSpace_throwsIllegalValueException() {
        JsonAdaptedPosition position = new JsonAdaptedPosition(INVALID_NAME_WITH_SPACE);
        String expectedMessage = Position.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, position::toModelType);
    }

    @Test
    public void toModelType_invalidNameWithSpecialChar_throwsIllegalValueException() {
        JsonAdaptedPosition position = new JsonAdaptedPosition(INVALID_NAME_WITH_SPECIAL_CHAR);
        String expectedMessage = Position.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, position::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPosition position = new JsonAdaptedPosition((String) null);
        String expectedMessage = String.format(JsonAdaptedPosition.MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, position::toModelType);
    }

    @Test
    public void constructor_validPosition_success() throws Exception {
        Position validPosition = new Position(VALID_NAME);
        JsonAdaptedPosition jsonPosition = new JsonAdaptedPosition(validPosition);
        assertEquals(validPosition, jsonPosition.toModelType());
    }

    @Test
    public void toModelType_caseInsensitiveName_returnsPosition() throws Exception {
        JsonAdaptedPosition position = new JsonAdaptedPosition("fw");
        Position modelPosition = position.toModelType();
        assertEquals("fw", modelPosition.getName());
    }
}
