package seedu.classmanager.testutil;

import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.student.Student;

/**
 * A utility class to help with building ClassManager objects.
 * Example usage: <br>
 *     {@code ClassManager ab = new ClassManagerBuilder().withStudent("John", "Doe").build();}
 */
public class ClassManagerBuilder {

    private ClassManager classManager;

    public ClassManagerBuilder() {
        classManager = new ClassManager();
    }

    public ClassManagerBuilder(ClassManager classManager) {
        this.classManager = classManager;
    }

    /**
     * Adds a new {@code Student} to the {@code ClassManager} that we are building.
     */
    public ClassManagerBuilder withStudent(Student student) {
        classManager.addStudent(student);
        return this;
    }

    public ClassManager build() {
        return classManager;
    }
}
