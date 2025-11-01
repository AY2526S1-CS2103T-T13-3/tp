package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code AssignCaptainCommand}.
 */
public class AssignCaptainCommandTest {

    @Test
    public void execute_personExistsAndNotCaptain_marksAsCaptain() throws Exception {
        Person person = new PersonBuilder().withCaptain(false).build();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            private boolean assignCaptainCalled = false;

            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new seedu.address.model.person.exceptions.PersonNotFoundException();
                }
                return person;
            }

            @Override
            public Person getTeamCaptain(seedu.address.model.team.Team team) {
                // No existing captain
                return null;
            }

            @Override
            public void assignCaptain(Person person) {
                assignCaptainCalled = true;
            }
        };

        AssignCaptainCommand command = new AssignCaptainCommand(name);
        CommandResult result = command.execute(modelStub);

        String expectedSuccess = String.format(AssignCaptainCommand.MESSAGE_SUCCESS,
                person.getName(), person.getTeam().getName());
        assertEquals(CommandResult.showPersonCommandResult(expectedSuccess), result);
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

        AssignCaptainCommand command = new AssignCaptainCommand(name);
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
    public void execute_personAlreadyCaptain_throwsCommandException() {
        Person person = new PersonBuilder().withCaptain(true).build();
        Name name = person.getName();

        ModelStub modelStub = new ModelStub() {
            @Override
            public Person getPersonByName(Name queryName) {
                if (!queryName.equals(name)) {
                    throw new PersonNotFoundException();
                }
                return person;
            }
        };

        AssignCaptainCommand command = new AssignCaptainCommand(name);
        try {
            command.execute(modelStub);
        } catch (CommandException e) {
            String expected = String.format(AssignCaptainCommand.MESSAGE_ALREADY_CAPTAIN,
                    person.getName(), person.getTeam().getName());
            assertEquals(expected, e.getMessage());
            return;
        } catch (Exception e) {
            throw new AssertionError("Expected CommandException for already captain.");
        }
        throw new AssertionError("Expected CommandException for already captain.");
    }

    @Test
    public void execute_teamAlreadyHasCaptain_stripsOldCaptainAndMakesNewCaptain() throws Exception {
        Person oldCaptain = new PersonBuilder().withName("Old Captain").withCaptain(true).build();
        Person newCaptain = new PersonBuilder().withName("New Captain").withCaptain(false).build();
        Name newCaptainName = newCaptain.getName();

        ModelStub modelStub = new ModelStub() {
            private boolean oldCaptainStripped = false;
            private boolean assignCaptainCalled = false;

            @Override
            public Person getPersonByName(Name queryName) {
                if (queryName.equals(newCaptainName)) {
                    return newCaptain;
                }
                throw new PersonNotFoundException();
            }

            @Override
            public Person getTeamCaptain(seedu.address.model.team.Team team) {
                return oldCaptainStripped ? null : oldCaptain;
            }

            @Override
            public void unassignCaptain(Person person) {
                if (person.equals(oldCaptain)) {
                    oldCaptainStripped = true;
                }
            }

            @Override
            public void assignCaptain(Person person) {
                assignCaptainCalled = true;
            }
        };

        AssignCaptainCommand command = new AssignCaptainCommand(newCaptainName);
        CommandResult result = command.execute(modelStub);

        String expectedMessage = String.format(AssignCaptainCommand.MESSAGE_STRIPPED_PREVIOUS_CAPTAIN,
                oldCaptain.getName())
                + String.format(AssignCaptainCommand.MESSAGE_SUCCESS,
                newCaptain.getName(), newCaptain.getTeam().getName());
        assertEquals(CommandResult.showPersonCommandResult(expectedMessage), result);
    }

    @Test
    public void equals() {
        Name sergio = new Name("Sergio Ramos");
        Name leo = new Name("Lionel Messi");
        AssignCaptainCommand makeSergio = new AssignCaptainCommand(sergio);
        AssignCaptainCommand makeSergioCopy = new AssignCaptainCommand(sergio);
        AssignCaptainCommand makeLeo = new AssignCaptainCommand(leo);

        // same object -> true
        assertTrue(makeSergio.equals(makeSergio));
        // same values -> true
        assertTrue(makeSergio.equals(makeSergioCopy));
        // null -> false
        assertFalse(makeSergio.equals(null));
        // different type -> false
        assertFalse(makeSergio.equals(1));
        // different name -> false
        assertFalse(makeSergio.equals(makeLeo));
    }
}
