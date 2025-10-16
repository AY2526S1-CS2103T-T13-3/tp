package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.position.Position;

/**
 * Assigns an existing {@code Position} to an existing {@code Person}.
 * <p>
 * Usage: {@code assignposition pl/<player> ps/<position>}
 */
public class AssignPositionCommand extends Command {
    public static final String COMMAND_WORD = "assignposition";
    public static final String MESSAGE_SUCCESS = "%s has been successfully assigned position %s!";
    public static final String MESSAGE_PLAYER_NOT_FOUND = "%s doesn't exist";
    public static final String MESSAGE_POSITION_NOT_FOUND = "%s doesn't exist";
    public static final String MESSAGE_DUPLICATE_ASSIGN = "%s is already assigned position %s!";
    public static final String MESSAGE_MISSING_PLAYER_FLAG = "Missing 'pl/' flag for assignposition command";
    public static final String MESSAGE_MISSING_POSITION_FLAG = "Missing 'ps/' flag for assignposition command";
    public static final String MESSAGE_INVALID_FORMAT =
            "Invalid command format. Please ensure correct format: assignposition pl/<player> ps/<position>";

    private final String rawPlayerName;
    private final String rawPositionName;

    /**
     * Creates an {@code AssignPositionCommand}.
     *
     * @param rawPlayerName raw player name string (may require trimming and case-insensitive matching).
     * @param rawPositionName raw position name string (may require trimming and case-insensitive matching).
     */
    public AssignPositionCommand(String rawPlayerName, String rawPositionName) {
        requireNonNull(rawPlayerName);
        requireNonNull(rawPositionName);
        this.rawPlayerName = rawPlayerName;
        this.rawPositionName = rawPositionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Assign position to player with validations for existence and duplicates
        requireNonNull(model);
        final String playerNameStr = rawPlayerName.trim();
        final String positionNameStr = rawPositionName.trim();

        if (!Position.isValidPositionName(positionNameStr)) {
            throw new CommandException(Position.MESSAGE_CONSTRAINTS);
        }

        final Position position;
        try {
            position = model.getPositionByName(positionNameStr);
        } catch (RuntimeException e) {
            throw new CommandException(String.format(MESSAGE_POSITION_NOT_FOUND, positionNameStr));
        }

        final Person person;
        try {
            person = model.getPersonByName(new Name(playerNameStr));
        } catch (RuntimeException e) {
            throw new CommandException(String.format(MESSAGE_PLAYER_NOT_FOUND, playerNameStr));
        }

        if (person.getPosition().getName().equalsIgnoreCase(position.getName())) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ASSIGN, playerNameStr, position.getName()));
        }

        Person edited = new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTeam(),
                person.getTags(),
                position,
                person.getInjury()
        );
        model.setPerson(person, edited);
        return new CommandResult(String.format(MESSAGE_SUCCESS, playerNameStr, position.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignPositionCommand)) {
            return false;
        }

        AssignPositionCommand otherCommand = (AssignPositionCommand) other;
        return rawPlayerName.equals(otherCommand.rawPlayerName)
                && rawPositionName.equals(otherCommand.rawPositionName);
    }
}


