package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Injury;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.position.Position;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final JsonAdaptedTeam team;
    private final JsonAdaptedPosition position;
    private final List<JsonAdaptedInjury> injuries = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final Boolean isCaptain;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("injuries") List<JsonAdaptedInjury> injuries,
                             @JsonProperty("team") JsonAdaptedTeam team,
                             @JsonProperty("position") JsonAdaptedPosition position,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("isCaptain") Boolean isCaptain) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.position = position;
        this.isCaptain = isCaptain;
        if (injuries != null) {
            this.injuries.addAll(injuries);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Convenience constructor matching legacy call sites that provide injuries but not captain status.
     * Delegates to the main constructor with {@code isCaptain} set to null.
     */
    public JsonAdaptedPerson(String name, String phone, String email, String address,
                             List<JsonAdaptedInjury> injuries, JsonAdaptedTeam team,
                             JsonAdaptedPosition position, List<JsonAdaptedTag> tags) {
        this(name, phone, email, address, injuries, team, position, tags, null);
    }

    /**
     * Backward-compatible constructor retained for tests and older code that do not pass captain status.
     * Delegates to the main constructor with {@code isCaptain} set to null so downstream defaults apply.
     */
    public JsonAdaptedPerson(String name, String phone, String email, String address,
                             String injuryName, JsonAdaptedTeam team,
                             JsonAdaptedPosition position, List<JsonAdaptedTag> tags) {
        List<JsonAdaptedInjury> injuriesList = new ArrayList<>();
        if (injuryName != null) {
            injuriesList.add(new JsonAdaptedInjury(injuryName));
        }
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.team = team;
        this.position = position;
        this.isCaptain = null;
        if (!injuriesList.isEmpty()) {
            this.injuries.addAll(injuriesList);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        team = new JsonAdaptedTeam(source.getTeam());
        position = new JsonAdaptedPosition(source.getPosition());
        isCaptain = source.isCaptain();
        injuries.addAll(source.getInjuries().stream()
                .map(JsonAdaptedInjury::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Injury> personInjuries = new ArrayList<>();
        for (JsonAdaptedInjury injury : injuries) {
            personInjuries.add(injury.toModelType());
        }

        // Ensure at least the default injury is present if the list is empty
        if (personInjuries.isEmpty()) {
            personInjuries.add(Person.DEFAULT_INJURY_STATUS);
        }

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (team == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Team.class.getSimpleName()));
        }
        final Team modelTeam = team.toModelType();

        final Position modelPosition = (position == null) ? new Position("NONE") : position.toModelType();

        final Set<Injury> modelInjuries = new HashSet<>(personInjuries);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final boolean modelIsCaptain = (isCaptain == null) ? Person.DEFAULT_CAPTAIN_STATUS : isCaptain;
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTeam, modelTags,
                modelPosition, modelInjuries, modelIsCaptain);
    }
}
