package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.NONEXISTENT_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MarkAbsentCommand.
 */
public class MarkAbsentCommandTest {

    private final Model model = new ModelManager(TypicalStudents.getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validStudentNumber_success() throws IllegalValueException, CommandException {
        Student studentToMark = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        Index i = Index.fromOneBased(ClassDetails.getTutorialCount());
        model.setSelectedStudent(studentToMark);

        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(i, studentToMark.getStudentNumber());

        String expectedMessage = MarkAbsentCommand.MESSAGE_MARK_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());
        Student markedStudent = studentToMark.copy();
        markedStudent.markAbsent(i);
        expectedModel.setStudent(studentToMark, markedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(markAbsentCommand, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(studentToMark, model.getSelectedStudent().get(0));
    }

    @Test
    public void execute_invalidTutorialIndex_throwsCommandException() {
        Student studentToMark = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        Index i = Index.fromZeroBased(ClassDetails.getTutorialCount() + 1);

        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(i, studentToMark.getStudentNumber());

        assertCommandFailure(
                markAbsentCommand, model,
                String.format(ClassDetails.MESSAGE_INVALID_TUTORIAL_INDEX, ClassDetails.getTutorialCount()),
                        commandHistory);
    }

    @Test
    public void execute_nonexistentStudentNumber_throwsCommandException() {
        MarkAbsentCommand markAbsentCommand = new MarkAbsentCommand(Index.fromOneBased(1),
                NONEXISTENT_STUDENT_NUMBER);

        assertCommandFailure(markAbsentCommand, model, Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER, commandHistory);
    }

    @Test
    public void equals() {
        Student firstStudent = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student secondStudent = TypicalStudents.getTypicalStudents().get(INDEX_SECOND_STUDENT.getZeroBased());
        MarkAbsentCommand markFirstStudentForFirstTutorial = new MarkAbsentCommand(Index.fromOneBased(1),
                firstStudent.getStudentNumber());
        MarkAbsentCommand markFirstStudentForSecondTutorial = new MarkAbsentCommand(Index.fromOneBased(2),
                firstStudent.getStudentNumber());
        MarkAbsentCommand markSecondStudentForFirstTutorial = new MarkAbsentCommand(Index.fromOneBased(1),
                secondStudent.getStudentNumber());
        MarkAbsentCommand markSecondStudentForSecondTutorial = new MarkAbsentCommand(Index.fromOneBased(2),
                secondStudent.getStudentNumber());

        // same object -> returns true
        assertTrue(markFirstStudentForFirstTutorial.equals(markFirstStudentForFirstTutorial));

        // null -> returns false
        assertFalse(markFirstStudentForFirstTutorial.equals(null));

        // different types -> returns false
        assertFalse(markSecondStudentForFirstTutorial.equals(1));

        // same student and different tutorial index -> returns false
        assertFalse(markFirstStudentForFirstTutorial.equals(markFirstStudentForSecondTutorial));

        // different student and same tutorial index -> returns false
        assertFalse(markFirstStudentForFirstTutorial.equals(markSecondStudentForFirstTutorial));

        // different student and different tutorial index -> returns false
        assertFalse(markFirstStudentForFirstTutorial.equals(markSecondStudentForSecondTutorial));
    }

}
