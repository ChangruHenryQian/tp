package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;

public class AssignmentTrackerTest {

    @Test
    public void copy_success() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(3);
        assignmentTracker.editMarks(Index.fromZeroBased(0), 50);
        assignmentTracker.editMarks(Index.fromZeroBased(1), 10);
        assignmentTracker.editMarks(Index.fromZeroBased(2), 75);
        AssignmentTracker copy = assignmentTracker.copy();
        assertEquals(assignmentTracker, copy);
    }

    @Test
    public void constructor_invalidNumOfAssignments_throwsIllegalArgumentException() {
        int invalidNumOfAssignments = -1;
        assertThrows(IllegalArgumentException.class, () -> new AssignmentTracker(invalidNumOfAssignments));
    }

    @Test
    public void constructor_nullAssignmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignmentTracker((Assignment[]) null));
    }

    @Test
    public void constructor_validAssignmentList_success() {
        int numOfAssignments = 3;
        Assignment[] assignmentList = new Assignment[numOfAssignments];
        IntStream.range(0, numOfAssignments).forEach(i -> assignmentList[i] = new Assignment());
        AssignmentTracker assignmentTracker = new AssignmentTracker(assignmentList);
        AssignmentTracker newAssignmentTracker = new AssignmentTracker(3);
        assertEquals(assignmentTracker, newAssignmentTracker);
    }

    @Test
    public void isValidAssignments() {
        // invalid number of assignments
        assertFalse(AssignmentTracker.isValidAssignments(-1)); // -negative number

        // valid number of assignments
        assertTrue(AssignmentTracker.isValidAssignments(1));
        assertTrue(AssignmentTracker.isValidAssignments(2));
        assertTrue(AssignmentTracker.isValidAssignments(10));
    }

    @Test
    public void updateAssignmentCountChange() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(3);
        AssignmentTracker expectedAssignmentTracker = new AssignmentTracker(1);
        assignmentTracker.updateAssignmentCountChange(1);
        assertEquals(expectedAssignmentTracker, assignmentTracker);
        expectedAssignmentTracker = new AssignmentTracker(5);
        assignmentTracker.updateAssignmentCountChange(5);
        assertEquals(expectedAssignmentTracker, assignmentTracker);
    }

    @Test
    public void assignmentPercentage_validValues_returnsCorrectPercentage() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(3);
        assignmentTracker.editMarks(Index.fromZeroBased(0), 50);
        assignmentTracker.editMarks(Index.fromZeroBased(1), 100);
        assignmentTracker.editMarks(Index.fromZeroBased(2), 75);
        assertEquals(75, assignmentTracker.getPercentage());
    }

    @Test
    public void assignmentPercentage_noValues_returnsHundred() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(0);
        assertEquals(100, assignmentTracker.getPercentage());
    }

    @Test
    public void equals() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(13);

        // same values -> returns true
        assertTrue(assignmentTracker.equals(new AssignmentTracker(13)));

        // same object -> returns true
        assertTrue(assignmentTracker.equals(assignmentTracker));

        // null -> returns false
        assertFalse(assignmentTracker.equals(null));

        // different values -> returns false
        assertFalse(assignmentTracker.equals(new AssignmentTracker(26)));
    }

    @Test
    public void toStringMethod() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(3);

        assertEquals("Assignment marks:\n"
                + "Assignment 1: 0 / 100\n"
                + "Assignment 2: 0 / 100\n"
                + "Assignment 3: 0 / 100\n", assignmentTracker.toString());
    }

    @Test
    public void test_hashCode() {
        AssignmentTracker assignmentTracker = new AssignmentTracker(13);

        // same values -> returns true
        assertTrue(assignmentTracker.hashCode() == new AssignmentTracker(13).hashCode());

        // same object -> returns true
        assertTrue(assignmentTracker.hashCode() == assignmentTracker.hashCode());

        // null -> returns false
        assertFalse(assignmentTracker.hashCode() == 0);

        // different values -> returns false
        assertFalse(assignmentTracker.hashCode() == new AssignmentTracker(26).hashCode());
    }
}
