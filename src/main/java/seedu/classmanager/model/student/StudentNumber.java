package seedu.classmanager.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's Student Number in Class Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentNumber(String)}
 */
public class StudentNumber {

    public static final String MESSAGE_CONSTRAINTS = "Student Number must begin with the letter 'A',"
            + " followed by 1 or more digits, and end with a single alphabetical character.";

    /*
     * The first character of the student number needs to be "A".
     */
    public static final String VALIDATION_REGEX = "^[Aa]\\d+[A-Za-z]$";

    public final String value;

    /**
     * Constructs an {@code StudentNumber}.
     *
     * @param studentNumber A valid student number.
     */
    public StudentNumber(String studentNumber) {
        requireNonNull(studentNumber);
        studentNumber = studentNumber.toUpperCase().trim();
        checkArgument(isValidStudentNumber(studentNumber), MESSAGE_CONSTRAINTS);
        value = studentNumber;
    }

    /**
     * Returns true if a given string is a valid email,
     * checks if the student number already exists.
     */
    public static boolean isValidStudentNumber(String test) {
        return (test.matches(VALIDATION_REGEX));
    }

    /**
     * Checks if the student number is valid.
     */
    public boolean isValid() {
        return this.value.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentNumber)) {
            return false;
        }

        StudentNumber otherStudentNumber = (StudentNumber) other;
        return value.equals(otherStudentNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
