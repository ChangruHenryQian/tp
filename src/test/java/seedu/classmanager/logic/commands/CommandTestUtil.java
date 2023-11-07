package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.EditStudentDescriptorBuilder;
import seedu.classmanager.testutil.NameContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_STUDENT_NUMBER_AMY = "A0218510F";
    public static final String VALID_STUDENT_NUMBER_BOB = "A0123819A";
    public static final String VALID_CLASS_NUMBER_AMY = "T11";
    public static final String VALID_CLASS_NUMBER_BOB = "T12";
    public static final String INVALID_STUDENT_NUMBER = "B2103818N";
    public static final String VALID_COMMENT_BOB = "Bad student";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_FRIENDS = "friends";
    public static final String VALID_TAG_SMART = "smart";
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String STUDENT_NUMBER_DESC_AMY = " " + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_AMY;
    public static final String STUDENT_NUMBER_DESC_BOB = " " + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_BOB;
    public static final String CLASS_NUMBER_DESC_AMY = " " + PREFIX_CLASS_NUMBER + VALID_CLASS_NUMBER_AMY;
    public static final String CLASS_NUMBER_DESC_BOB = " " + PREFIX_CLASS_NUMBER + VALID_CLASS_NUMBER_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final int TEST_FIRST_TUTORIAL = 1;
    public static final String TEST_FIRST_TUTORIAL_DESC = " " + PREFIX_TUTORIAL_INDEX + TEST_FIRST_TUTORIAL;
    public static final int TEST_DEFAULT_TUTORIAL = ClassDetails.getTutorialCount();
    public static final String TEST_DEFAULT_TUTORIAL_DESC = " " + PREFIX_TUTORIAL_INDEX + TEST_DEFAULT_TUTORIAL;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_STUDENT_NUMBER_DESC = " " + PREFIX_STUDENT_NUMBER + INVALID_STUDENT_NUMBER;
    // Student number should start with A
    public static final String INVALID_CLASS_NUMBER_DESC = " " + PREFIX_CLASS_NUMBER + "11";
    // Class number should start with T
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;
    public static final StudentNumber NONEXISTENT_STUDENT_NUMBER = new StudentNumber("A0000000A");

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                .withClassNumber(VALID_CLASS_NUMBER_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withStudentNumber(VALID_STUDENT_NUMBER_BOB)
                .withClassNumber(VALID_CLASS_NUMBER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, CommandHistory commandHistory) {
        try {
            CommandResult result = command.execute(actualModel, commandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model, CommandHistory)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel, CommandHistory actualCommandHistory) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel, actualCommandHistory);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Class Manager, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            CommandHistory actualCommandHistory) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ClassManager expectedClassManager = new ClassManager(actualModel.getClassManager());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, actualCommandHistory));
        assertEquals(expectedClassManager, actualModel.getClassManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s Class Manager.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Deletes the first student in {@code model}'s filtered list from {@code model}'s Class Manager.
     */
    public static void deleteFirstStudent(Model model) {
        Student firstStudent = model.getFilteredStudentList().get(0);
        model.deleteStudent(firstStudent);
        model.commitClassManager();
    }
}
