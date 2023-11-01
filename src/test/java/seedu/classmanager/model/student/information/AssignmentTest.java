package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AssignmentTest {

    @Test
    public void setMarks_success() {
        Assignment assignment = new Assignment();
        assignment.setMarks(100);
        assertEquals(100, assignment.getMarks());
    }

    @Test
    public void equals() {
        Assignment assignment = new Assignment();
        Assignment diffAssignment = new Assignment();
        diffAssignment.setMarks(100);

        // same values -> returns true
        assertTrue(assignment.equals(new Assignment()));

        // same object -> returns true
        assertTrue(assignment.equals(assignment));

        // null -> returns false
        assertFalse(assignment.equals(null));

        // different values -> returns false
        assertFalse(assignment.equals(diffAssignment));
    }

    @Test
    public void toStringMethod() {
        Assignment assignment = new Assignment();

        // assignment should print 0 / 100 as default
        assertEquals("0 / 100", assignment.toString());

        assignment.setMarks(100);
        // assignment should print 100 / 100 after change
        assertEquals("100 / 100", assignment.toString());
    }

    @Test
    public void test_hashCode() {
        Assignment assignment = new Assignment();
        Assignment diffAssignment = new Assignment();
        diffAssignment.setMarks(100);

        // same values -> returns true
        assertTrue(assignment.hashCode() == new Assignment().hashCode());

        // same object -> returns true
        assertTrue(assignment.hashCode() == assignment.hashCode());

        // null -> returns false
        assertFalse(assignment.hashCode() == 3);

        // different values -> returns false
        assertFalse(assignment.hashCode() == diffAssignment.hashCode());
    }
}

