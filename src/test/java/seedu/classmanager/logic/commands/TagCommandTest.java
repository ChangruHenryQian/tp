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

import java.util.HashSet;
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


/**
 * Contains integration tests (interaction with the Model) and unit tests for TagCommand.
 */
public class TagCommandTest {

    private final Model model = new ModelManager(new ClassManager(getTypicalClassManager()), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_replaceAllTag_success() {
        Student studentToTag = new StudentBuilder(TypicalStudents.ALICE)
            .withTags(VALID_TAG_SMART, VALID_TAG_FRIENDS).build();
        Set<Tag> replacedTags = SampleDataUtil.getTagSet(VALID_TAG_SMART, VALID_TAG_FRIENDS);
        TagCommand tagCommand = new TagCommand(
            studentToTag.getStudentNumber(),
            replacedTags);

        String expectedMessage = String.format(TagCommand.MESSAGE_REPLACE_ALL_TAG_SUCCESS,
                studentToTag.getName()) + replacedTags;

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), studentToTag);
        expectedModel.commitClassManager();

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_deleteAllTag_success() {
        Student studentToRemoveTag = new StudentBuilder(TypicalStudents.CARL).build();
        Set<Tag> emptyTag = new HashSet<>();
        TagCommand tagCommand = new TagCommand(
            studentToRemoveTag.getStudentNumber(),
            emptyTag);

        String expectedMessage = String.format(TagCommand.MESSAGE_DELETE_ALL_TAG_SUCCESS,
                studentToRemoveTag.getName()) + emptyTag;

        Model expectedModel = new ModelManager(new ClassManager(model.getClassManager()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(2), studentToRemoveTag);
        expectedModel.commitClassManager();

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_noStudentWithStudentNumber_failure() {
        TagCommand tagCommand = new TagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY),
            TypicalStudents.ALICE.getTags());

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_STUDENT_DOES_NOT_EXIST, commandHistory);
    }

    @Test
    public void equals() {
        final TagCommand standardCommand = new TagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());

        TagCommand commandWithSameValue = new TagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            SampleDataUtil.getTagSet(VALID_TAG_FRIENDS));

        assertTrue(standardCommand.equals(commandWithSameValue));

        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        TagCommand tagCommand = new TagCommand(
            TypicalStudents.ALICE.getStudentNumber(),
            TypicalStudents.ALICE.getTags());
        String expected = TagCommand.class.getCanonicalName() + "{tags=" + TypicalStudents.ALICE.getTags() + "}";
        assertEquals(expected, tagCommand.toString());
    }
}
