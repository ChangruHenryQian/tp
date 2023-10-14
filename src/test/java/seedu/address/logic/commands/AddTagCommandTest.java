package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SMART;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;


public class AddTagCommandTest {

    private Model model = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());

    @Test
    public void execute_addTag_success() {
        Person taggedStudent = new PersonBuilder(TypicalPersons.ALICE)
            .withTags(VALID_TAG_FRIENDS, VALID_TAG_SMART).build();
        Set<Tag> tagToAdd = SampleDataUtil.getTagSet(VALID_TAG_SMART);
        AddTagCommand addTagCommand = new AddTagCommand(TypicalPersons.ALICE.getStudentNumber(),
            tagToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS,
            taggedStudent.getName()) + tagToAdd;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), taggedStudent);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
        assertEquals(taggedStudent.getTags(), model.getFilteredPersonList().get(0).getTags());
    }

    @Test
    public void execute_noStudentWithStudentNumber_failure() {
        AddTagCommand addTagCommand = new AddTagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), SampleDataUtil.getTagSet(VALID_TAG_SMART));

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_STUDENT_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(TypicalPersons.ALICE.getStudentNumber(),
            TypicalPersons.ALICE.getTags());

        AddTagCommand commandWithSameValue = new AddTagCommand(TypicalPersons.ALICE.getStudentNumber(),
            SampleDataUtil.getTagSet(VALID_TAG_FRIENDS));

        assertTrue(standardCommand.equals(commandWithSameValue));

        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        AddTagCommand addTagCommand = new AddTagCommand(
            TypicalPersons.ALICE.getStudentNumber(),
            TypicalPersons.ALICE.getTags());
        String expected = AddTagCommand.class.getCanonicalName() + "{tags=" + TypicalPersons.ALICE.getTags() + "}";
        assertEquals(expected, addTagCommand.toString());
    }
}
