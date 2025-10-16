package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AssignPositionCommand} object.
 */
public class AssignPositionCommandParser implements Parser<AssignPositionCommand> {
    private static final Pattern ARG_PATTERN =
            Pattern.compile("(?i).*\\bpl/(?<player>[^\\s].*?)\\s+ps/(?<position>\\S+)\\s*");

    @Override
    public AssignPositionCommand parse(String args) throws ParseException {
        // Validates presence of both pl/ and ps/ flags and extracts values
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        Matcher m = ARG_PATTERN.matcher(args.trim());
        if (!m.matches()) {
            boolean hasPlayer = args.contains(PREFIX_PLAYER.getPrefix());
            boolean hasPosition = args.contains(PREFIX_POSITION.getPrefix());
            if (!hasPlayer) {
                throw new ParseException(AssignPositionCommand.MESSAGE_MISSING_PLAYER_FLAG);
            }
            if (!hasPosition) {
                throw new ParseException(AssignPositionCommand.MESSAGE_MISSING_POSITION_FLAG);
            }
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        String player = m.group("player").trim();
        String position = m.group("position").trim();
        if (player.isEmpty()) {
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        return new AssignPositionCommand(player, position);
    }
}


