package seedu.classmanager.model.student.information;

/**
 * Represents the attendance for a specific tutorial slot.
 */
public class Attendance {

    private boolean isPresent = false;

    public Attendance() {
    }

    /**
     * Creates an {@code Attendance} object with the given isPresent value.
     */
    public Attendance(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public void mark() {
        isPresent = true;
    }

    public void unmark() {
        isPresent = false;
    }

    public boolean getIsPresent() {
        return isPresent;
    }

    @Override
    public String toString() {
        return isPresent ? "Present" : "Absent";
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return isPresent == otherAttendance.isPresent;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isPresent);
    }
}
