package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.commands.AssignInjuryCommand;
import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePositionCommand;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCaptainCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListInjuredCommand;
import seedu.address.logic.commands.ListPositionCommand;
import seedu.address.logic.commands.ListTeamCommand;
import seedu.address.logic.commands.MakeCaptainCommand;
import seedu.address.logic.commands.NewPositionCommand;
import seedu.address.logic.commands.StripCaptainCommand;
import seedu.address.logic.commands.UnassignInjuryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTeamCommand.COMMAND_WORD:
            return new AddTeamCommandParser().parse(arguments);

        case AssignTeamCommand.COMMAND_WORD:
            return new AssignTeamCommandParser().parse(arguments);

        case DeleteTeamCommand.COMMAND_WORD:
            return new DeleteTeamCommandParser().parse(arguments);

        case NewPositionCommand.COMMAND_WORD:
            return new NewPositionCommandParser().parse(arguments);

        case DeletePositionCommand.COMMAND_WORD:
            return new DeletePositionCommandParser().parse(arguments);

        case AssignPositionCommand.COMMAND_WORD:
            return new AssignPositionCommandParser().parse(arguments);

        case ListTeamCommand.COMMAND_WORD:
            return new ListTeamCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case AssignInjuryCommand.COMMAND_WORD:
            return new AssignInjuryCommandParser().parse(arguments);

        case UnassignInjuryCommand.COMMAND_WORD:
            return new UnassignInjuryCommandParser().parse(arguments);

        case FilterCaptainCommand.COMMAND_WORD:
            return new FilterCaptainCommand();

        case StripCaptainCommand.COMMAND_WORD:
            return new StripCaptainCommandParser().parse(arguments);

        case MakeCaptainCommand.COMMAND_WORD:
            return new MakeCaptainCommandParser().parse(arguments);

        case ListPositionCommand.COMMAND_WORD:
            return new ListPositionCommand();

        case ListInjuredCommand.COMMAND_WORD:
            return new ListInjuredCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
