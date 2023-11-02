package seedu.classmanager.model.student.information;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.commons.exceptions.IllegalValueException;

/**
 * Represents an array of assignments to track the student's grades.
 */
public class AssignmentTracker implements Tracker {

    public static final String MESSAGE_CONSTRAINTS = "Assignment Tracker needs to have positive number of assignments.";

    private Assignment[] assignmentList;

    /**
     * Constructs an {@code AssignmentTracker}.
     *
     * @param numOfAssignments A valid integer for the number of assignments in a class.
     *
     */
    public AssignmentTracker(int numOfAssignments) {
        checkArgument(isValidAssignments(numOfAssignments), MESSAGE_CONSTRAINTS);
        assignmentList = new Assignment[numOfAssignments];
        IntStream.range(0, numOfAssignments).forEach(i -> assignmentList[i] = new Assignment());
    }

    /**
     * Constructs an {@code AssignmentTracker}. With the given assignment tracker list.
     *
     * @param assignmentTracker A list of integer to represent marks for the assignments.
     */
    public AssignmentTracker(List<Integer> assignmentTracker) throws IllegalValueException {
        requireNonNull(assignmentTracker);
        assignmentList = new Assignment[assignmentTracker.size()];
        for (int i = 0; i < assignmentTracker.size(); i++) {
            assignmentList[i] = new Assignment(assignmentTracker.get(i));
        }
    }

    /**
     * Constructs an {@code AssignmentTracker} with a given assignment list.
     * Used for duplication.
     * @param assignmentList A list of integers stored in {@code Assignment}.
     */
    public AssignmentTracker(Assignment[] assignmentList) {
        requireNonNull(assignmentList);
        this.assignmentList = assignmentList;
    }

    /**
     * Returns a deep copy of the assignment tracker.
     * @return A deep copy of {@code AssignmentTracker}.
     */
    public AssignmentTracker copy() {
        Assignment[] newAssignmentList = new Assignment[this.assignmentList.length];
        for (int i = 0; i < this.assignmentList.length; i++) {
            newAssignmentList[i] = new Assignment();
            newAssignmentList[i].setMarks(this.assignmentList[i].getMarks());
        }
        return new AssignmentTracker(newAssignmentList);
    }


    /**
     * Returns true if a given number is a valid amount of assignments
     */
    public static boolean isValidAssignments(int numOfAssignments) {
        return numOfAssignments >= 0;
    }

    /**
     * Change the mark of the assignment.
     * @param index Position of assignment in array.
     * @param marks Marks to be changed to.
     */
    public void editMarks(Index index, int marks) {
        assignmentList[index.getZeroBased()].setMarks(marks);
    }

    /**
     * Returns a Json friendly version of the assignmentTracker.
     */
    public List<Integer> getJson() {
        List<Integer> assignmentTracker = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            assignmentTracker.add(assignment.getMarks());
        }
        return assignmentTracker;
    }

    /**
     * Returns of overall assignment grades.
     *
     * @return Percentage of overall assignment grades.
     */
    public double getPercentage() {
        if (assignmentList.length == 0) {
            return 100;
        }
        int score = 0;
        int totalScore = 0;
        for (Assignment assignment : assignmentList) {
            totalScore += 100;
            score += assignment.getMarks();
        }
        return (double) score / totalScore * 100;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Assignment marks:\n");
        for (int i = 0; i < assignmentList.length; i++) {
            ret.append("Assignment ").append(i + 1).append(": ").append(assignmentList[i].toString()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignmentTracker)) {
            return false;
        }

        AssignmentTracker otherAssignmentTracker = (AssignmentTracker) other;
        return Arrays.equals(assignmentList, otherAssignmentTracker.assignmentList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(assignmentList);
    }

    /**
     * Updates the length of the assignment tracker. Whenever the assignment count changes.
     */
    public void updateAssignmentCountChange(int assignmentCount) {
        if (assignmentCount == assignmentList.length) {
            return;
        }
        Assignment[] newAssignments = new Assignment[assignmentCount];
        for (int i = 0; i < assignmentCount; i++) {
            if (i < assignmentList.length) {
                newAssignments[i] = assignmentList[i];
            } else {
                newAssignments[i] = new Assignment();
            }
        }
        assignmentList = newAssignments;
    }
}
