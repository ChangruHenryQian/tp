package seedu.classmanager.model.student.information;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.AppUtil.checkArgument;
import static seedu.classmanager.model.student.ClassDetails.MESSAGE_INVALID_TUTORIAL_INDEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.exceptions.CommandException;

/**
 * Represents a Student's AttendanceTracker grades in the class manager.
 * Guarantees: is valid as declared in {@link #isValidAttendance(int)}
 */
public class AttendanceTracker implements Tracker {

    public static final String MESSAGE_CONSTRAINTS = "Attendance Tracker needs to have positive number of tutorials.";

    private Attendance[] attendanceList;

    /**
     * Constructs an {@code AttendanceTracker}.
     *
     * @param numOfTut A valid integer for the number of tutorials in a class.
     *
     */
    public AttendanceTracker(int numOfTut) {
        checkArgument(isValidAttendance(numOfTut), MESSAGE_CONSTRAINTS);
        attendanceList = new Attendance[numOfTut];
        IntStream.range(0, numOfTut).forEach(i -> attendanceList[i] = new Attendance());
    }

    /**
     * Constructs an {@code AttendanceTracker} with a given attendance tracker list.
     *
     * @param attendanceTracker A list of booleans to represent attendance.
     */
    public AttendanceTracker(List<Boolean> attendanceTracker) {
        requireNonNull(attendanceTracker);
        attendanceList = new Attendance[attendanceTracker.size()];
        IntStream.range(0, attendanceTracker.size())
                .forEach(i -> attendanceList[i] = new Attendance(attendanceTracker.get(i)));
    }

    /**
     * Constructs an {@code AttendanceTracker} with a given attendance list.
     * Used for duplication.
     * @param attendanceList A list of booleans stored in {@code Attendance}.
     */
    public AttendanceTracker(Attendance[] attendanceList) {
        requireNonNull(attendanceList);
        this.attendanceList = attendanceList;
    }

    /**
     * Returns a deep copy of the attendance tracker.
     * @return A deep copy of {@code AttendanceTracker}.
     */
    public AttendanceTracker copy() {
        Attendance[] newAttendanceList = new Attendance[this.attendanceList.length];
        for (int i = 0; i < this.attendanceList.length; i++) {
            newAttendanceList[i] = new Attendance();
            if (this.attendanceList[i].getIsPresent()) {
                newAttendanceList[i].mark();
            }
        }
        return new AttendanceTracker(newAttendanceList);
    }

    /**
     * Returns true if a given number is a valid attendance
     */
    public static boolean isValidAttendance(int numOfTut) {
        return numOfTut >= 0;
    }

    /**
     * Marks attendanceTracker of a student as present.
     *
     * @param tutNum The tutorial number.
     */
    public void markPresent(Index tutNum) throws CommandException {
        requireNonNull(tutNum);
        if (tutNum.getZeroBased() >= attendanceList.length) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_TUTORIAL_INDEX, attendanceList.length));
        }
        attendanceList[tutNum.getZeroBased()].mark();
    }

    /**
     * Marks attendanceTracker of a student as absent.
     *
     * @param tutNum The tutorial number.
     */
    public void markAbsent(Index tutNum) throws CommandException {
        requireNonNull(tutNum);
        if (tutNum.getZeroBased() >= attendanceList.length) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_TUTORIAL_INDEX, attendanceList.length));
        }
        attendanceList[tutNum.getZeroBased()].unmark();
    }

    /**
     * Returns true if the attendance is present for the given tutorial number
     *
     * @param tutNum The tutorial number.
     */
    public boolean isPresent(Index tutNum) throws CommandException {
        if (tutNum.getZeroBased() >= attendanceList.length) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_TUTORIAL_INDEX, attendanceList.length));
        }
        return attendanceList[tutNum.getZeroBased()].getIsPresent();
    }

    /**
     * Returns a Json friendly version of the attendanceTracker.
     */
    public List<Boolean> getJson() {
        List<Boolean> attendanceTracker = new ArrayList<>();
        for (Attendance attendance : this.attendanceList) {
            attendanceTracker.add(attendance.getIsPresent());
        }
        return attendanceTracker;
    }

    /**
     * Returns the percentage of tutorials attended.
     *
     * @return Percentage of tutorials attended.
     */
    public double getPercentage() {
        // Case when there are no tutorials
        if (attendanceList.length == 0) {
            return 100;
        }
        int score = 0;
        int totalScore = 0;
        for (int i = 0; i < attendanceList.length; i++) {
            if (attendanceList[i] != null) {
                totalScore += 1;
                if (attendanceList[i].getIsPresent()) {
                    score += 1;
                }
            }
        }
        return (double) score / totalScore * 100;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Attendance:\n");
        for (int i = 0; i < attendanceList.length; i++) {
            ret.append("Tutorial ").append(i + 1).append(": ").append(attendanceList[i].toString()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceTracker)) {
            return false;
        }

        AttendanceTracker otherAttendanceTracker = (AttendanceTracker) other;
        return Arrays.equals(attendanceList, otherAttendanceTracker.attendanceList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(attendanceList);
    }

    /**
     * Updates the length of the attendance tracker. Whenever the tutorial count changes.
     */
    public void updateTutorialCountChange(int tutorialCount) {
        if (tutorialCount == attendanceList.length) {
            return;
        }
        Attendance[] newAttendanceList = new Attendance[tutorialCount];
        for (int i = 0; i < tutorialCount; i++) {
            if (i < attendanceList.length) {
                newAttendanceList[i] = attendanceList[i];
            } else {
                newAttendanceList[i] = new Attendance();
            }
        }
        attendanceList = newAttendanceList;
    }
}
