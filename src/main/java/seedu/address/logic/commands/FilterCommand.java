package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.FilterByInjuryPredicate;
import seedu.address.model.team.FilterByTeamPredicate;
import seedu.address.model.team.Team;

/**
 * Filters and lists all persons in address book whose team matches the argument team name.
 * Team name matching is case insensitive.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " [tm/TEAM_NAME] [i/INJURY] : Filters players by team and/or injury.";

    private final FilterByTeamPredicate teamPredicate;
    private final FilterByInjuryPredicate injuryPredicate;

    private final Optional<String> teamArg;
    private final Optional<String> injuryArg;

    /**
     * Creates a FilterCommand to filter the persons with the specified {@code teamPredicate}
     * and {@code injuryPredicate}
     */
    public FilterCommand(FilterByTeamPredicate teamPred, FilterByInjuryPredicate injuryPred,
            Optional<String> teamArg, Optional<String> injuryArg) {
        this.teamPredicate = requireNonNull(teamPred);
        this.injuryPredicate = requireNonNull(injuryPred);
        this.teamArg = requireNonNull(teamArg);
        this.injuryArg = requireNonNull(injuryArg);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<String> tArg = teamArg;
        Optional<String> iArg = injuryArg;

        if (tArg.isPresent()) {
            Team check = new Team(tArg.get());
            if (!model.hasTeam(check)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TEAM);
            }
        }

        model.updateFilteredPersonList(person ->
            teamPredicate.test(person) && injuryPredicate.test(person));

        int size = model.getFilteredPersonList().size();
        if (size == 0) {
            if (tArg.isPresent() && iArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_MATCHING_TEAM_OR_INJURY, tArg.get(), iArg.get()));
            } else if (tArg.isPresent()) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_IN_TEAM, tArg.get()));
            } else {
                throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PLAYERS_WITH_INJURY, iArg.get()));
            }
        }

        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FilterCommand)) {
            return false;
        }
        FilterCommand o = (FilterCommand) other;
        return Objects.equals(teamPredicate, o.teamPredicate)
            && Objects.equals(injuryPredicate, o.injuryPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamPredicate", teamPredicate)
                .add("injuryPredicate", injuryPredicate)
                .toString();
    }
}
