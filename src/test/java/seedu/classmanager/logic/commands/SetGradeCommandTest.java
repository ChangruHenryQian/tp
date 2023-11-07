package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.StudentBuilder;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetGradeCommand.
 */
public class SetGradeCommandTest {

    private final Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder(TypicalStudents.ALICE)
                .withAssignmentDetails(Index.fromOneBased(1), 100)
                .build();
        model.setSelectedStudent(editedStudent);
        StudentNumber studentNumber = editedStudent.getStudentNumber();
        SetGradeCommand setGradeCommand = new SetGradeCommand(studentNumber, Index.fromOneBased(1), 100);

        String expectedMessage = String.format(SetGradeCommand.MESSAGE_SUCCESS, editedStudent.getStudentNumber())
                + editedStudent.getClassDetails().displayAssignments();

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(model.getStudent(editedStudent.getStudentNumber()), editedStudent);
        expectedModel.setSelectedStudent(editedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(setGradeCommand, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(editedStudent, model.getSelectedStudent().get(0));
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList)
                .withAssignmentDetails(Index.fromOneBased(1), 50)
                .build();
        SetGradeCommand setGradeCommand = new SetGradeCommand(editedStudent.getStudentNumber(),
                Index.fromOneBased(1), 50);

        String expectedMessage = String.format(SetGradeCommand.MESSAGE_SUCCESS, editedStudent.getStudentNumber())
                + editedStudent.getClassDetails().displayAssignments();

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);
        expectedModel.setStudent(model.getStudent(editedStudent.getStudentNumber()), editedStudent);
        expectedModel.setSelectedStudent(editedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(setGradeCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_studentDoesNotExist_failure() {
        Student ida = TypicalStudents.IDA;
        assertFalse(model.hasStudent(ida));
        SetGradeCommand setGradeCommand = new SetGradeCommand(ida.getStudentNumber(),
                Index.fromOneBased(1), 100);

        assertCommandFailure(setGradeCommand, model, Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER, commandHistory);
    }

    @Test
    public void equals() {
        final SetGradeCommand standardCommand = new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY), Index.fromOneBased(1), 100);

        // same values -> returns true
        SetGradeCommand commandWithSameValues = new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY), Index.fromOneBased(1), 100);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different assignment -> returns false
        assertFalse(standardCommand.equals(new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY), Index.fromOneBased(2), 100)));

        // different grade -> returns false
        assertFalse(standardCommand.equals(new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY), Index.fromOneBased(1), 50)));

        // different student number -> returns false
        assertFalse(standardCommand.equals(new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_BOB), Index.fromOneBased(1), 50)));
    }

    @Test
    public void toStringMethod() {
        SetGradeCommand setGradeCommand = new SetGradeCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY), Index.fromOneBased(1), 50);

        String expected = SetGradeCommand.class.getCanonicalName()
                + "{studentNumber=" + VALID_STUDENT_NUMBER_AMY + ", "
                + "assignmentIndex=seedu.classmanager.commons.core.index.Index{zeroBasedIndex=0}, grade=50}";
        assertEquals(expected, setGradeCommand.toString());
    }
}
