package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RandomCommand.
 */
public class RandomCommandTest {

    private final Model model = new ModelManager(TypicalStudents.getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        Student studentToSelect = TypicalStudents.getTypicalStudents().get(INDEX_FIRST_STUDENT.getZeroBased());
        model.updateFilteredStudentList(s -> s.equals(studentToSelect));

        RandomCommand randomCommand = new RandomCommand(1);

        String expectedMessage = RandomCommand.MESSAGE_RANDOM_SUCCESS_SINGLE_STUDENT
                + studentToSelect.getName() + " " + studentToSelect.getStudentNumber() + "\n";

        assertCommandSuccess(randomCommand, model, expectedMessage, model, commandHistory);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // input that is larger than students displayed
        int largeInvalid = TypicalStudents.getTypicalStudents().size() + 1;
        RandomCommand invalidRandomCommand = new RandomCommand(largeInvalid);
        assertCommandFailure(invalidRandomCommand, model,
            RandomCommand.MESSAGE_INVALID_NUM_OF_STUDENTS, commandHistory);

        // input that is negative
        RandomCommand negativeRandomCommand = new RandomCommand(-1);
        assertCommandFailure(negativeRandomCommand, model,
            RandomCommand.MESSAGE_INVALID_NUM_OF_STUDENTS, commandHistory);
    }

    @Test
    public void generateRandomInt_success() {
        int length = 1;
        int upper = 1;
        Integer[] actual = RandomCommand.generateRandomInt(length, upper);
        assertEquals(upper - 1, actual[0]);
    }

    @Test
    public void equals() {
        RandomCommand randomOne = new RandomCommand(1);
        RandomCommand randomTwo = new RandomCommand(2);

        // same object -> returns true
        assertTrue(randomOne.equals(randomOne));

        // null -> returns false
        assertFalse(randomOne.equals(null));

        // different types -> returns false
        assertFalse(randomOne.equals(1));

        // different number to be selected
        assertFalse(randomOne.equals(randomTwo));
    }

}
