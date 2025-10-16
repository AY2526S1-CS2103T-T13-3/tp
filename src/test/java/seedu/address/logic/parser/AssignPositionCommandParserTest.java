package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.PLAYER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_FW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_FW;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignPositionCommandParserTest {
    private final AssignPositionCommandParser parser = new AssignPositionCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        AssignPositionCommand cmd = parser.parse(PLAYER_DESC_AMY + POSITION_DESC_AMY);
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_validArgsWithWhitespace_success() throws Exception {
        AssignPositionCommand cmd = parser.parse("  pl/" + VALID_NAME_AMY + "  ps/" + VALID_POSITION_FW + "  ");
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_missingPlayerFlag_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_MISSING_PLAYER_FLAG, () ->
                parser.parse(POSITION_DESC_AMY));
    }

    @Test
    public void parse_missingPositionFlag_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_MISSING_POSITION_FLAG, () ->
                parser.parse(PLAYER_DESC_AMY));
    }

    @Test
    public void parse_bothFlagsMissing_failure() {
        // When both flags missing, parser checks pl/ first
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_MISSING_PLAYER_FLAG, () ->
                parser.parse(" " + VALID_NAME_AMY + " " + VALID_POSITION_FW));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(""));
    }

    @Test
    public void parse_nullArgs_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(null));
    }

    @Test
    public void parse_emptyPlayerName_failure() {
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(" pl/" + POSITION_DESC_FW));
    }

    @Test
    public void parse_emptyPositionName_failure() {
        // Empty position name fails to parse due to regex requiring \S+
        assertThrows(ParseException.class, AssignPositionCommand.MESSAGE_INVALID_FORMAT, () ->
                parser.parse(PLAYER_DESC_AMY + " ps/"));
    }

    @Test
    public void parse_playerNameWithSpaces_success() throws Exception {
        AssignPositionCommand cmd = parser.parse(" pl/" + VALID_NAME_AMY + POSITION_DESC_FW);
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_caseInsensitiveFlags_success() throws Exception {
        // Flags should be case-insensitive due to (?i) in regex
        AssignPositionCommand cmd = parser.parse(" PL/" + VALID_NAME_AMY + " PS/" + VALID_POSITION_FW);
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }

    @Test
    public void parse_preambleAccepted_success() throws Exception {
        // Preamble is accepted by the regex (.*)
        AssignPositionCommand cmd = parser.parse(" preamble" + PLAYER_DESC_AMY + POSITION_DESC_FW);
        assertEquals(AssignPositionCommand.class, cmd.getClass());
    }
}


