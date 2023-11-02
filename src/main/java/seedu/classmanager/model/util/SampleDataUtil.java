package seedu.classmanager.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ClassManager} with sample data.
 */
public class SampleDataUtil {

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new StudentNumber("A0247243A"), new ClassDetails("T11"),
                getTagSet("friends"), new Comment("Good at math")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new StudentNumber("A0231018A"), new ClassDetails("T12"),
                getTagSet("colleagues", "friends"), new Comment("Good at science")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new StudentNumber("A0241243A"), new ClassDetails("T12"),
                getTagSet("neighbours"), new Comment("Needs help")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new StudentNumber("A0123243A"), new ClassDetails("T11"),
                getTagSet("family"), new Comment("")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new StudentNumber("A0234543A"), new ClassDetails("T10"),
                getTagSet("classmates"), new Comment("")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new StudentNumber("A0224543A"), new ClassDetails("T11"),
                getTagSet("colleagues"), new Comment(""))
        };
    }

    public static ReadOnlyClassManager getSampleClassManager() {
        ClassManager sampleAb = new ClassManager();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
