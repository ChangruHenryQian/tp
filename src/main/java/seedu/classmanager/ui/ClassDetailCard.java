package seedu.classmanager.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.classmanager.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class ClassDetailCard extends UiPart<Region> {

    private static final String FXML = "ClassDetailCard.fxml";

    private static final String DIVIDER = " ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox classDetail;
    @FXML
    private Label name;
    @FXML
    private Label classDetails;
    @FXML
    private Label attendance;
    @FXML
    private Label classParticipation;
    @FXML
    private Label assignments;
    @FXML
    private Label divider1;
    @FXML
    private Label divider2;
    @FXML
    private Label divider3;

    /**
     * Creates a {@code PStudentCode} with the given {@code Student} and index to display.
     */
    public ClassDetailCard(Student student) {
        super(FXML);
        this.student = student;
        name.setText(student.getName().fullName);
        classDetails.setText(student.getClassDetails().classNumber);
        attendance.setText(student.getClassDetails().attendanceTracker.toString());
        classParticipation.setText(student.getClassDetails().classParticipationTracker.toString());
        assignments.setText(student.getClassDetails().assignmentTracker.toString());
        divider1.setText(DIVIDER);
        divider2.setText(DIVIDER);
        divider3.setText(DIVIDER);
    }

}
