package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.classmanager.testutil.EditStudentDescriptorBuilder;


public class EditStudentDescriptorTest {


    @Test
    public void equals() {
        // same values -> returns true
        EditStudentDescriptor descriptorWithSameValues = new EditStudentDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditStudentDescriptor editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different student number -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withStudentNumber(VALID_STUDENT_NUMBER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different class number -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withClassNumber(VALID_CLASS_NUMBER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different comment -> returns true
        editedAmy = new EditStudentDescriptorBuilder(DESC_AMY).withComment(VALID_COMMENT_BOB).build();
    }

    @Test
    public void toStringMethod() {
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        String expected = EditStudentDescriptor.class.getCanonicalName() + "{name="
                + editStudentDescriptor.getName().orElse(null) + ", phone="
                + editStudentDescriptor.getPhone().orElse(null) + ", email="
                + editStudentDescriptor.getEmail().orElse(null) + ", student number="
                + editStudentDescriptor.getStudentNumber().orElse(null) + ", class number="
                + editStudentDescriptor.getClassNumber().orElse(null) + ", tags="
                + editStudentDescriptor.getTags().orElse(new HashSet<>())
                + ", comment=" + editStudentDescriptor.getComment().orElse(null) + "}";
        assertEquals(expected, editStudentDescriptor.toString());
    }
}
