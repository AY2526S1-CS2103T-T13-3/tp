package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        String input = " " + PREFIX_TEAM + "U12";
        FilterByTeamPredicate expectedPredicate = new FilterByTeamPredicate("U12");
        FilterCommand expectedCommand =
                new FilterCommand(expectedPredicate, FilterByInjuryPredicate.ALWAYS_TRUE,
                        Optional.of("U12"), Optional.empty());
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String input = "U12"; // no tm/ prefix
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTeamName_throwsParseException() {
        String input = " " + PREFIX_TEAM + "U@16"; // invalid character
        assertParseFailure(parser, input, Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyTeamName_throwsParseException() {
        String input = " " + PREFIX_TEAM; // no team name
        assertParseFailure(parser, input, Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_preambleNotEmpty_throwsParseException() {
        String input = "xyz " + PREFIX_TEAM + "U12";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
