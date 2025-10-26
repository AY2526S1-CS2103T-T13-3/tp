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
 * Marks a person in the PlayBook as the team captain.
 * <p>
 * The person is identified by their {@link Name}. If the person does not exist
 * or is already a captain, a {@link CommandException} is thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code assigncaptain pl/Sergio Ramos}
 * </pre>
 */
public class AssignCaptainCommand extends Command {
    public static final String COMMAND_WORD = "assigncaptain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns the player as captain of their team. \n"
            + "Parameters: "
            + PREFIX_PLAYER + "PLAYER_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAYER + "Sergio Ramos";

    public static final String MESSAGE_SUCCESS = "%1$s is now captain of %2$s";
    public static final String MESSAGE_ALREADY_CAPTAIN = "%1$s is already a captain!";
    public static final String MESSAGE_STRIPPED_PREVIOUS_CAPTAIN = "%1$s is no longer captain. ";

    private final Name targetName;

    /**
     * Creates a {@code AssignCaptainCommand} targeting the person with
     * {@code targetName}.
     *
     * @param targetName the name of the person to mark as captain; must not be
     *                   null.
     */
    public AssignCaptainCommand(Name targetName) {
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

        if (targetPerson.isCaptain()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_CAPTAIN, targetPerson.getName()));
        }

        Person currentCaptain = model.getTeamCaptain(targetPerson.getTeam());
        String resultMessage = "";
        if (currentCaptain != null) {
            model.stripCaptain(currentCaptain);
            resultMessage = String.format(MESSAGE_STRIPPED_PREVIOUS_CAPTAIN, currentCaptain.getName());
        }

        model.assignCaptain(targetPerson);

        resultMessage += String.format(MESSAGE_SUCCESS,
                targetPerson.getName(), targetPerson.getTeam().getName());
        return CommandResult.showPersonCommandResult(resultMessage);

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

        if (!(other instanceof AssignCaptainCommand)) {
            return false;
        }

        AssignCaptainCommand otherCommand = (AssignCaptainCommand) other;
        return targetName.equals(otherCommand.targetName);
    }
}
