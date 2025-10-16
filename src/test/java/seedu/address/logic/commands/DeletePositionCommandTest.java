package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_WITH_SPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_FW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_GK;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.position.Position;

public class DeletePositionCommandTest {

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePositionCommand(null));
    }

    @Test
    public void execute_deleteExisting_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);

        CommandResult result = new DeletePositionCommand(VALID_POSITION_FW).execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, VALID_POSITION_FW),
                result.getFeedbackToUser());
        assertFalse(model.hasPosition(new Position(VALID_POSITION_FW)));
    }

    @Test
    public void execute_deleteExistingAndMissing_behaviour() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_AMY).execute(model);
        String lowerCasePosition = VALID_POSITION_AMY.toLowerCase();
        CommandResult res = new DeletePositionCommand(lowerCasePosition).execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, lowerCasePosition), res.getFeedbackToUser());
        assertThrows(CommandException.class, DeletePositionCommand.MESSAGE_NOT_FOUND, () ->
                new DeletePositionCommand(lowerCasePosition).execute(model));
    }

    @Test
    public void execute_positionNotFound_throwsCommandException() {
        Model model = new ModelManager();

        assertThrows(CommandException.class, DeletePositionCommand.MESSAGE_NOT_FOUND, () ->
                new DeletePositionCommand(VALID_POSITION_FW).execute(model));
    }

    @Test
    public void execute_caseInsensitiveDelete_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);

        // Should work with different case
        String lowerCasePosition = VALID_POSITION_FW.toLowerCase();
        CommandResult result = new DeletePositionCommand(lowerCasePosition).execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, lowerCasePosition),
                result.getFeedbackToUser());
        assertFalse(model.hasPosition(new Position(VALID_POSITION_FW)));
    }

    @Test
    public void execute_whitespaceInName_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);

        CommandResult result = new DeletePositionCommand(" " + VALID_POSITION_FW + " ").execute(model);
        assertEquals(String.format(DeletePositionCommand.MESSAGE_SUCCESS, VALID_POSITION_FW),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();

        // Contains space
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new DeletePositionCommand(INVALID_POSITION_WITH_SPACE).execute(model));

        // Empty string
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new DeletePositionCommand("").execute(model));
    }

    @Test
    public void execute_deleteAfterAdd_modelUpdated() throws Exception {
        Model model = new ModelManager();
        String midfielderPosition = "MF";
        new NewPositionCommand(VALID_POSITION_FW).execute(model);
        new NewPositionCommand(midfielderPosition).execute(model);

        assertTrue(model.hasPosition(new Position(VALID_POSITION_FW)));
        assertTrue(model.hasPosition(new Position(midfielderPosition)));

        new DeletePositionCommand(VALID_POSITION_FW).execute(model);

        assertFalse(model.hasPosition(new Position(VALID_POSITION_FW)));
        assertTrue(model.hasPosition(new Position(midfielderPosition)));
    }

    @Test
    public void equals() {
        DeletePositionCommand deleteFW = new DeletePositionCommand(VALID_POSITION_FW);
        DeletePositionCommand deleteGK = new DeletePositionCommand(VALID_POSITION_GK);

        // same object -> returns true
        assertTrue(deleteFW.equals(deleteFW));

        // same values -> returns true
        DeletePositionCommand deleteFwCopy = new DeletePositionCommand(VALID_POSITION_FW);
        assertTrue(deleteFW.equals(deleteFwCopy));

        // different types -> returns false
        assertFalse(deleteFW.equals(1));

        // null -> returns false
        assertFalse(deleteFW.equals(null));

        // different position -> returns false
        assertFalse(deleteFW.equals(deleteGK));
    }
}


