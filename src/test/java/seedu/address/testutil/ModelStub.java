package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.AlreadyCaptainException;
import seedu.address.model.person.exceptions.AlreadyNotCaptainException;
import seedu.address.model.position.Position;
import seedu.address.model.team.Team;


//@@author

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getPersonByName(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTeamList(Predicate<Team> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void makeCaptain(Person person) {
        if (person.isCaptain()) {
            throw new AlreadyCaptainException();
        }
        person.makeCaptain();
    }

    @Override
    public void stripCaptain(Person person) {
        if (!person.isCaptain()) {
            throw new AlreadyNotCaptainException();
        }
        person.stripCaptain();
    }

    @Override
    public ObservableList<Team> getFilteredTeamList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTeam(Team team) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTeam(Team team) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPosition(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPosition(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePosition(Position position) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Position> getFilteredPositionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPositionList(Predicate<Position> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Position getPositionByName(String name) {
        throw new AssertionError("This method should not be called.");
    }

}
