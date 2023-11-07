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
 * Marks all displayed students' attendance as absent.
 */
public class MarkAbsentAllCommand extends Command {
    public static final String COMMAND_WORD = "absent-all";
    public static final String MESSAGE_MARK_SUCCESS = "Successfully marked the attendance of all displayed students "
            + "as absent.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the attendance of all displayed students as "
            + "absent.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_SESSION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1";
    private final Index index;

    /**
     * Constructs a MarkAbsentAllCommand object.
     *
     * @param index of the class.
     */
    public MarkAbsentAllCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        for (Student studentToMark : lastShownList) {
            Student markedStudent = studentToMark.copy();
            markedStudent.markAbsent(index);
            model.setStudent(studentToMark, markedStudent);
            if (model.isSelectedStudent(studentToMark)) {
                model.setSelectedStudent(markedStudent);
            }
        }
        model.commitClassManager();

        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAbsentAllCommand)) {
            return false;
        }

        MarkAbsentAllCommand e = (MarkAbsentAllCommand) other;
        return index.equals(e.index);
    }
}
