package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Teams should be shown to the user. */
    private final boolean showTeams;

    /** Persons should be shown to the user. */
    private final boolean showPersons;

    /** Positions should be shown to the user. */
    private final boolean showPositions;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isHelpVisible, boolean exit, boolean isTeamsVisible,
            boolean isPersonsVisible, boolean isPositionsVisible) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = isHelpVisible;
        this.exit = exit;
        this.showTeams = isTeamsVisible;
        this.showPersons = isPersonsVisible;
        this.showPositions = isPositionsVisible;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showHelp} and {@code exit}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and display teams in the UI.
     */
    public static CommandResult showTeamCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, true, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and displays persons in the UI.
     */
    public static CommandResult showPersonCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, false, true, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and displays positions in the UI.
     */
    public static CommandResult showPositionCommandResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, false, false, true);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowTeams() {
        return showTeams;
    }

    public boolean isShowPersons() {
        return showPersons;
    }

    public boolean isShowPositions() {
        return showPositions;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
