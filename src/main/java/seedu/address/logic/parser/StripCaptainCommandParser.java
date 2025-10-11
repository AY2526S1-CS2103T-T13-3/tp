package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.logic.commands.StripCaptainCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new StripCaptainCommand object
 */
public class StripCaptainCommandParser implements Parser<StripCaptainCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StripCaptainCommand
     * and returns a StripCaptainCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StripCaptainCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StripCaptainCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER);

        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());
        return new StripCaptainCommand(playerName);
    }
}
