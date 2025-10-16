package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.DeletePositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeletePositionCommand} object.
 */
public class DeletePositionCommandParser implements Parser<DeletePositionCommand> {
    private static final Pattern ARG_PATTERN = Pattern.compile("(?i)\\s*ps/(?<name>\\S+)\\s*");

    @Override
    public DeletePositionCommand parse(String args) throws ParseException {
        // Validates presence of ps/ flag and extracts value
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(DeletePositionCommand.MESSAGE_INVALID_FORMAT);
        }
        Matcher m = ARG_PATTERN.matcher(args);
        if (!m.matches()) {
            if (!args.contains(PREFIX_POSITION.getPrefix())) {
                throw new ParseException(DeletePositionCommand.MESSAGE_MISSING_FLAG);
            }
            throw new ParseException(DeletePositionCommand.MESSAGE_INVALID_FORMAT);
        }
        String posName = m.group("name");
        return new DeletePositionCommand(posName);
    }
}


