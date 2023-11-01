package seedu.classmanager.model.student.information;

import static java.util.Objects.requireNonNull;

import seedu.classmanager.commons.exceptions.IllegalValueException;

/**
 * Represents an Assignment of a Student in Class Manager.
 */
public class Assignment {
    public static final String MESSAGE_CONSTRAINTS = "Assignment marks should be between 0 and 100";
    private Integer marks = 0;

    public Assignment() {
    }

    /**
     * Constructs a {@code Assignment}. With a given mark between 0 and 100.
     */
    public Assignment(Integer marks) throws IllegalValueException {
        requireNonNull(marks);
        if (marks > 100 || marks < 0) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
        this.marks = marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Integer getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return marks + " / 100";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return marks.equals(otherAssignment.marks);
    }

    @Override
    public int hashCode() {
        return marks.hashCode();
    }
}
