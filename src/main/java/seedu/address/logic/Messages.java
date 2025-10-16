package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person '%1$s' does not exist.";
    public static final String MESSAGE_INVALID_TEAM = "No such team in the address book.";
    public static final String MESSAGE_NO_PLAYERS_IN_TEAM = "No players assigned to team \"%1$s\".";
    public static final String MESSAGE_NO_PLAYERS_WITH_INJURY = "No players with injury \"%1$s\".";
    public static final String MESSAGE_NO_PLAYERS_WITH_POSITION = "No players with position \"%1$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_INJURY_AND_POSITION =
            "No players matching team \"%1$s\", injury \"%2$s\" and position \"%3$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_AND_INJURY =
            "No players matching team \"%1$s\" and injury \"%2$s\".";
    public static final String MESSAGE_NO_MATCHING_TEAM_AND_POSITION =
            "No players matching team \"%1$s\" and position \"%2$s\".";
    public static final String MESSAGE_NO_MATCHING_INJURY_AND_POSITION =
            "No players matching injury \"%1$s\" and position \"%2$s\".";
    public static final String MESSAGE_NO_TEAMS = "There are currently no teams in the PlayBook.";
    public static final String MESSAGE_NO_POSITIONS = "There are currently no positions in the PlayBook.";
    public static final String MESSAGE_NO_INJURED = "There are currently no injured players in the PlayBook.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Team: ")
                .append(person.getTeam())
                .append("; Injury Status: ")
                .append(person.getInjury())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code team} for display to the user.
     */
    public static String format(Team team) {
        final StringBuilder builder = new StringBuilder();
        builder.append(team.getName());
        return builder.toString();
    }
}
