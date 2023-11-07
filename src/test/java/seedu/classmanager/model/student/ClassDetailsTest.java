package seedu.classmanager.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.classmanager.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.student.information.AssignmentTracker;
import seedu.classmanager.model.student.information.AttendanceTracker;
import seedu.classmanager.model.student.information.ClassParticipationTracker;

public class ClassDetailsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassDetails(null));
        assertThrows(NullPointerException.class, () -> new ClassDetails(null, null, null, null));
        assertThrows(NullPointerException.class, () -> new ClassDetails("T11",
                new AttendanceTracker(1), null, null));
        assertThrows(NullPointerException.class, () -> new ClassDetails("T11",
                new AttendanceTracker(1),
                new AssignmentTracker(1), null));
    }

    @Test
    public void constructor_invalidClassDetails_throwsIllegalArgumentException() {
        String invalidClassDetails = "11";
        assertThrows(IllegalArgumentException.class, () -> new ClassDetails(invalidClassDetails));
        assertThrows(IllegalArgumentException.class, () -> new ClassDetails(invalidClassDetails,
                new AttendanceTracker(1),
                new AssignmentTracker(1),
                new ClassParticipationTracker(1)));
    }

    @Test
    public void constructor_validClassDetails_success() {
        String invalidClassDetails = "T11";
        new ClassDetails(invalidClassDetails);
        new ClassDetails(invalidClassDetails,
                new AttendanceTracker(1),
                new AssignmentTracker(1),
                new ClassParticipationTracker(1));
    }

    @Test
    public void isValidClassDetails() {
        // null class number
        assertThrows(NullPointerException.class, () -> ClassDetails.isValidClassDetails(null));

        // invalid class number
        assertFalse(ClassDetails.isValidClassDetails("")); // empty string
        assertFalse(ClassDetails.isValidClassDetails("11")); // doesn't start with T

        // valid class numbers
        assertTrue(ClassDetails.isValidClassDetails("T01"));
        assertTrue(ClassDetails.isValidClassDetails("T11"));
        assertTrue(ClassDetails.isValidClassDetails("T02"));
    }

    @Test
    public void markAttendancePresent_invalidValues_exceptionThrown() {
        ClassDetails classDetails = new ClassDetails("T11");
        assertThrows(CommandException.class, () -> classDetails.markPresent(Index.fromOneBased(14)));
        assertThrows(IndexOutOfBoundsException.class, () -> classDetails.markPresent(Index.fromOneBased(0)));
    }

    @Test
    public void markAttendanceAbsent_invalidValues_exceptionThrown() {
        ClassDetails classDetails = new ClassDetails("T11");
        assertThrows(CommandException.class, () -> classDetails.markAbsent(Index.fromOneBased(14)));
        assertThrows(IndexOutOfBoundsException.class, () -> classDetails.markAbsent(Index.fromOneBased(0)));
    }

    @Test
    public void setAssignmentGrade_invalidValues_exceptionThrown() {
        ClassDetails classDetails = new ClassDetails("T11");
        assertThrows(CommandException.class, () -> classDetails.setGrade(Index.fromOneBased(7), 0));
        assertThrows(CommandException.class, () -> classDetails.setGrade(Index.fromOneBased(1), -1));
        assertThrows(CommandException.class, () -> classDetails.setGrade(Index.fromOneBased(1), 200));
    }

    @Test
    public void setAssignmentGrade_validValues_success() {
        ClassDetails classDetails = new ClassDetails("T11");
        try {
            classDetails.setGrade(Index.fromOneBased(3), 0);
            classDetails.setGrade(Index.fromOneBased(1), 100);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void recordClassParticipation_invalidValues_exceptionThrown() {
        ClassDetails classDetails = new ClassDetails("T11");
        assertThrows(CommandException.class, ()
                -> classDetails.recordClassParticipation(Index.fromOneBased(14), true));
        assertThrows(CommandException.class, ()
                -> classDetails.recordClassParticipation(Index.fromOneBased(14), false));
    }

    @Test
    public void recordClassParticipation_validValues_success() {
        ClassDetails classDetails = new ClassDetails("T11");
        try {
            classDetails.recordClassParticipation(Index.fromOneBased(1), true);
            classDetails.recordClassParticipation(Index.fromOneBased(10), false);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void getAttendancePercentage_validValues_success() {
        ClassDetails classDetails = new ClassDetails("T11");
        try {
            ClassDetails.setTutorialCount(10);
            classDetails.markPresent(Index.fromOneBased(1));
            classDetails.markPresent(Index.fromOneBased(3));
            classDetails.markPresent(Index.fromOneBased(6));
            assertEquals(30, classDetails.getAttendancePercentage());
        } catch (Exception e) {
            fail();
        }
        ClassDetails.setTutorialCount(13);
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If no tutorial intialized, it should return the default value of 100.
     */
    @Test void getAttendancePercentage_noTutorials_success() {
        ClassDetails classDetails = new ClassDetails("T11", new AttendanceTracker(0),
                new AssignmentTracker(0), new ClassParticipationTracker(0));
        try {
            assertEquals(100, classDetails.getAttendancePercentage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If attendanceTracker not initialized properly, it will throw an exception.
     */
    @Test
    public void getAttendancePercentage_invalidValues_fail() {
        try {
            ClassDetails classDetails = new ClassDetails("T11", null, null, null);
            classDetails.markPresent(Index.fromOneBased(-1));
            classDetails.markPresent(Index.fromOneBased(1));
            classDetails.markPresent(Index.fromOneBased(66));
            assertNotEquals(0, classDetails.getAttendancePercentage());
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void getClassPartPercentage_validValues_success() {
        ClassDetails classDetails = new ClassDetails("T11");
        ClassDetails.setTutorialCount(10);
        try {
            classDetails.recordClassParticipation(Index.fromOneBased(1), true);
            classDetails.recordClassParticipation(Index.fromOneBased(2), true);
            classDetails.recordClassParticipation(Index.fromOneBased(8), true);
            assertEquals(30, classDetails.getClassParticipationPercentage());
        } catch (Exception e) {
            fail();
        }
        ClassDetails.setTutorialCount(13);
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If no tutorial intialized, it should return the default value of 100.
     */
    @Test void getClassPartPercentage_noTutorials_success() {
        ClassDetails classDetails = new ClassDetails("T11", new AttendanceTracker(0),
                new AssignmentTracker(0), new ClassParticipationTracker(0));
        try {
            assertEquals(100, classDetails.getClassParticipationPercentage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If attendanceTracker not initialized properly, it will throw an exception.
     */
    @Test
    public void getClassPartPercentage_invalidValues_fail() {
        try {
            ClassDetails classDetails = new ClassDetails("T11", null, null, null);
            classDetails.recordClassParticipation(Index.fromOneBased(1), true);
            classDetails.recordClassParticipation(Index.fromOneBased(2), true);
            classDetails.recordClassParticipation(Index.fromOneBased(7), true);
            assertNotEquals(0.0, classDetails.getClassParticipationPercentage());
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void getAssignmentPercentage_validValues_success() {
        ClassDetails classDetails = new ClassDetails("T11");
        ClassDetails.setAssignmentCount(6);
        try {
            classDetails.setGrade(Index.fromOneBased(1), 30);
            classDetails.setGrade(Index.fromOneBased(2), 40);
            classDetails.setGrade(Index.fromOneBased(3), 50);
            assertEquals(20, classDetails.getAssignmentPercentage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If no tutorial intialized, it should return the default value of 100.
     */
    @Test void getAssignmentPercentage_noAssignments_success() {
        ClassDetails classDetails = new ClassDetails("T11", new AttendanceTracker(0),
                new AssignmentTracker(0), new ClassParticipationTracker(0));
        try {
            assertEquals(100, classDetails.getAssignmentPercentage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test is to check if the attendance percentage is calculated correctly.
     * If attendanceTracker not initialized properly, it will throw an exception.
     */
    @Test
    public void getAssignmentPercentage_invalidValues_fail() {
        try {
            ClassDetails classDetails = new ClassDetails("T11", null, null, null);
            classDetails.setGrade(Index.fromOneBased(1), 30);
            classDetails.setGrade(Index.fromOneBased(2), 40);
            classDetails.setGrade(Index.fromOneBased(3), 50);
            assertEquals(40, classDetails.getAssignmentPercentage());
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void getMessageInvalidAssignmentIndexZero() {
        ClassDetails.setAssignmentCount(0);
        assertEquals(ClassDetails.getMessageInvalidAssignmentIndex(), "There are no assignments configured.");
        ClassDetails.setAssignmentCount(6);
    }

    @Test
    public void getMessageInvalidTutorialIndexZero() {
        ClassDetails.setTutorialCount(0);
        assertEquals(ClassDetails.getMessageInvalidTutorialIndex(), "There are no tutorials configured.");
        ClassDetails.setTutorialCount(13);
    }

    @Test
    public void equals() {
        ClassDetails classDetails = new ClassDetails("T11");

        // same values -> returns true
        assertTrue(classDetails.equals(new ClassDetails("T11")));

        // same object -> returns true
        assertTrue(classDetails.equals(classDetails));

        // null -> returns false
        assertFalse(classDetails.equals(null));

        // different types -> returns false
        assertFalse(classDetails.equals(5.0f));

        // different values -> returns false
        assertFalse(classDetails.equals(new ClassDetails("T12")));

        ClassDetails classDetailsWithTrackers = new ClassDetails("T12",
            new AttendanceTracker(1),
            new AssignmentTracker(1),
            new ClassParticipationTracker(1));

        // same values -> returns true
        assertTrue(classDetailsWithTrackers.equals(new ClassDetails("T12",
            new AttendanceTracker(1),
            new AssignmentTracker(1),
            new ClassParticipationTracker(1))));

        // same object -> returns true
        assertTrue(classDetailsWithTrackers.equals(classDetailsWithTrackers));

        // null -> returns false
        assertFalse(classDetailsWithTrackers.equals(null));

        // different values -> returns false
        assertFalse(classDetailsWithTrackers.equals(new ClassDetails("T12",
            new AttendanceTracker(2),
            new AssignmentTracker(2),
            new ClassParticipationTracker(2))));

        // different values -> returns false
        assertFalse(classDetailsWithTrackers.equals(new ClassDetails("T12",
                new AttendanceTracker(1),
                new AssignmentTracker(2),
                new ClassParticipationTracker(1))));

        assertFalse(classDetailsWithTrackers.equals(new ClassDetails("T12",
                new AttendanceTracker(1),
                new AssignmentTracker(1),
                new ClassParticipationTracker(2))));
    }

    @Test
    public void test_hashCode() {
        ClassDetails classDetails = new ClassDetails("T11");

        // same values -> returns true
        assertTrue(classDetails.hashCode() == (new ClassDetails("T11")).hashCode());

        // same object -> returns true
        assertTrue(classDetails.hashCode() == classDetails.hashCode());

        // null -> returns false
        assertFalse(classDetails.hashCode() == 0);

        // different types -> returns false
        assertFalse(classDetails.hashCode() == (5.0f));

        // different values -> returns false
        assertFalse(classDetails.hashCode() == (new ClassDetails("T12")).hashCode());
    }
}
