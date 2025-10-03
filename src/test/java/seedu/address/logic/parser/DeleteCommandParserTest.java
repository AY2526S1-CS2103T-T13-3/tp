package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the DeleteCommand code.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // Valid player name
        Name personName = new Name(VALID_NAME_AMY);
        assertParseSuccess(parser, PLAYER_DESC_AMY, new DeleteCommand(personName));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsDeleteCommand() {
        // Valid player name with extra whitespace
        Name personName = new Name(VALID_NAME_AMY);
        assertParseSuccess(parser, PLAYER_DESC_AMY + "  ", new DeleteCommand(personName));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing pl/ prefix
        assertParseFailure(parser, VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPlayerName_throwsParseException() {
        // Missing player name after prefix
        assertParseFailure(parser, " " + PREFIX_PLAYER, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPlayerName_throwsParseException() {
        // Invalid name with special characters
        assertParseFailure(parser, INVALID_PLAYER_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Duplicate pl/ prefix
        assertParseFailure(parser, PLAYER_DESC_AMY + PLAYER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PLAYER));
    }

}
