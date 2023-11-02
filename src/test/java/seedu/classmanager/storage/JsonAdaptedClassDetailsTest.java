package seedu.classmanager.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classmanager.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.information.Assignment;
import seedu.classmanager.model.student.information.AssignmentTracker;
import seedu.classmanager.model.student.information.AttendanceTracker;
import seedu.classmanager.model.student.information.ClassParticipationTracker;

public class JsonAdaptedClassDetailsTest {
    private static final String INVALID_CLASS_NUMBER = "11";

    private static final String VALID_CLASS_NUMBER = BENSON.getClassDetails().toString();
    static Boolean[] tutorialArray = new Boolean[ClassDetails.tutorialCount];
    static Integer[] assignmentArray = new Integer[ClassDetails.assignmentCount];
    Arrays.fill(tutorialArray, Boolean.FALSE);
    public static final List<Boolean> VALID_ATTENDANCE_TRACKER = Arrays.asList(tutorialArray);
    public static final List<Integer> VALID_ASSIGNMENT_TRACKER = Arrays.asList(assignmentArray);
    public static final List<Boolean> VALID_CLASS_PARTICIPATION_TRACKER = Arrays.asList(tutorialArray);

    @Test
    public void toModelType_validClassDetails_returnsClassDetails() throws Exception {
        System.out.println(Arrays.toString(tutorialArray));
        System.out.println(Arrays.toString(assignmentArray));
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(VALID_CLASS_NUMBER,
                VALID_ATTENDANCE_TRACKER,
                VALID_ASSIGNMENT_TRACKER,
                VALID_CLASS_PARTICIPATION_TRACKER);
        ClassDetails expectedClassDetails = new ClassDetails(VALID_CLASS_NUMBER,
                new AttendanceTracker(VALID_ATTENDANCE_TRACKER),
                new AssignmentTracker(VALID_ASSIGNMENT_TRACKER),
                new ClassParticipationTracker(VALID_CLASS_PARTICIPATION_TRACKER));
        assertEquals(expectedClassDetails, classDetails.toModelType());
    }

    @Test
    public void toModelType_invalidClassNumber_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails =
                new JsonAdaptedClassDetails(INVALID_CLASS_NUMBER,
                        VALID_ATTENDANCE_TRACKER,
                        VALID_ASSIGNMENT_TRACKER,
                        VALID_CLASS_PARTICIPATION_TRACKER);
        String expectedMessage = ClassDetails.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_nullClassNumber_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(null,
                VALID_ATTENDANCE_TRACKER,
                VALID_ASSIGNMENT_TRACKER,
                VALID_CLASS_PARTICIPATION_TRACKER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ClassDetails.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_invalidAssignmentTracker_throwsIllegalValueException() {
        List<Integer> invalidAssignmentTracker = new ArrayList<Integer>(VALID_ASSIGNMENT_TRACKER);
        invalidAssignmentTracker.set(0, -1);
        JsonAdaptedClassDetails classDetails =
                new JsonAdaptedClassDetails(VALID_CLASS_NUMBER,
                        VALID_ATTENDANCE_TRACKER,
                        invalidAssignmentTracker,
                        VALID_CLASS_PARTICIPATION_TRACKER);
        String expectedMessage = Assignment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

    @Test
    public void toModelType_unequalLength_throwsIllegalValueException() {
        JsonAdaptedClassDetails classDetails = new JsonAdaptedClassDetails(VALID_CLASS_NUMBER,
                Arrays.asList(true, false),
                VALID_ASSIGNMENT_TRACKER,
                Arrays.asList(false, true, false));
        String expectedMessage = ClassDetails.MESSAGE_UNEQUAL_LENGTH;
        assertThrows(IllegalValueException.class, expectedMessage, classDetails::toModelType);
    }

}
