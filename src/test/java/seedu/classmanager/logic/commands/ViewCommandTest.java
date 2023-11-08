package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.NONEXISTENT_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.TypicalStudents;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private final Model model = new ModelManager(new ClassManager(getTypicalClassManager()), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validStudentNumber_success() {
        Student studentToView = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(studentToView.getStudentNumber());

        String expectedMessage = String.format(ViewCommand.MESSAGE_COMMAND_SUCCESS,
            studentToView.getName());

        ModelManager expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());
        expectedModel.setSelectedStudent(studentToView);
        expectedModel.commitClassManager();

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_nonExistentStudentNumber_failure() {
        ViewCommand viewCommand = new ViewCommand(NONEXISTENT_STUDENT_NUMBER);

        assertCommandFailure(viewCommand, model, ViewCommand.MESSAGE_COMMAND_FAILURE, commandHistory);
    }

    @Test
    public void equalsMethod() {
        ViewCommand viewCommand = new ViewCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY));
        ViewCommand commandWithSameValue = new ViewCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY));
        ViewCommand commandWithDifferentValue = new ViewCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB));

        // same object -> returns true
        assertTrue(viewCommand.equals(viewCommand));

        // same value -> returns true
        assertTrue(viewCommand.equals(commandWithSameValue));

        // different value -> returns false
        assertFalse(viewCommand.equals(commandWithDifferentValue));

        // null value -> returns false
        assertFalse(viewCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Student target = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(target.getStudentNumber());
        String expected = ViewCommand.class.getCanonicalName() + "{studentNumber="
            + target.getStudentNumber() + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
