package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.util.List;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;

/**
 * Marks all displayed students' attendance as present.
 */
public class MarkPresentAllCommand extends Command {
    public static final String COMMAND_WORD = "present-all";
    public static final String MESSAGE_MARK_SUCCESS = "Successfully marked the attendance of all displayed students "
            + "as present.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the attendance of all displayed students as "
            + "present.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1";
    private final Index index;

    /**
     * Constructs a MarkPresentAllCommand object.
     *
     * @param index of the class.
     */
    public MarkPresentAllCommand(Index index) {
        this.index = index;
    }

    /**
     * Marks all displayed students' attendance as present.
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory The command history to record this command.
     * @return A {@code CommandResult} with the feedback message of the operation result.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        markPresentAll(model);
        model.commitClassManager();
        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }

    /**
     * Helps execute to mark all displayed students' attendance as present.
     * @param model {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    private void markPresentAll(Model model) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        for (Student studentToMark : lastShownList) {
            Student markedStudent = studentToMark.copy();
            markedStudent.markPresent(index);
            model.setStudent(studentToMark, markedStudent);
            if (model.isSelectedStudent(studentToMark)) {
                model.setSelectedStudent(markedStudent);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkPresentAllCommand)) {
            return false;
        }

        MarkPresentAllCommand e = (MarkPresentAllCommand) other;
        return index.equals(e.index);
    }
}
