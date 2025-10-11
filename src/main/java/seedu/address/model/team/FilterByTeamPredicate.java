package seedu.address.model.team;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s team name matches the given team name (case-insensitive).
 */
public class FilterByTeamPredicate implements Predicate<Person> {
    public static final FilterByTeamPredicate ALWAYS_TRUE = new FilterByTeamPredicate("");
    private final String teamName;

    public FilterByTeamPredicate(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean test(Person person) {
        if (teamName.isEmpty()) {
            return true;
        }
        return StringUtil.containsWordIgnoreCase(person.getTeam().getName(), teamName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterByTeamPredicate)) {
            return false;
        }

        FilterByTeamPredicate otherFilterByTeamPredicate = (FilterByTeamPredicate) other;
        return teamName.equals(otherFilterByTeamPredicate.teamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("team name", teamName).toString();
    }

    public String getTeamName() {
        return teamName;
    }
}
