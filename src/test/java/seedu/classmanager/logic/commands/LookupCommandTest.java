package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;
import static seedu.classmanager.testutil.TypicalStudents.BENSON;
import static seedu.classmanager.testutil.TypicalStudents.DANIEL;
import static seedu.classmanager.testutil.TypicalStudents.HOON;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.StudentContainsKeywordsPredicate;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) for {@code LookupCommand}.
 */
public class LookupCommandTest {
    private final Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        StudentContainsKeywordsPredicate firstPredicate =
                new StudentContainsKeywordsPredicate("T11", null,
                        TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, null);
        StudentContainsKeywordsPredicate secondPredicate =
                new StudentContainsKeywordsPredicate("T11", null,
                        TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, "testTag");

        LookupCommand lookupFirstCommand = new LookupCommand(firstPredicate);
        LookupCommand lookupSecondCommand = new LookupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(lookupFirstCommand.equals(lookupFirstCommand));
        assertTrue(lookupSecondCommand.equals(lookupSecondCommand));

        // same values -> returns true
        LookupCommand lookupFirstCommandCopy = new LookupCommand(firstPredicate);
        LookupCommand lookupSecondCommandCopy = new LookupCommand(secondPredicate);
        assertTrue(lookupFirstCommand.equals(lookupFirstCommandCopy));
        assertTrue(lookupSecondCommand.equals(lookupSecondCommandCopy));

        // different types -> returns false
        assertFalse(lookupFirstCommand.equals(1));
        assertFalse(lookupSecondCommand.equals(1));

        // null -> returns false
        assertFalse(lookupFirstCommand.equals(null));
        assertFalse(lookupSecondCommand.equals(null));

        // different student -> returns false
        assertFalse(lookupFirstCommand.equals(lookupSecondCommand));
        assertFalse(lookupSecondCommand.equals(lookupFirstCommand));
    }

    /* Note, the underlying logics of StudentContainsKeywordsPredicate allow
       all students to be found if no keywords are provided. But the lookupCommandParser
       will not allow this to happen. */
    @Test
    public void execute_zeroKeywords_allStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW,
                expectedModel.getFilteredStudentList().size());
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(null,
                null, null, null, null, null);
        LookupCommand command = new LookupCommand(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_singlePrefix_multipleStudentsFound() {
        expectedModel.addStudent(HOON);
        model.addStudent(HOON);
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(null,
                null, TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, null);
        LookupCommand command = new LookupCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(Arrays.asList(BENSON, DANIEL, HOON), model.getFilteredStudentList());
        expectedModel.deleteStudent(HOON);
        model.deleteStudent(HOON);
    }

    @Test
    public void execute_multiplePrefixes_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate("T11 T12 T9",
                null, null, null, null, "friends");
        LookupCommand command = new LookupCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredStudentList());

        expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        predicate = new StudentContainsKeywordsPredicate(null,
                null, TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, "friends");
        command = new LookupCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredStudentList());
    }

    @Test
    public void execute_nonExistentKeywords_noStudentsFound() {
        // single prefix
        String expectedMessage = LookupCommand.MESSAGE_NO_MATCH;
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate("T9999",
                null, null, null, null, null);
        LookupCommand command = new LookupCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());

        // multiple prefixes
        predicate = new StudentContainsKeywordsPredicate("T11",
                null, "thisIsATestName", null, null, "tagTagTag");
        command = new LookupCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void toStringMethod() {
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate("T11",
                null, TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, null);
        LookupCommand lookupCommand = new LookupCommand(predicate);
        String expected = LookupCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, lookupCommand.toString());
    }

    @Test
    public void test_hashCode() {
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate("T11",
                null, TypicalStudents.KEYWORD_MATCHING_MEIER, null, null, null);
        LookupCommand lookupCommand = new LookupCommand(predicate);
        assertEquals(lookupCommand.hashCode(), lookupCommand.hashCode());
    }
}
