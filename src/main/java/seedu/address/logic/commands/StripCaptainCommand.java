package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Marks a person in the address book as no longer a team captain.
 * <p>
 * The person is identified by their {@link Name}. If the person does not exist
 * or is already not a captain, a {@link CommandException} is thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code stripCaptain p/Sergio Ramos}
 * </pre>
 */
public class StripCaptainCommand extends Command {
    public static final String COMMAND_WORD = "stripcaptain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the person "
            + "as no longer captain.\n"
            + "Parameters: " + PREFIX_PLAYER + "PLAYER "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PLAYER + "Sergio Ramos";

    public static final String MESSAGE_SUCCESS = "; %1$s is no longer team captain.";
    public static final String MESSAGE_NOT_CAPTAIN = "%1$s is not a captain!";

    private final Name targetName;

    /**
     * Creates a {@code StripCaptainCommand} targeting the person with
     * {@code targetName}.
     *
     * @param targetName the name of the person to strip captaincy from; must not be
     *                   null.
     */
    public StripCaptainCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson;

        try {
            targetPerson = model.getPersonByName(targetName);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetName));
        }

        if (!targetPerson.isCaptain()) {
            throw new CommandException(String.format(MESSAGE_NOT_CAPTAIN, targetPerson.getName()));
        }

        model.stripCaptain(targetPerson);

        return CommandResult.showPersonCommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(targetPerson)));

    }

    /**
     * Returns true if both commands target the same {@link Name}.
     *
     * @param other the other object to compare with
     * @return whether both commands are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StripCaptainCommand)) {
            return false;
        }

        StripCaptainCommand otherCommand = (StripCaptainCommand) other;
        return targetName.equals(otherCommand.targetName);
    }
}
