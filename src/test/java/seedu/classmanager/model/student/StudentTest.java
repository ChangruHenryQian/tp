package seedu.classmanager.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;
import static seedu.classmanager.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.classmanager.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withStudentNumber(VALID_STUDENT_NUMBER_BOB).withClassDetails(VALID_CLASS_NUMBER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // same student number, all other attributes different -> returns true
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withName(VALID_NAME_BOB).withClassDetails(VALID_CLASS_NUMBER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different student number, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_AMY).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // student number differs in case, all other attributes same -> returns True
        Student editedBob = new StudentBuilder(BOB).withStudentNumber(VALID_STUDENT_NUMBER_BOB.toLowerCase())
                .build();
        assertTrue(BOB.isSameStudent(editedBob));

        // student number has trailing spaces, all other attributes same -> returns True
        String studentNumberWithTrailingSpaces = VALID_STUDENT_NUMBER_BOB + " ";
        editedBob = new StudentBuilder(BOB).withStudentNumber(studentNumberWithTrailingSpaces).build();
        assertTrue(BOB.isSameStudent(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different student number -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", student number=" + ALICE.getStudentNumber()
                + ", class number=" + ALICE.getClassDetails() + ", tags=" + ALICE.getTags() + ", comment="
                + ALICE.getComment() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
