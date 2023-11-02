package seedu.classmanager.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.student.Student;

/**
 * An Immutable ClassManager that is serializable to JSON format.
 */
@JsonRootName(value = "classmanager")
class JsonSerializableClassManager {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    public final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClassManager} with the given students.
     */
    @JsonCreator
    public JsonSerializableClassManager(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyClassManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClassManager}.
     */
    public JsonSerializableClassManager(ReadOnlyClassManager source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Class Manager into the model's {@code ClassManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ClassManager toModelType() throws IllegalValueException {
        ClassManager classManager = new ClassManager();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (classManager.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            classManager.addStudent(student);
        }
        return classManager;
    }
}
