package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.exceptions.CommandException;

public class AttendanceTrackerTest {

    @Test
    public void constructor_nullAttendanceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendanceTracker((Attendance[]) null));
    }

    @Test
    public void constructor_invalidNumOfTut_throwsIllegalArgumentException() {
        int invalidNumOfTut = -1;
        assertThrows(IllegalArgumentException.class, () -> new AttendanceTracker(invalidNumOfTut));
    }

    @Test
    public void isValidAttendance() {
        // invalid number of tutorials
        assertFalse(AttendanceTracker.isValidAttendance(-1)); // -negative number

        // valid number of tutorials
        assertTrue(AttendanceTracker.isValidAttendance(1));
        assertTrue(AttendanceTracker.isValidAttendance(2));
        assertTrue(AttendanceTracker.isValidAttendance(10));
    }

    @Test
    public void updateTutorialCountChange() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(3);
        AttendanceTracker expectedAttendanceTracker = new AttendanceTracker(1);
        attendanceTracker.updateTutorialCountChange(1);
        assertEquals(expectedAttendanceTracker, attendanceTracker);
        expectedAttendanceTracker = new AttendanceTracker(5);
        attendanceTracker.updateTutorialCountChange(5);
        assertEquals(expectedAttendanceTracker, attendanceTracker);
    }

    @Test
    public void attendancePercentage_validValues_returnsCorrectPercentage() throws CommandException {
        AttendanceTracker attendanceTracker = new AttendanceTracker(10);
        attendanceTracker.markPresent(Index.fromZeroBased(0));
        attendanceTracker.markPresent(Index.fromZeroBased(2));
        assertEquals(20, attendanceTracker.getPercentage());
    }

    @Test
    public void attendancePercentage_noValues_returnsHundred() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(0);
        assertEquals(100, attendanceTracker.getPercentage());
    }

    @Test
    public void markPresent_validTutorialIndex_success() throws CommandException {
        AttendanceTracker attendanceTracker = new AttendanceTracker(10);
        int tutNum = 1;
        attendanceTracker.markPresent(Index.fromOneBased(tutNum));
        assertTrue(attendanceTracker.isPresent(Index.fromOneBased(tutNum)));
    }

    @Test
    public void markPresent_invalidTutorialIndex_throwsCommandException() {
        int total = 10;
        AttendanceTracker attendanceTracker = new AttendanceTracker(total);

        int num = 100;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.markPresent(Index.fromZeroBased(num)));

        // edge case
        int edg = total + 1;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.markPresent(Index.fromZeroBased(edg)));
    }

    @Test
    public void markAbsent_invalidTutorialIndex_throwsCommandException() {
        int total = 10;
        AttendanceTracker attendanceTracker = new AttendanceTracker(total);

        int num = 100;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.markAbsent(Index.fromZeroBased(num)));
        // edge case
        int edg = total + 1;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.markAbsent(Index.fromZeroBased(edg)));
    }

    @Test
    public void isPresent_invalidTutorialIndex_throwsCommandException() {
        int total = 10;
        AttendanceTracker attendanceTracker = new AttendanceTracker(total);

        int num = 100;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.isPresent(Index.fromZeroBased(num)));

        // edge case
        int edg = total + 1;
        assertThrows(
                CommandException.class, ()
                        -> attendanceTracker.isPresent(Index.fromZeroBased(edg)));
    }

    @Test
    public void equals() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(13);

        // same values -> returns true
        assertTrue(attendanceTracker.equals(new AttendanceTracker(13)));

        // same object -> returns true
        assertTrue(attendanceTracker.equals(attendanceTracker));

        // null -> returns false
        assertFalse(attendanceTracker.equals(null));

        // different values -> returns false
        assertFalse(attendanceTracker.equals(new AttendanceTracker(26)));
    }

    @Test
    public void toStringMethod() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(3);

        assertEquals("Attendance:\n"
                + "Tutorial 1: Absent\n"
                + "Tutorial 2: Absent\n"
                + "Tutorial 3: Absent\n", attendanceTracker.toString());
    }

    @Test
    public void test_hashCode() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(13);

        // same values -> returns true
        assertTrue(attendanceTracker.hashCode() == new AttendanceTracker(13).hashCode());

        // same object -> returns true
        assertTrue(attendanceTracker.hashCode() == attendanceTracker.hashCode());

        // null -> returns false
        assertFalse(attendanceTracker.hashCode() == 0);

        // different values -> returns false
        assertFalse(attendanceTracker.hashCode() == new AttendanceTracker(26).hashCode());
    }
}
