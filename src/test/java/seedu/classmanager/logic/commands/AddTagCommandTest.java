package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_SMART;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;
import seedu.classmanager.model.util.SampleDataUtil;
import seedu.classmanager.testutil.StudentBuilder;
import seedu.classmanager.testutil.TypicalStudents;


public class AddTagCommandTest {

    private final Model model = new ModelManager(new ClassManager(getTypicalClassManager()), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addTag_success() {
        Student taggedStudent = new StudentBuilder(TypicalStudents.ALICE)
            .withTags(VALID_TAG_FRIENDS, VALID_TAG_SMART).build();
        Set<Tag> tagToAdd = SampleDataUtil.getTagSet(VALID_TAG_SMART);
        AddTagCommand addTagCommand = new AddTagCommand(TypicalStudents.ALICE.getStudentNumber(),
            tagToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS,
            taggedStudent.getName()) + tagToAdd;

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), taggedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(taggedStudent.getTags(), model.getFilteredStudentList().get(0).getTags());
    }

    @Test
    public void execute_noStudentWithStudentNumber_failure() {
        AddTagCommand addTagCommand = new AddTagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), SampleDataUtil.getTagSet(VALID_TAG_SMART));

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_STUDENT_DOES_NOT_EXIST, commandHistory);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());

        AddTagCommand commandWithSameValue = new AddTagCommand(TypicalStudents.ALICE.getStudentNumber(),
            SampleDataUtil.getTagSet(VALID_TAG_FRIENDS));

        assertTrue(standardCommand.equals(commandWithSameValue));

        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        AddTagCommand addTagCommand = new AddTagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());
        String expected = AddTagCommand.class.getCanonicalName() + "{tags=" + TypicalStudents.ALICE.getTags() + "}";
        assertEquals(expected, addTagCommand.toString());
    }
}
