package seedu.classmanager.logic.commands;

import static seedu.classmanager.commons.util.CollectionUtil.requireAllNonNull;

import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Selects a student in Class Manager to view their class details.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": selects a student to view their class details\n"
        + "Parameters: s/STUDENT_NUMBER\n"
        + "Example: " + COMMAND_WORD + " s/A1234567N";
    public static final String MESSAGE_COMMAND_SUCCESS =
        "You are now viewing the class details of student: %1$s";
    public static final String MESSAGE_COMMAND_FAILURE =
        "Please check that the student exist in Class Manager.";
    protected final StudentNumber studentNumber;

    /**
     * ViewCommand object to execute the user input.
     * @param studentNumber of the the student to be viewed.
     */
    public ViewCommand(StudentNumber studentNumber) {
        requireAllNonNull(studentNumber);
        this.studentNumber = studentNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        if (!model.hasStudent(new Student(studentNumber))) {
            throw new CommandException(MESSAGE_COMMAND_FAILURE);
        }

        Student studentToView = model.getStudent(studentNumber);
        model.setSelectedStudent(studentToView);
        model.commitClassManager();

        return new CommandResult(String.format(MESSAGE_COMMAND_SUCCESS, studentToView.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand e = (ViewCommand) other;
        return studentNumber.equals(e.studentNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("studentNumber", studentNumber)
            .toString();
    }
}
