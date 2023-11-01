package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Sets a specific assignment grade for a student.
 */
public class SetGradeCommand extends Command {
    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set Student grade for a particular assignment.\n"
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT_NUMBER "
            + PREFIX_GRADE + "GRADE\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_STUDENT_NUMBER + "A0123456X "
            + PREFIX_ASSIGNMENT + "1 " + PREFIX_GRADE + "100";

    public static final String MESSAGE_SUCCESS = "Grade set for Student: %1$s\n"
            + "Here are the details:\n";


    private final StudentNumber studentNumber;
    private final int assignmentNumber;
    private final int grade;

    /**
     * Creates an SetGradeCommand to set the specified {@code Student}'s grade
     */
    public SetGradeCommand(StudentNumber studentNumber, int assignmentNumber, int grade) {
        this.studentNumber = studentNumber;
        this.assignmentNumber = assignmentNumber;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory)
            throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(studentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToGrade = model.getStudent(studentNumber);
        Student gradedStudent = studentToGrade.copy();
        gradedStudent.setGrade(assignmentNumber, grade);
        model.setStudent(studentToGrade, gradedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.setSelectedStudent(gradedStudent);
        model.commitClassManager();

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentNumber)
                + studentToGrade.getClassDetails().displayAssignments());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetGradeCommand)) {
            return false;
        }

        SetGradeCommand otherSetGradeCommand = (SetGradeCommand) other;
        return studentNumber.equals(otherSetGradeCommand.studentNumber)
                && assignmentNumber == otherSetGradeCommand.assignmentNumber
                && grade == otherSetGradeCommand.grade;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentNumber", studentNumber)
                .add("assignmentNumber", assignmentNumber)
                .add("grade", grade)
                .toString();
    }
}
