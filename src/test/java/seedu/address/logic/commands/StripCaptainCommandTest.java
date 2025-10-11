package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code StripCaptainCommand}.
 */
public class StripCaptainCommandTest {

    @Test
    public void execute_personExistsAndIsCaptain_stripsCaptaincy() throws Exception {
        Person person = new PersonBuilder().withCaptain(true).build();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }
        };

        StripCaptainCommand command = new StripCaptainCommand(name);
        CommandResult result = command.execute(modelStub);

        assertFalse(person.isCaptain());
        assertEquals(CommandResult.showPersonCommandResult(String.format(StripCaptainCommand.MESSAGE_SUCCESS,
                Messages.format(person))), result);
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() {
        Name name = new Name("Non Existent");
        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                throw new seedu.address.model.person.exceptions.PersonNotFoundException();
            }
        };

        StripCaptainCommand command = new StripCaptainCommand(name);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            assertEquals(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, name), e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for missing person.");
        }
        throw new AssertionError("Expected CommandException for missing person.");
    }

    @Test
    public void execute_personAlreadyNotCaptain_throwsCommandException() {
        Person person = new PersonBuilder().build();
        // Ensure starting state is not captain
        person.stripCaptain();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }
        };

        StripCaptainCommand command = new StripCaptainCommand(name);

        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            assertEquals(String.format(StripCaptainCommand.MESSAGE_NOT_CAPTAIN, person.getName()), e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for not-a-captain.");
        }
        throw new AssertionError("Expected CommandException for not-a-captain.");
    }

    @Test
    public void equals() {
        Name sergio = new Name("Sergio Ramos");
        Name leo = new Name("Lionel Messi");
        StripCaptainCommand stripSergio = new StripCaptainCommand(sergio);
        StripCaptainCommand stripSergioCopy = new StripCaptainCommand(sergio);
        StripCaptainCommand stripLeo = new StripCaptainCommand(leo);

        // same object -> true
        assertTrue(stripSergio.equals(stripSergio));
        // same values -> true
        assertTrue(stripSergio.equals(stripSergioCopy));
        // null -> false
        assertFalse(stripSergio.equals(null));
        // different type -> false
        assertFalse(stripSergio.equals(1));
        // different name -> false
        assertFalse(stripSergio.equals(stripLeo));
    }
}
