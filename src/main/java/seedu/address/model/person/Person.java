package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.position.Position;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Team team;
    private final Position position;
    private boolean isCaptain;

    /**
     * Creates a Person object, where isCaptain is false and with a team assigned.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Team team, Position position, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, team, position, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.position = position;
        this.tags.addAll(tags);
        this.isCaptain = false;
    }

    /**
     * Creates a Person object with specified captain status and with a team assigned.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Team team,
                  Position position, Set<Tag> tags, boolean isCaptain) {
        requireAllNonNull(name, phone, email, address, team, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.position = position;
        this.tags.addAll(tags);
        this.isCaptain = isCaptain;
    }

    /**
     * Backwards-compatible constructor defaulting position to NONE for legacy call sites.
     */
    public Person(Name name, Phone phone, Email email, Address address, Team team, Set<Tag> tags) {
        this(name, phone, email, address, team, new Position("NONE"), tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Team getTeam() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void makeCaptain() {
        this.isCaptain = true;
    }

    public void stripCaptain() {
        this.isCaptain = false;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && team.equals(otherPerson.team)
                && tags.equals(otherPerson.tags)
                && isCaptain == otherPerson.isCaptain();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, team);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("team", team)
                .add("isCaptain", isCaptain)
                .toString();
    }

}
