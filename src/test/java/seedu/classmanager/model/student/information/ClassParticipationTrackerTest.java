package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;

public class ClassParticipationTrackerTest {

    @Test
    public void constructor_nullClassParticipationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassParticipationTracker((ClassParticipation[]) null));
    }

    @Test
    public void constructor_invalidNumOfTut_throwsIllegalArgumentException() {
        int invalidNumOfTut = -1;
        assertThrows(IllegalArgumentException.class, () -> new ClassParticipationTracker(invalidNumOfTut));
    }

    @Test
    public void isValidAttendance() {
        // invalid number of tutorials
        assertFalse(ClassParticipationTracker.isValidClassParticipation(-1)); // -negative number

        // valid number of tutorials
        assertTrue(ClassParticipationTracker.isValidClassParticipation(1));
        assertTrue(ClassParticipationTracker.isValidClassParticipation(2));
        assertTrue(ClassParticipationTracker.isValidClassParticipation(10));
    }

    @Test
    public void updateTutorialCountChange() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(3);
        ClassParticipationTracker expectedClassParticipationTracker = new ClassParticipationTracker(1);
        classParticipationTracker.updateTutorialCountChange(1);
        assertEquals(expectedClassParticipationTracker, classParticipationTracker);
        expectedClassParticipationTracker = new ClassParticipationTracker(5);
        classParticipationTracker.updateTutorialCountChange(5);
        assertEquals(expectedClassParticipationTracker, classParticipationTracker);
    }

    @Test
    public void classPartPercentage_validValues_returnsCorrectPercentage() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(10);
        classParticipationTracker.markParticipated(Index.fromZeroBased(0));
        classParticipationTracker.markParticipated(Index.fromZeroBased(2));
        assertEquals(20, classParticipationTracker.getPercentage());
    }

    @Test
    public void classPartPercentage_noValues_returnsHundred() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(0);
        assertEquals(100, classParticipationTracker.getPercentage());
    }

    @Test
    public void equals() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(13);

        // same values -> returns true
        assertTrue(classParticipationTracker.equals(new ClassParticipationTracker(13)));

        // same object -> returns true
        assertTrue(classParticipationTracker.equals(classParticipationTracker));

        // null -> returns false
        assertFalse(classParticipationTracker.equals(null));

        // different values -> returns false
        assertFalse(classParticipationTracker.equals(new ClassParticipationTracker(26)));
    }

    @Test
    public void toStringMethod() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(3);

        assertEquals("Class participation:\n"
                + "Tutorial 1: Did not Participate\n"
                + "Tutorial 2: Did not Participate\n"
                + "Tutorial 3: Did not Participate\n", classParticipationTracker.toString());
    }

    @Test
    public void test_hashCode() {
        ClassParticipationTracker classParticipationTracker = new ClassParticipationTracker(13);

        // same values -> returns true
        assertTrue(classParticipationTracker.hashCode() == new ClassParticipationTracker(13).hashCode());

        // same object -> returns true
        assertTrue(classParticipationTracker.hashCode() == classParticipationTracker.hashCode());

        // null -> returns false
        assertFalse(classParticipationTracker.hashCode() == 0);

        // different values -> returns false
        assertFalse(classParticipationTracker.hashCode() == new ClassParticipationTracker(26).hashCode());
    }
}
