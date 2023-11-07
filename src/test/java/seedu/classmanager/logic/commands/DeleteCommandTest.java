package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.NONEXISTENT_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private final Model model = new ModelManager(TypicalStudents.getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validStudentNumber_success() {
        Student studentToDelete = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(studentToDelete.getStudentNumber());
        model.setSelectedStudent(studentToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        ModelManager expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        expectedModel.commitClassManager();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, commandHistory);
        assertTrue(model.getSelectedStudent().isEmpty());
    }

    @Test
    public void execute_nonexistentStudentNumber_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(NONEXISTENT_STUDENT_NUMBER);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER, commandHistory);
    }

    //@@author Cikguseven-reused
    //Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
    // with minor modifications
    @Test
    public void executeUndoRedo_validStudentNumber_success() throws Exception {
        Student studentToDelete = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(studentToDelete.getStudentNumber());
        Model expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        expectedModel.commitClassManager();

        // delete -> first student deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts Class Manager back to previous state and filtered student list to show all students
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // redo -> same first student deleted again
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }

    @Test
    public void executeUndoRedo_nonexistentStudentNumber_failure() {
        DeleteCommand deleteCommand = new DeleteCommand(NONEXISTENT_STUDENT_NUMBER);

        // execution failed -> Class Manager state not added into model
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER, commandHistory);

        // single Class Manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE, commandHistory);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE, commandHistory);
    }

    /**
     * 1. Deletes a {@code Student} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted student in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStudentDeleted() throws Exception {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getOneBased());
        DeleteCommand deleteCommand = new DeleteCommand(studentToDelete.getStudentNumber());
        Model expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());

        expectedModel.deleteStudent(studentToDelete);
        expectedModel.commitClassManager();

        // delete -> deletes second student in unfiltered student list / first student in filtered student list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts Class Manager back to previous state and filtered student list to show all students
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        assertNotEquals(studentToDelete, model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()));
        // redo -> deletes same second student in unfiltered person list
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }
    //@@author

    @Test
    public void equals() {
        Student first = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student second = TypicalStudents.getTypicalStudents().get(INDEX_SECOND_STUDENT.getZeroBased());
        DeleteCommand deleteFirstCommand = new DeleteCommand(first.getStudentNumber());
        DeleteCommand deleteSecondCommand = new DeleteCommand(second.getStudentNumber());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(first.getStudentNumber());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Student target = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(target.getStudentNumber());
        String expected = DeleteCommand.class.getCanonicalName() + "{targetStudentNumber="
                + target.getStudentNumber() + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
