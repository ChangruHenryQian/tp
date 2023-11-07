package seedu.classmanager.logic.commands;

import static seedu.classmanager.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_STUDENT_DOES_NOT_EXIST;

import java.util.List;
import java.util.Set;

import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Changes the tags of an existing student in Class Manager.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String ADD_TAGS = "add";
    public static final String DELETE_TAGS = "delete";
    public static final String DEFAULT = "";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tags of the student identified by the student number.\n"
            + "Existing tags will be overwritten by the input.\n"
            + "Use /add to add or /delete to delete tags without overwriting all tags.\n"
            + "Parameters: s/STUDENT_NUMBER (exists in Class Manager) [/add] [/delete] t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " s/A1234567N /add t/smart.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added following tags to Student %1$s:\n";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed following tags from Student %1$s:\n";
    public static final String MESSAGE_REPLACE_ALL_TAG_SUCCESS = "Replace all tags of Student %1$s with:\n";
    public static final String MESSAGE_DELETE_ALL_TAG_SUCCESS = "Removed all tags from Student %1$s:\n";
    public static final String MESSAGE_TAG_FAILED = "There was an issue tagging the student.\n"
        + "Please check that the student with the student number exists or each tag has the “t/” prefix.\n";
    public static final String MESSAGE_INVALID_ACTION_IDENTIFIER = "Only /add and /delete is allowed.";
    protected final StudentNumber studentNumber;
    protected final Set<Tag> tags;

    /**
     * @param studentNumber of the student in the filtered student list to edit their tags.
     * @param tags of the student to be updated to.
     */
    public TagCommand(StudentNumber studentNumber, Set<Tag> tags) {
        requireAllNonNull(studentNumber, tags);
        this.studentNumber = studentNumber;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        Student studentToTag;
        try {
            studentToTag = getStudentByStudentNumber(lastShownList, studentNumber);
        } catch (NullPointerException ive) {
            throw new CommandException(MESSAGE_STUDENT_DOES_NOT_EXIST);
        }

        Student editedStudent = new Student(
                studentToTag.getName(), studentToTag.getPhone(), studentToTag.getEmail(),
                studentToTag.getStudentNumber(), studentToTag.getClassDetails(), this.tags, studentToTag.getComment());

        model.setStudent(studentToTag, editedStudent);
        model.commitClassManager();

        return new CommandResult(generateSuccessMessage(editedStudent));
    }

    /**
     * Generates a command execution success message based on whether
     * the tag is added to or removed from {@code studentToTag}.
     */
    private String generateSuccessMessage(Student studentToTag) {
        String message = !tags.isEmpty() ? MESSAGE_REPLACE_ALL_TAG_SUCCESS : MESSAGE_DELETE_ALL_TAG_SUCCESS;
        return String.format(message, studentToTag.getName()) + studentToTag.getTags();
    }

    protected Student getStudentByStudentNumber(List<Student> list, StudentNumber studentNumber) {
        return list.stream()
            .filter(student -> student.getStudentNumber().equals(studentNumber))
            .findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand e = (TagCommand) other;
        return studentNumber.equals(e.studentNumber)
                && tags.equals(e.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
