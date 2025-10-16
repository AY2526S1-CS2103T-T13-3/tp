package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_NO_INJURED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INJURED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class ListInjuredCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_INJURED);
    }

    @Test
    void execute_injuredPlayers_showsOnlyInjured() {
        assertCommandSuccess(new ListInjuredCommand(), model,
            ListInjuredCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyPlayBook_showsEmptyInjuredList() {
        // Create a model with an empty PlayBook
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandFailure(new ListInjuredCommand(), emptyModel, MESSAGE_NO_INJURED);
    }
}
