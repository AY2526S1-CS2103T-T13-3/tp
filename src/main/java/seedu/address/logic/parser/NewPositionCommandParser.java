package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.NewPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code NewPositionCommand} object.
 */
public class NewPositionCommandParser implements Parser<NewPositionCommand> {
    private static final Pattern ARG_PATTERN = Pattern.compile("(?i)\\s*ps/(?<name>\\S+)\\s*");

    @Override
    public NewPositionCommand parse(String args) throws ParseException {
        // Validates presence of ps/ flag and extracts value
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(NewPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        Matcher m = ARG_PATTERN.matcher(args);
        if (!m.matches()) {
            if (!args.contains(PREFIX_POSITION.getPrefix())) {
                throw new ParseException(NewPositionCommand.MESSAGE_MISSING_FLAG);
            }
            throw new ParseException(NewPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        String posName = m.group("name");
        return new NewPositionCommand(posName);
    }
}


