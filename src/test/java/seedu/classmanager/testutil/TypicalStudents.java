package seedu.classmanager.testutil;

import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withStudentNumber("A0239123A").withClassDetails("T11")
            .withEmail("alice@example.com").withPhone("94351253")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withStudentNumber("A0239141A").withClassDetails("T12")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withStudentNumber("A0212123A").withClassDetails("T10").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withStudentNumber("A0213143A").withClassDetails("T9")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withStudentNumber("A0755123A").withClassDetails("T10").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withStudentNumber("A02189123A").withClassDetails("T15").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withStudentNumber("A0989113A").withClassDetails("T11").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withStudentNumber("A0818923A").withClassDetails("T11").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withStudentNumber("A09141343A").withClassDetails("T11").build();
    public static final Student JAMES = new StudentBuilder().withName("James White").withPhone("8433131")
            .withEmail("james@example.com").withStudentNumber("A0257890A").withClassDetails("T99").build();
    public static final Student KACEY = new StudentBuilder().withName("Kacey Armstrong").withPhone("81102001")
            .withEmail("kacey@example.com").withStudentNumber("A2334512D").withClassDetails("T81").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withStudentNumber(VALID_STUDENT_NUMBER_AMY)
            .withClassDetails(VALID_CLASS_NUMBER_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withStudentNumber(VALID_STUDENT_NUMBER_BOB)
            .withClassDetails(VALID_CLASS_NUMBER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code ClassManager} with some of the typical students.
     */
    public static ClassManager getTypicalClassManager() {
        ClassManager ab = new ClassManager();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns an {@code ClassManager} with even more of the typical students.
     */
    public static ClassManager getTypicalClassManagerMore() {
        ClassManager ab = new ClassManager();
        for (Student student : getMoreTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Student> getMoreTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA, JAMES,
                KACEY));
    }
}
