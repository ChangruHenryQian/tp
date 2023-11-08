package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.NONEXISTENT_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.EditStudentDescriptorBuilder;
import seedu.classmanager.testutil.StudentBuilder;
import seedu.classmanager.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private final Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified_success() {
        Student studentToEdit = TypicalStudents.ALICE;
        Student editedStudent = new StudentBuilder().withTags("friends").build();
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(studentToEdit.getStudentNumber(), descriptor);
        model.setSelectedStudent(studentToEdit);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(studentToEdit, editedStudent);
        expectedModel.setSelectedStudent(editedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(expectedModel.getSelectedStudent().get(0), model.getSelectedStudent().get(0));
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Student studentToEdit = TypicalStudents.BENSON;

        StudentBuilder studentInList = new StudentBuilder(studentToEdit);
        Student editedStudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(studentToEdit.getStudentNumber(), descriptor);
        model.resetSelectedStudent();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(studentToEdit, editedStudent);
        expectedModel.resetSelectedStudent();
        expectedModel.commitClassManager();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
        assertTrue(model.getSelectedStudent().isEmpty());
    }

    @Test
    public void execute_noFieldSpecified_success() {
        Student studentToEdit = TypicalStudents.BENSON;
        EditCommand editCommand = new EditCommand(studentToEdit.getStudentNumber(),
                new EditCommand.EditStudentDescriptor());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(studentToEdit));

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.commitClassManager();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_nonexistentStudentNumber_throwsCommandException() {
        EditCommand editCommand = new EditCommand(NONEXISTENT_STUDENT_NUMBER,
                new EditCommand.EditStudentDescriptor());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER, commandHistory);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB), DESC_AMY);

        // same values -> returns true
        EditCommand.EditStudentDescriptor copyDescriptor = new EditCommand.EditStudentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB),
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different student number -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        String studentNumber = VALID_STUDENT_NUMBER_BOB;
        EditCommand.EditStudentDescriptor editStudentDescriptor = new EditCommand.EditStudentDescriptor(DESC_AMY);
        EditCommand editCommand = new EditCommand(new StudentNumber(studentNumber), editStudentDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{student number=" + studentNumber
                + ", editStudentDescriptor=" + editStudentDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
