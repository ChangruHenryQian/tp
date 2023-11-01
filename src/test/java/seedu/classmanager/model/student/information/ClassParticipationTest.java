package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClassParticipationTest {

    @Test
    public void mark_success() {
        ClassParticipation classParticipation = new ClassParticipation();
        classParticipation.mark();
        assertTrue(classParticipation.getHasParticipated());
    }

    @Test
    public void unmark_success() {
        ClassParticipation classParticipation = new ClassParticipation();
        classParticipation.unmark();
        assertFalse(classParticipation.getHasParticipated());
    }

    @Test
    public void equals() {
        ClassParticipation normal = new ClassParticipation();
        ClassParticipation participated = new ClassParticipation();
        participated.mark();
        ClassParticipation didNotParticipate = new ClassParticipation();
        didNotParticipate.unmark();

        // same values -> returns true
        assertTrue(normal.equals(new ClassParticipation()));

        // same object -> returns true
        assertTrue(normal.equals(normal));

        // null -> returns false
        assertFalse(normal.equals(null));

        // different values -> returns false
        assertFalse(participated.equals(didNotParticipate));

        // different values -> returns false
        assertFalse(participated.equals(normal));
    }

    @Test
    public void toStringMethod() {
        ClassParticipation classParticipation = new ClassParticipation();

        // classParticipation should return Did not Participate by default
        assertEquals("Did not Participate", classParticipation.toString());

        classParticipation.mark();
        // presentAttendance should return Participated when true
        assertEquals("Participated", classParticipation.toString());
    }

    @Test
    public void test_hashCode() {
        ClassParticipation classParticipation = new ClassParticipation();

        // same values -> returns true
        assertTrue(classParticipation.hashCode() == (new ClassParticipation()).hashCode());

        // same object -> returns true
        assertTrue(classParticipation.hashCode() == classParticipation.hashCode());

        // null -> returns false
        assertFalse(classParticipation.hashCode() == 0);
    }
}
