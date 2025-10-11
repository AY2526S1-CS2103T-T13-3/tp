package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.team.FilterByTeamPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM, PREFIX_INJURY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM, PREFIX_INJURY);

        boolean hasTeam = argMultimap.getValue(PREFIX_TEAM).isPresent();
        boolean hasInjury = argMultimap.getValue(PREFIX_INJURY).isPresent();

        if (hasTeam) {
            ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
        }

        if (hasInjury) {
            ParserUtil.parseInjury(argMultimap.getValue(PREFIX_INJURY).get());
        }

        if ((!hasTeam && !hasInjury) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        FilterByTeamPredicate teamPred = argMultimap.getValue(PREFIX_TEAM)
                .map(name -> new FilterByTeamPredicate(name))
                .orElse(FilterByTeamPredicate.ALWAYS_TRUE);
        FilterByInjuryPredicate injuryPred = argMultimap.getValue(PREFIX_INJURY)
                .map(inj -> new FilterByInjuryPredicate(inj))
                .orElse(FilterByInjuryPredicate.ALWAYS_TRUE);

        return new FilterCommand(teamPred, injuryPred, argMultimap.getValue(PREFIX_TEAM),
                argMultimap.getValue(PREFIX_INJURY));
    }
}
