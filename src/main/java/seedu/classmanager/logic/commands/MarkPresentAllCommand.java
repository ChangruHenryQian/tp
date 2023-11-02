package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

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
    public static final String MESSAGE_MARK_SUCCESS = "Successfully mark attendance as present.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all the students displayed as present.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL INDEX\n"
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

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        for (Student studentToMark : lastShownList) {
            Student markedStudent = studentToMark.copy();
            markedStudent.markPresent(index);
            model.setStudent(studentToMark, markedStudent);
            if (model.isSelectedStudent(studentToMark)) {
                model.setSelectedStudent(markedStudent);
            }
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitClassManager();

        return new CommandResult(MESSAGE_MARK_SUCCESS);
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
