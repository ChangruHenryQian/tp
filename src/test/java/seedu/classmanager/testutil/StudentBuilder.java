package seedu.classmanager.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;
import seedu.classmanager.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_STUDENT_NUMBER = "A0249434A";
    public static final String DEFAULT_CLASS_NUMBER = "T11";
    private static final String DEFAULT_COMMENT = "Good student";

    private Name name;
    private Phone phone;
    private Email email;

    private StudentNumber studentNumber;
    private ClassDetails classDetails;
    private Set<Tag> tags;
    private Comment comment;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
        classDetails = new ClassDetails(DEFAULT_CLASS_NUMBER);
        tags = new HashSet<>();
        comment = new Comment(DEFAULT_COMMENT);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        studentNumber = studentToCopy.getStudentNumber();
        classDetails = studentToCopy.getClassDetails();
        tags = new HashSet<>(studentToCopy.getTags());
        comment = studentToCopy.getComment();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code Student} that we are building.
     */
    public StudentBuilder withStudentNumber(String studentNumber) {
        this.studentNumber = new StudentNumber(studentNumber);
        return this;
    }

    /**
     * Sets the {@code ClassDetails} of the {@code Student} that we are building.
     */
    public StudentBuilder withClassDetails(String classDetails) {
        this.classDetails = new ClassDetails(classDetails);
        return this;
    }

    /**
     * Sets a {@code Assignment} in {@code AssignmentTracker} of the
     * {@code ClassDetails} that we are building.
     */
    public StudentBuilder withAssignmentDetails(Index assignmentIndex, int marks) {
        try {
            this.classDetails.setGrade(assignmentIndex, marks);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets a {@code ClassParticipation} in {@code ClassParticipationTracker} of the
     * {@code ClassDetails} that we are building.
     */
    public StudentBuilder withClassParticipationDetails(Index tutNum, boolean isPresent) {
        try {
            this.classDetails.recordClassParticipation(tutNum, isPresent);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Student} that we are building.
     * @param comment the comment to be added
     * @return the StudentBuilder with the comment added
     */
    public StudentBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, studentNumber, classDetails, tags, comment);
    }

}
