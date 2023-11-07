package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.Messages.MESSAGE_STUDENT_DOES_NOT_EXIST;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Deletes the tags from an existing student in Class Manager.
 */
public class DeleteTagCommand extends TagCommand {

    public DeleteTagCommand(StudentNumber studentNumber, Set<Tag> tags) {
        super(studentNumber, tags);
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
        Set<Tag> newTags = deleteTags(studentToTag.getTags(), super.tags);
        Student editedStudent = new Student(
                studentToTag.getName(), studentToTag.getPhone(), studentToTag.getEmail(),
                studentToTag.getStudentNumber(), studentToTag.getClassDetails(), newTags, studentToTag.getComment());

        model.setStudent(studentToTag, editedStudent);
        model.commitClassManager();

        return new CommandResult(generateSuccessMessage(editedStudent));
    }

    private Set<Tag> deleteTags(Set<Tag> studentTags, Set<Tag> tagsToDelete) {
        Set<Tag> newTags = new HashSet<>(studentTags);
        newTags.removeAll(tagsToDelete);
        return newTags;
    }

    private String generateSuccessMessage(Student studentToEdit) {
        return String.format(MESSAGE_DELETE_TAG_SUCCESS, studentToEdit.getName()) + tags;
    }
}
