package seedu.address.model.person;

import static seedu.address.model.person.Person.DEFAULT_INJURY_STATUS;

import java.util.function.Predicate;

/**
 * Tests that a Person’s injury is not the default.
 */
public class FilterInjuredPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return !person.getInjury().getInjuryName().equals(DEFAULT_INJURY_STATUS);
    }
}
