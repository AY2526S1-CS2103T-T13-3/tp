package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Name nonExistentName = new Name(NON_EXISTENT_NAME);
        DeleteCommand deleteCommand = new DeleteCommand(nonExistentName);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_PERSON_NOT_FOUND,
                nonExistentName));
    }

    @Test
    public void execute_validName_success() {
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Name aliceName = ALICE.getName();
        Name bensonName = BENSON.getName();
        DeleteCommand deleteAliceCommand = new DeleteCommand(aliceName);
        DeleteCommand deleteBensonCommand = new DeleteCommand(bensonName);

        // same object -> returns true
        assertTrue(deleteAliceCommand.equals(deleteAliceCommand));

        // same values -> returns true
        DeleteCommand deleteAliceCommandCopy = new DeleteCommand(aliceName);
        assertTrue(deleteAliceCommand.equals(deleteAliceCommandCopy));

        // different types -> returns false
        assertFalse(deleteAliceCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteAliceCommand.equals(deleteBensonCommand));
    }

    @Test
    public void toStringMethod() {
        Name targetName = new Name(VALID_NAME_AMY);
        DeleteCommand deleteCommand = new DeleteCommand(targetName);
        String expected = DeleteCommand.class.getCanonicalName() + "{personToDelete=" + targetName + "}";
        assertEquals(expected, deleteCommand.toString());
    }

}
