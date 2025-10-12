package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane injuryStatus;
    @FXML
    private FlowPane teamContainer;
    @FXML
    private FlowPane positionContainer;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        Label teamTag = new Label(person.getTeam().getName());
        teamTag.getStyleClass().add("team-tag");
        teamContainer.getChildren().add(teamTag);
        Label positionTag = new Label(person.getPosition().getName());
        positionTag.getStyleClass().add("team-tag");
        positionContainer.getChildren().add(positionTag);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Label injuryLabel = new Label(person.getInjury().getInjuryName());
        if (person.getInjury().getInjuryName().equals("FIT")) {
            injuryLabel.getStyleClass().add("fit-tag");
        } else {
            injuryLabel.getStyleClass().add("injured-tag");
        }
        injuryStatus.getChildren().add(injuryLabel);
    }
}
