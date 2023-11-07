package seedu.classmanager.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.classmanager.logic.parser.Prefix;
import seedu.classmanager.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NONEXISTENT_STUDENT_NUMBER = "The student number provided does not exist here.";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "Lookup successful!\n"
            + "%1$d students listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_STUDENT_DOES_NOT_EXIST = "There is no student with the given student number.";
    public static final String MESSAGE_USER_PREFS_CANNOT_LOAD = "Unable to load user preferences from preferences.json";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("\nPhone: ")
                .append(student.getPhone())
                .append("\nEmail: ")
                .append(student.getEmail())
                .append("\nStudent Number: ")
                .append(student.getStudentNumber())
                .append("\nClass Number: ")
                .append(student.getClassDetails())
                .append("\nTags: ");
        student.getTags().forEach(builder::append);
        return builder.toString();
    }

}
