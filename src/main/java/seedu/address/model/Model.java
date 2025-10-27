package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.FilterInjuredPredicate;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.position.Position;
import seedu.address.model.team.Team;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Team> PREDICATE_SHOW_ALL_TEAMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Position> PREDICATE_SHOW_ALL_POSITIONS = unused -> true;

    /** Shows only persons whose injury is non-default. */
    Predicate<Person> PREDICATE_SHOW_ALL_INJURED = new FilterInjuredPredicate();

    Predicate<Person> PREDICATE_SHOW_CAPTAINS = Person::isCaptain;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns the person with the given name.
     */
    Person getPersonByName(Name name);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the specified {@code injury} to the given person {@code target}'s injury list.
     * Returns a new {@code Person} instance with the updated injuries.
     * {@code target} must exist in the address book.
     */
    Person addInjury(Person target, Injury injury);

    /**
     * Removes the specified {@code injury} from the given person {@code target}'s injury list.
     * {@code target} must exist in the address book.
     */
    Person deleteInjury(Person target, Injury injury);

    /**
     * Returns true if the given person {@code target} has any injury status
     * that is not the default {@code "FIT"} status.
     * {@code target} must exist in the address book.
     */
    boolean hasNonDefaultInjury(Person target);

    /**
     * Returns true if the given person {@code target} has the specified {@code injury}.
     */
    boolean hasSpecificInjury(Person target, Injury injury);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a team with the same identity as {@code team} exists in the address book.
     */
    boolean hasTeam(Team team);

    /**
     * Returns the team from the team list that matches the given team (case-insensitive).
     */
    Team getTeamByName(Team team);

    /**
     * Adds the given team.
     * {@code team} must not already exist in the address book.
     */
    void addTeam(Team team);

    /**
     * Returns true if the team has no players, or if team does not exist in address book.
     */
    boolean isTeamEmpty(Team team);

    /**
     * Deletes team from the address book.
     * The team must exist in the address book.
     * The team must have no players assigned to it.
     */
    void deleteTeam(Team team);

    /**
     * Returns an unmodifiable view of the filtered team list.
     */
    ObservableList<Team> getFilteredTeamList();

    /**
     * Updates the filter of the filtered team list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTeamList(Predicate<Team> predicate);

    /**
     * Assigns the given team to the given person.
     * {@code person} and {@code team} must already exist in the address book.
     */
    void assignTeam(Person person, Team team);

    // Positions API
    boolean hasPosition(Position position);

    void addPosition(Position position);

    void deletePosition(Position position);

    ObservableList<Position> getFilteredPositionList();

    void updateFilteredPositionList(Predicate<Position> predicate);

    Position getPositionByName(String name);

    /**
     * Returns true if the given position is assigned to at least one person in the address book.
     * @param position the position to check
     * @return true if the position is assigned to any person, false otherwise
     */
    boolean isPositionAssigned(Position position);

    void assignCaptain(Person person);

    void stripCaptain(Person person);

    /**
     * Returns the current captain of the given team, if one exists.
     * @param team the team to check for a captain
     * @return the Person who is captain of the team, or null if no captain exists
     */
    Person getTeamCaptain(Team team);
}
