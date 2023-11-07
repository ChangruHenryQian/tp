package seedu.classmanager.storage;

import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classmanager.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.information.Assignment;
import seedu.classmanager.model.student.information.AssignmentTracker;
import seedu.classmanager.model.student.information.AttendanceTracker;
import seedu.classmanager.model.student.information.ClassParticipationTracker;

public class JsonAdaptedClassDetailsTest {
    private final String validClassNumber = BENSON.getClassDetails().toString();
    private final Boolean[] validTutorialArray = new Boolean[ClassDetails.getTutorialCount()];
    private final Integer[] validAssignmentArray = new Integer[ClassDetails.getAssignmentCount()];
    private final Boolean[] invalidTutorialArray = new Boolean[ClassDetails.getTutorialCount() + 1];
    private final Integer[] invalidAssignmentArray = new Integer[ClassDetails.getAssignmentCount() + 1];
    private final List<Boolean> validAttendanceTracker = Arrays.asList(validTutorialArray);
    private final List<Integer> validAssignmentTracker = Arrays.asList(validAssignmentArray);
    private final List<Boolean> validClassParticipationTracker = Arrays.asList(validTutorialArray);
    private final List<Boolean> invalidAttendanceTracker = Arrays.asList(invalidTutorialArray);
    private final List<Integer> invalidAssignmentTracker = Arrays.asList(invalidAssignmentArray);
    private final List<Boolean> invalidClassParticipationTracker = Arrays.asList(invalidTutorialArray);

    @BeforeEach
    public void setUp() {
        Arrays.fill(validTutorialArray, FALSE);
        Arrays.fill(validAssignmentArray, 0);
        Arrays.fill(invalidTutorialArray, FALSE);
        Arrays.fill(invalidAssignmentArray, 0);
    }

    @Test
    public void toModelType_validClassDetails_returnsClassDetails() throws Exception {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(validClassNumber,
                validAttendanceTracker,
                validAssignmentTracker,
                validClassParticipationTracker);
        ClassDetails expectedClassDetails = new ClassDetails(validClassNumber,
                new AttendanceTracker(validAttendanceTracker),
                new AssignmentTracker(validAssignmentTracker),
                new ClassParticipationTracker(validClassParticipationTracker));
        assertEquals(expectedClassDetails, classDetails.toModelType());
    }

    @Test
    public void toModelType_invalidClassNumber_throwsIllegalValueException() {
        String invalidClassNumber = "11";
        JsonAdaptedClassDetails classDetails =
                new JsonAdaptedClassDetails(invalidClassNumber,
                        validAttendanceTracker,
                        validAssignmentTracker,
                        validClassParticipationTracker);
        String expectedMessage = ClassDetails.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_nullClassNumber_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(null,
                validAttendanceTracker,
                validAssignmentTracker,
                validClassParticipationTracker);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ClassDetails.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_invalidAssignmentTracker_throwsIllegalValueException() {
        List<Integer> invalidAssignmentTracker = new ArrayList<Integer>(validAssignmentTracker);
        invalidAssignmentTracker.set(0, -1);
        JsonAdaptedClassDetails classDetails =
                new JsonAdaptedClassDetails(validClassNumber,
                        validAttendanceTracker,
                        invalidAssignmentTracker,
                        validClassParticipationTracker);
        String expectedMessage = Assignment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_unequalLength_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(validClassNumber,
                Arrays.asList(true, false),
                validAssignmentTracker,
                Arrays.asList(false, true, false));
        String expectedMessage = ClassDetails.MESSAGE_UNEQUAL_LENGTH;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_unequalTutorialCount_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(validClassNumber,
                invalidAttendanceTracker,
                validAssignmentTracker,
                invalidClassParticipationTracker);
        String expectedMessage = ClassDetails.MESSAGE_TUTORIAL_COUNT_MISMATCH;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_unequalAssignmentCount_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(validClassNumber,
                validAttendanceTracker,
                invalidAssignmentTracker,
                validClassParticipationTracker);
        String expectedMessage = ClassDetails.MESSAGE_ASSIGNMENT_COUNT_MISMATCH;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }
}
