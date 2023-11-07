package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Records the class participation for a student in a specific tutorial session.
 */
public class RecordClassParticipationCommand extends Command {

    public static final String COMMAND_WORD = "class-part";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records the class participation of a student in a specific tutorial session.\n"
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_SESSION "
            + PREFIX_PARTICIPATION + "PARTICIPATION\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_STUDENT_NUMBER + "A0123456X "
            + PREFIX_TUTORIAL_INDEX + "1 " + PREFIX_PARTICIPATION + "true";

    public static final String MESSAGE_SUCCESS = "Recorded participation for Student: %1$s\n"
            + "Here are the details:\n";

    private final StudentNumber studentNumber;
    private final Index tutorialIndex;
    private final boolean hasParticipated;

    /**
     * Creates an RecordPartCommand to record the specified {@code Student}'s participation
     */
    public RecordClassParticipationCommand(StudentNumber studentNumber, Index tutorialIndex, boolean hasParticipated) {
        this.studentNumber = studentNumber;
        this.tutorialIndex = tutorialIndex;
        this.hasParticipated = hasParticipated;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory)
            throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(studentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToMark = model.getStudent(studentNumber);
        Student markedStudent = studentToMark.copy();
        markedStudent.markClassParticipation(this.tutorialIndex, this.hasParticipated);
        model.setStudent(studentToMark, markedStudent);
        model.setSelectedStudent(markedStudent);
        model.commitClassManager();

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentNumber)
                + markedStudent.getClassDetails().displayParticipation());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordClassParticipationCommand)) {
            return false;
        }

        RecordClassParticipationCommand otherSetGradeCommand = (RecordClassParticipationCommand) other;
        return studentNumber.equals(otherSetGradeCommand.studentNumber)
                && tutorialIndex.equals(otherSetGradeCommand.tutorialIndex)
                && hasParticipated == otherSetGradeCommand.hasParticipated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentNumber", studentNumber)
                .add("tutorialIndex", tutorialIndex)
                .add("hasParticipated", hasParticipated)
                .toString();
    }
}
