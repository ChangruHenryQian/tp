package seedu.classmanager.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.classmanager.model.student.Student;

/**
 * A UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label studentNumber;
    @FXML
    private Label classDetails;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label comment;
    @FXML
    private BarChart<String, Double> barChart;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        studentNumber.setText(student.getStudentNumber().value);
        classDetails.setText(student.getClassDetails().classNumber);
        email.setText(student.getEmail().value);
        comment.setText(student.getComment().comment);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        barChart.getData().add(initBarChart(student));
    }

    /**
     * Initializes the class grades data in a barchart.
     *
     * @param student Student object to be converted in a barchart.
     *
     * @return XYChart.Series representing the data.
     */
    public XYChart.Series<String, Double> initBarChart(Student student) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Grades");
        series.getData().add(new XYChart.Data<>("Assignment marks",
                student.getClassDetails().getAssignmentPercentage()));
        series.getData().add(new XYChart.Data<>("Attendance",
                student.getClassDetails().getAttendancePercentage()));
        series.getData().add(new XYChart.Data<>("Class participation",
                student.getClassDetails().getClassParticipationPercentage()));
        return series;
    }
}
