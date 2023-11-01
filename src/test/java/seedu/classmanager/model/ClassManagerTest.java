package seedu.classmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.exceptions.DuplicateStudentException;
import seedu.classmanager.testutil.StudentBuilder;

public class ClassManagerTest {

    private final ClassManager classManager = new ClassManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), classManager.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> classManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClassManager_replacesData() {
        ClassManager newData = getTypicalClassManager();
        classManager.resetData(newData);
        assertEquals(newData, classManager);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withClassDetails(VALID_CLASS_NUMBER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        ClassManagerStub newData = new ClassManagerStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> classManager.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> classManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInClassManager_returnsFalse() {
        assertFalse(classManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInClassManager_returnsTrue() {
        classManager.addStudent(ALICE);
        assertTrue(classManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInClassManager_returnsTrue() {
        classManager.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withClassDetails(VALID_CLASS_NUMBER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(classManager.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> classManager.getStudentList().remove(0));
    }

    @Test
    public void setSelectedStudent_setStudent_success() {
        classManager.setSelectedStudent(ALICE);
        assertEquals(ALICE, classManager.getSelectedStudent().get(0));
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        classManager.addListener(listener);
        classManager.addStudent(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        classManager.addListener(listener);
        classManager.removeListener(listener);
        classManager.addStudent(ALICE);
        assertEquals(0, counter.get());
    }

    @Test
    public void equalsMethod() {
        ClassManager testClassManager = new ClassManager();

        // same object -> returns true
        assertTrue(classManager.equals(classManager));

        // same value -> returns true
        assertTrue(classManager.equals(testClassManager));

        // null -> return false
        assertFalse(classManager.equals(null));

        testClassManager.addStudent(ALICE);
        testClassManager.setSelectedStudent(ALICE);

        // different value -> returns false;
        assertFalse(classManager.equals(testClassManager));
    }

    @Test
    public void toStringMethod() {
        String expected = ClassManager.class.getCanonicalName() + "{students=" + classManager.getStudentList() + "}";
        assertEquals(expected, classManager.toString());
    }

    /**
     * A stub ReadOnlyClassManager whose students list can violate interface constraints.
     */
    private static class ClassManagerStub implements ReadOnlyClassManager {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        private final ObservableList<Student> selectedStudent = FXCollections.observableArrayList();

        ClassManagerStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Student> getSelectedStudent() {
            return selectedStudent;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
