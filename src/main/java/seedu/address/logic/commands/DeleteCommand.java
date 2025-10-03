package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified by name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person specified by name.\n"
            + "Parameters: "
            + PREFIX_PLAYER + "PLAYER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAYER + "John Doe ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Name personNameToDelete;

    /**
     * Creates a DeleteCommand to delete the specified {@code Person} by name.
     */
    public DeleteCommand(Name personNameToDelete) {
        requireNonNull(personNameToDelete);
        this.personNameToDelete = personNameToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete;

        try {
            personToDelete = model.getPersonByName(personNameToDelete);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personNameToDelete.toString()));
        }

        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)), false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return personNameToDelete.equals(otherDeleteCommand.personNameToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personToDelete", personNameToDelete)
                .toString();
    }
}
