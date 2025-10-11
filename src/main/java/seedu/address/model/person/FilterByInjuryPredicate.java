package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s injury matches the given injury keyword (case-insensitive).
 */
public class FilterByInjuryPredicate implements Predicate<Person> {
    public static final FilterByInjuryPredicate ALWAYS_TRUE = new FilterByInjuryPredicate("");
    private final String keyword;

    /**
     * Constructs a {@code FilterByInjuryPredicate}.
     *
     * @param keyword A keyword to filter by.
     */
    public FilterByInjuryPredicate(String keyword) {
        requireNonNull(keyword);
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        if (keyword.isEmpty()) {
            return true;
        }
        return StringUtil.containsWordIgnoreCase(person.getInjury().getInjuryName(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FilterByInjuryPredicate)) {
            return false;
        }
        return keyword.equals(((FilterByInjuryPredicate) other).keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("injury keyword", keyword).toString();
    }

    public String getKeyword() {
        return keyword;
    }
}
