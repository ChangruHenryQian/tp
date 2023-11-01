package seedu.classmanager.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentNumber(null));
    }

    @Test
    public void constructor_invalidStudentNumber_throwsIllegalArgumentException() {
        String invalidStudentNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidStudentNumber));
    }

    @Test
    public void isValidStudentNumber() {
        // null student number
        assertThrows(NullPointerException.class, () -> StudentNumber.isValidStudentNumber(null));

        // invalid student number
        assertFalse(StudentNumber.isValidStudentNumber("")); // empty string
        assertFalse(StudentNumber.isValidStudentNumber("E0940230A")); // starts with non A

        // valid student numbers
        assertTrue(StudentNumber.isValidStudentNumber("A024392341A"));
        assertTrue(StudentNumber.isValidStudentNumber("A023819F")); // one character
        assertTrue(StudentNumber.isValidStudentNumber("A222222A")); // long classmanager
    }

    @Test
    public void equals() {
        StudentNumber studentNumber = new StudentNumber("A023819F");

        // same values -> returns true
        assertTrue(studentNumber.equals(new StudentNumber("A023819F")));

        // same object -> returns true
        assertTrue(studentNumber.equals(studentNumber));

        // null -> returns false
        assertFalse(studentNumber.equals(null));

        // different types -> returns false
        assertFalse(studentNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(studentNumber.equals(new StudentNumber("A023491A")));
    }
}
