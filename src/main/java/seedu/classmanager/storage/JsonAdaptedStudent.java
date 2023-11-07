package seedu.classmanager.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String studentNumber;
    private final JsonAdaptedClassDetails classDetails;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String comment;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("studentNumber") String studentNumber,
                              @JsonProperty("classDetails") JsonAdaptedClassDetails classDetails,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("comment") String comment) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentNumber = studentNumber;
        this.classDetails = classDetails;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.comment = comment;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        studentNumber = source.getStudentNumber().value;
        classDetails = source.getJsonAdaptedClassDetails();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        comment = source.getComment().comment;
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            studentTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (studentNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentNumber.class.getSimpleName()));
        }
        if (!StudentNumber.isValidStudentNumber(studentNumber)) {
            throw new IllegalValueException(StudentNumber.MESSAGE_CONSTRAINTS);
        }
        final StudentNumber modelStudentNumber = new StudentNumber(studentNumber);

        if (classDetails == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassDetails.class.getSimpleName()));
        }

        if (comment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Comment.class.getSimpleName()));
        }
        final Comment modelComment = new Comment(comment);

        final Set<Tag> modelTags = new HashSet<>(studentTags);

        final ClassDetails modelClassDetails = classDetails.toModelType();

        return new Student(modelName, modelPhone, modelEmail, modelStudentNumber, modelClassDetails,
                modelTags, modelComment);
    }
}
