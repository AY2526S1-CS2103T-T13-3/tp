package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_CAPTAINS;

import seedu.address.model.Model;

/**
 * Filters the list of persons in the address book to show only captains.
 * <p>
 * This command updates the filtered person list in the model using
 * {@code PREDICATE_SHOW_CAPTAINS} and returns a message indicating
 * the successful operation.
 */
public class FilterCaptainCommand extends Command {
    public static final String COMMAND_WORD = "filtercaptains";
    public static final String MESSAGE_SUCCESS = "Listed all captains";

    /**
     * Executes the filter captains command by updating the model's filtered person
     * list
     * to include only captains.
     *
     * @param model the model to operate on; must not be null
     * @return a {@link CommandResult} containing the success message
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_CAPTAINS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
