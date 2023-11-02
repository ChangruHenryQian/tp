package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
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

public class DeleteTagCommandTest {

    private final Model model = new ModelManager(new ClassManager(getTypicalClassManager()), new UserPrefs());
    private final CommandHistory commentHistory = new CommandHistory();

    @Test
    public void execute_deleteTag_success() {
        Student taggedStudent = new StudentBuilder(TypicalStudents.BENSON)
            .withTags(VALID_TAG_FRIENDS).build();
        Set<Tag> tagToDelete = SampleDataUtil.getTagSet("owesMoney");
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(
            TypicalStudents.BENSON.getStudentNumber(),
            tagToDelete);

        String expectedMessage = String.format(TagCommand.MESSAGE_DELETE_TAG_SUCCESS,
            taggedStudent.getName()) + tagToDelete;

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(1), taggedStudent);
        expectedModel.commitClassManager();

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel, commentHistory);
        assertEquals(taggedStudent.getTags(), model.getFilteredStudentList().get(1).getTags());
    }

    @Test
    public void execute_noStudentWithStudentNumber_failure() {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY),
            TypicalStudents.ALICE.getTags());

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_STUDENT_DOES_NOT_EXIST, commentHistory);
    }

    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());

        DeleteTagCommand commandWithSameValue = new DeleteTagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            SampleDataUtil.getTagSet(VALID_TAG_FRIENDS));

        assertTrue(standardCommand.equals(commandWithSameValue));

        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());
        String expected = DeleteTagCommand.class.getCanonicalName() + "{tags=" + TypicalStudents.ALICE.getTags() + "}";
        assertEquals(expected, deleteTagCommand.toString());
    }
}
