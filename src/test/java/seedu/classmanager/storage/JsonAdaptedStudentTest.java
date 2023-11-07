package seedu.classmanager.storage;

import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classmanager.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.StudentNumber;


public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_STUDENT_NUMBER = "B0249242A";
    private static final JsonAdaptedClassDetails INVALID_CLASS_DETAILS = new JsonAdaptedClassDetails("11",
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_STUDENT_NUMBER = BENSON.getStudentNumber().toString();
    private static final String VALID_COMMENT = BENSON.getComment().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private final Boolean[] tutorialArray = new Boolean[ClassDetails.getTutorialCount()];
    private final Integer[] assignmentArray = new Integer[ClassDetails.getAssignmentCount()];
    private List<Boolean> validAttendanceTracker;
    private List<Integer> validAssignmentTracker;
    private List<Boolean> validClassParticipationTracker;
    private JsonAdaptedClassDetails validClassDetails;

    @BeforeEach
    public void setUp() {
        Arrays.fill(tutorialArray, FALSE);
        Arrays.fill(assignmentArray, 0);
        validAttendanceTracker = Arrays.asList(tutorialArray);
        validAssignmentTracker = Arrays.asList(assignmentArray);
        validClassParticipationTracker = Arrays.asList(tutorialArray);
        validClassDetails = new JsonAdaptedClassDetails(
                BENSON.getClassNumber(), validAttendanceTracker, validAssignmentTracker,
                validClassParticipationTracker);
    }

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                        validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                        validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_EMAIL, VALID_STUDENT_NUMBER,
                validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_STUDENT_NUMBER,
                        validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_STUDENT_NUMBER,
                validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidStudentNumber_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_STUDENT_NUMBER,
                        validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = StudentNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullStudentNumber_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                validClassDetails, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidClassDetails_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                        INVALID_CLASS_DETAILS, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = ClassDetails.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullClassDetails_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                null, VALID_TAGS, VALID_COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClassDetails.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                        validClassDetails, invalidTags, VALID_COMMENT);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_nullComment_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_NUMBER,
                validClassDetails, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Comment.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}
