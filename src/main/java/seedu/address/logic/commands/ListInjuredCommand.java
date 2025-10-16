package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INJURED;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Lists all persons whose injury status is non-default in the PlayBook.
 */
public class ListInjuredCommand extends Command {
    public static final String COMMAND_WORD = "listinjured";

    public static final String MESSAGE_SUCCESS = "Listed all injured players";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_INJURED);

        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_INJURED);
        }

        return CommandResult.showPersonCommandResult(MESSAGE_SUCCESS);
    }
}
