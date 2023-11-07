package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Deletes a student identified using it's displayed index from Class Manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a student.\n"
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_NUMBER + "A0245234A";
    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted student: %1$s";

    private final StudentNumber targetStudentNumber;

    public DeleteCommand(StudentNumber targetStudentNumber) {
        this.targetStudentNumber = targetStudentNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(targetStudentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToDelete = model.getStudent(targetStudentNumber);
        model.deleteStudent(studentToDelete);
        if (model.isSelectedStudent(studentToDelete)) {
            model.getSelectedStudent().clear();
        }

        model.commitClassManager();

        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, Messages.format(studentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetStudentNumber.equals(otherDeleteCommand.targetStudentNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentNumber", targetStudentNumber)
                .toString();
    }
}
