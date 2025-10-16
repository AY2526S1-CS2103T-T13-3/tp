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

public class NewPositionCommandTest {

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NewPositionCommand(null));
    }

    @Test
    public void execute_newPosition_success() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand command = new NewPositionCommand(VALID_POSITION_FW);
        CommandResult result = command.execute(model);

        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, VALID_POSITION_FW),
                result.getFeedbackToUser());
        assertTrue(model.hasPosition(new Position(VALID_POSITION_FW)));
    }

    @Test
    public void execute_addAndDuplicate_behaviour() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand add = new NewPositionCommand(VALID_POSITION_AMY);
        CommandResult res = add.execute(model);
        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, VALID_POSITION_AMY), res.getFeedbackToUser());

        // duplicate
        assertThrows(CommandException.class, () ->
                new NewPositionCommand(VALID_POSITION_AMY.toLowerCase()).execute(model));
    }

    @Test
    public void execute_duplicatePosition_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);

        // Exact duplicate
        assertThrows(CommandException.class, String.format(
                NewPositionCommand.MESSAGE_DUPLICATE, VALID_POSITION_FW), () ->
                new NewPositionCommand(VALID_POSITION_FW).execute(model));
    }

    @Test
    public void execute_duplicatePositionDifferentCase_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);

        // Different case should still be duplicate
        assertThrows(CommandException.class, () ->
                new NewPositionCommand(VALID_POSITION_FW.toLowerCase()).execute(model));
        assertThrows(CommandException.class, () -> new NewPositionCommand(
                VALID_POSITION_FW.substring(0, 1) + VALID_POSITION_FW.substring(1).toLowerCase()).execute(model));
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();

        // Empty string
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand("").execute(model));

        // Contains space
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand(INVALID_POSITION_WITH_SPACE).execute(model));

        // Special characters
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new NewPositionCommand("FW@").execute(model));
    }

    @Test
    public void execute_whitespaceInName_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        NewPositionCommand command = new NewPositionCommand(" " + VALID_POSITION_FW + " ");
        CommandResult result = command.execute(model);

        assertEquals(String.format(NewPositionCommand.MESSAGE_SUCCESS, VALID_POSITION_FW),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_multiplePositions_allAdded() throws Exception {
        Model model = new ModelManager();
        String midfielderPosition = "MF";

        new NewPositionCommand(VALID_POSITION_FW).execute(model);
        new NewPositionCommand(midfielderPosition).execute(model);
        new NewPositionCommand(VALID_POSITION_GK).execute(model);

        assertTrue(model.hasPosition(new Position(VALID_POSITION_FW)));
        assertTrue(model.hasPosition(new Position(midfielderPosition)));
        assertTrue(model.hasPosition(new Position(VALID_POSITION_GK)));
    }

    @Test
    public void equals() {
        NewPositionCommand addFW = new NewPositionCommand(VALID_POSITION_FW);
        NewPositionCommand addGK = new NewPositionCommand(VALID_POSITION_GK);

        // same object -> returns true
        assertTrue(addFW.equals(addFW));

        // same values -> returns true
        NewPositionCommand addFwCopy = new NewPositionCommand(VALID_POSITION_FW);
        assertTrue(addFW.equals(addFwCopy));

        // different types -> returns false
        assertFalse(addFW.equals(1));

        // null -> returns false
        assertFalse(addFW.equals(null));

        // different position -> returns false
        assertFalse(addFW.equals(addGK));
    }
}


