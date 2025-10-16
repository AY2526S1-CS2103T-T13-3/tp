package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_WITH_SPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_FW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_GK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.position.Position;
import seedu.address.testutil.PersonBuilder;

public class AssignPositionCommandTest {

    @Test
    public void constructor_nullPlayerName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPositionCommand(null, VALID_POSITION_FW));
    }

    @Test
    public void constructor_nullPositionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignPositionCommand(VALID_NAME_AMY, null));
    }

    @Test
    public void execute_assign_successAndDuplicate() throws Exception {
        Model model = new ModelManager();
        // Ensure position exists
        new NewPositionCommand(VALID_POSITION_AMY).execute(model);
        // Build and add a person named Amy
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        CommandResult res = new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_AMY).execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, VALID_NAME_AMY, VALID_POSITION_AMY),
                res.getFeedbackToUser());

        // Duplicate assignment
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_DUPLICATE_ASSIGN, VALID_NAME_AMY, VALID_POSITION_AMY), () ->
                new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_AMY.toLowerCase()).execute(model));
    }

    @Test
    public void execute_positionNotFound_throwsCommandException() {
        Model model = new ModelManager();
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        // Position doesn't exist
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_POSITION_NOT_FOUND, VALID_POSITION_AMY), () ->
                new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_AMY).execute(model));
    }

    @Test
    public void execute_playerNotFound_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        // Add position but no person
        new NewPositionCommand(VALID_POSITION_AMY).execute(model);

        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_PLAYER_NOT_FOUND, VALID_NAME_AMY), () ->
                new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_AMY).execute(model));
    }

    @Test
    public void execute_missingEntities_fail() {
        Model model = new ModelManager();
        // No person, no position
        assertThrows(CommandException.class, String.format(
                AssignPositionCommand.MESSAGE_POSITION_NOT_FOUND, VALID_POSITION_AMY), () ->
                new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_AMY).execute(model));
    }

    @Test
    public void execute_invalidPositionName_throwsCommandException() {
        Model model = new ModelManager();
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        // Invalid position name (contains space)
        assertThrows(CommandException.class, Position.MESSAGE_CONSTRAINTS, () ->
                new AssignPositionCommand(VALID_NAME_AMY, INVALID_POSITION_WITH_SPACE).execute(model));
    }

    @Test
    public void execute_caseInsensitivePlayerName_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        // Should work with different case
        String lowerCaseName = VALID_NAME_AMY.toLowerCase();
        CommandResult res = new AssignPositionCommand(lowerCaseName, VALID_POSITION_FW).execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, lowerCaseName, VALID_POSITION_FW),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_caseInsensitivePositionName_success() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        // Should work with different case - message returns actual position name from model
        CommandResult res = new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_FW.toLowerCase()).execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, VALID_NAME_AMY, VALID_POSITION_FW),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_whitespaceInNames_trimsCorrectly() throws Exception {
        Model model = new ModelManager();
        new NewPositionCommand(VALID_POSITION_FW).execute(model);
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).withTeam(VALID_TEAM_AMY).build();
        model.addPerson(amy);

        // Should trim whitespace
        CommandResult res = new AssignPositionCommand(" " + VALID_NAME_AMY + " ",
                " " + VALID_POSITION_FW + " ").execute(model);
        assertEquals(String.format(AssignPositionCommand.MESSAGE_SUCCESS, VALID_NAME_AMY, VALID_POSITION_FW),
                res.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AssignPositionCommand assignAmyFW = new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_FW);
        AssignPositionCommand assignBobFW = new AssignPositionCommand(VALID_NAME_BOB, VALID_POSITION_FW);
        AssignPositionCommand assignAmyGK = new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_GK);

        // same object -> returns true
        assertTrue(assignAmyFW.equals(assignAmyFW));

        // same values -> returns true
        AssignPositionCommand assignAmyFwCopy = new AssignPositionCommand(VALID_NAME_AMY, VALID_POSITION_FW);
        assertTrue(assignAmyFW.equals(assignAmyFwCopy));

        // different types -> returns false
        assertFalse(assignAmyFW.equals(1));

        // null -> returns false
        assertFalse(assignAmyFW.equals(null));

        // different player -> returns false
        assertFalse(assignAmyFW.equals(assignBobFW));

        // different position -> returns false
        assertFalse(assignAmyFW.equals(assignAmyGK));
    }
}


