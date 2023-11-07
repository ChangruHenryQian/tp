package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.classmanager.commons.util.CollectionUtil;
import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Edits the details of an existing student in Class Manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: STUDENT_NUMBER "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER] "
            + "[" + PREFIX_CLASS_NUMBER + "CLASS_NUMBER]\n"
            + "Example: " + COMMAND_WORD + " A0245234A "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in Class Manager.";

    private final StudentNumber studentNumber;
    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * @param studentNumber of the student to edit
     * @param editStudentDescriptor details to edit the student with
     */
    public EditCommand(StudentNumber studentNumber, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(studentNumber);
        requireNonNull(editStudentDescriptor);

        this.studentNumber = studentNumber;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(studentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToEdit = model.getStudent(studentNumber);
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.setStudent(studentToEdit, editedStudent);
        model.commitClassManager();

        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedStudent)));
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Phone updatedPhone = editStudentDescriptor.getPhone().orElse(studentToEdit.getPhone());
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(studentToEdit.getEmail());
        StudentNumber updatedStudentNumber = editStudentDescriptor.getStudentNumber()
                        .orElse(studentToEdit.getStudentNumber());
        Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(studentToEdit.getTags());
        Comment updatedComment = editStudentDescriptor.getComment().orElse(studentToEdit.getComment());

        ClassDetails updatedClassDetails = studentToEdit.getClassDetails();
        if (editStudentDescriptor.getClassNumber().isPresent()) {
            updatedClassDetails = updatedClassDetails.setClassNumber(editStudentDescriptor.getClassNumber().get());
        }

        return new Student(updatedName, updatedPhone, updatedEmail, updatedStudentNumber,
                updatedClassDetails, updatedTags, updatedComment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return studentNumber.equals(otherEditCommand.studentNumber)
                && editStudentDescriptor.equals(otherEditCommand.editStudentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student number", studentNumber)
                .add("editStudentDescriptor", editStudentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private StudentNumber studentNumber;
        private String classNumber;
        private Set<Tag> tags = new HashSet<>();
        private Comment comment;

        public EditStudentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setStudentNumber(toCopy.studentNumber);
            setClassNumber(toCopy.classNumber);
            setTags(toCopy.tags);
            setComment(toCopy.comment);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, studentNumber, classNumber);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setStudentNumber(StudentNumber studentNumber) {
            this.studentNumber = studentNumber;
        }

        public Optional<StudentNumber> getStudentNumber() {
            return Optional.ofNullable(studentNumber);
        }

        public void setClassNumber(String classNumber) {
            this.classNumber = classNumber;
        }

        public Optional<String> getClassNumber() {
            return Optional.ofNullable(classNumber);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (!tags.isEmpty()) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }
        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudentDescriptor)) {
                return false;
            }

            EditStudentDescriptor otherEditStudentDescriptor = (EditStudentDescriptor) other;
            return Objects.equals(name, otherEditStudentDescriptor.name)
                    && Objects.equals(phone, otherEditStudentDescriptor.phone)
                    && Objects.equals(email, otherEditStudentDescriptor.email)
                    && Objects.equals(studentNumber, otherEditStudentDescriptor.studentNumber)
                    && Objects.equals(classNumber, otherEditStudentDescriptor.classNumber)
                    && Objects.equals(tags, otherEditStudentDescriptor.tags)
                    && Objects.equals(comment, otherEditStudentDescriptor.comment);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("student number", studentNumber)
                    .add("class number", classNumber)
                    .add("tags", tags)
                    .add("comment", comment)
                    .toString();
        }

    }
}
