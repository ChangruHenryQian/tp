package seedu.classmanager.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.model.student.Student;

/**
 * Pane containing the selected student.
 */
public class ClassDetailBox extends UiPart<Region> {
    private static final String FXML = "ClassDetailBox.fxml";
    private final Logger logger = LogsCenter.getLogger(ClassDetailBox.class);

    @FXML
    private ListView<Student> classDetailBox;

    /**
     * Creates a {@code ClassDetailBox} with the given {@code ObservableList}.
     */
    public ClassDetailBox(ObservableList<Student> list) {
        super(FXML);
        classDetailBox.setItems(list);
        classDetailBox.setCellFactory(items -> new ClassDetailCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of {@code ClassDetails} using a {@code ClassDetailCard}.
     */
    class ClassDetailCell extends ListCell<Student> {
        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClassDetailCard(student).getRoot());
            }
        }
    }

}
