package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.ReadOnlyUserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.StudentBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddCommand(validStudent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STUDENT, () ->
                addCommand.execute(modelStub, commandHistory));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withStudentNumber("A0412798N").build();
        Student bob = new StudentBuilder().withStudentNumber("A0939498M").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignmentCount(int assignmentCount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorialCount(int tutorialCount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void toggleColourTheme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTheme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getClassManagerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClassManagerFilePath(Path classManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClassManager(ReadOnlyClassManager classManager) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClassManager getClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getObservableSelectedStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSelectedStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author Cikguseven-reused
        //Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
        // with minor modifications
        @Override
        public boolean canUndoClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitClassManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void loadReset(ReadOnlyClassManager classManager) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void configReset() {
            throw new AssertionError("This method should not be called.");
        }
        //@@author
        @Override
        public void resetSelectedStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Student getStudent(StudentNumber studentNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Student getSelectedStudent() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyClassManager getClassManager() {
            return new ClassManager();
        }

        @Override
        public void commitClassManager() {
            // called by {@code AddCommand#execute()}
        }
    }
}
