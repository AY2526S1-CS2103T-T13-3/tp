package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TEAM;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.U12;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;
import seedu.address.testutil.TeamBuilder;

public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validTeamName_filtersCorrectly() {
        // U12 has persons in typical data
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(U12.getName());
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(U12.getName()), Optional.empty());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentTeam_throwsCommandException() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate("NoSuchTeam");
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(predicate.getTeamName()), Optional.empty());
        assertCommandFailure(command, model, MESSAGE_INVALID_TEAM);
    }

    @Test
    public void execute_teamHasNoPlayers_throwsCommandException() {
        // Create an empty model
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        // Add a team with no persons
        Team emptyTeam = new TeamBuilder().withName("LoneTeam").build();
        model.addTeam(emptyTeam);

        // Construct and execute the filter command
        FilterCommand command =
            new FilterCommand(new FilterByTeamPredicate(emptyTeam.getName()), FilterByInjuryPredicate.ALWAYS_TRUE,
                    Optional.of(emptyTeam.getName()), Optional.empty());

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, emptyTeam.getName()));
    }

    @Test
    public void equals() {
        FilterByTeamPredicate p1 = new FilterByTeamPredicate("A");
        FilterByTeamPredicate p2 = new FilterByTeamPredicate("B");

        FilterCommand cmd1 = new FilterCommand(p1, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()), Optional.empty());
        FilterCommand cmd1Copy = new FilterCommand(p1, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p1.getTeamName()), Optional.empty());
        FilterCommand cmd2 = new FilterCommand(p2, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(p2.getTeamName()), Optional.empty());

        // same object
        assertTrue(cmd1.equals(cmd1));
        // same values
        assertTrue(cmd1.equals(cmd1Copy));
        // different type
        assertFalse(cmd1.equals(1));
        // null
        assertFalse(cmd1.equals(null));
        // different predicate
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void toString_containsPredicate() {
        FilterByTeamPredicate predicate = new FilterByTeamPredicate(U12.getName());
        FilterCommand command = new FilterCommand(predicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                Optional.of(predicate.getTeamName()), Optional.empty());
        String str = command.toString();
        assertTrue(str.contains("teamPredicate=" + predicate.toString()));
    }
}
