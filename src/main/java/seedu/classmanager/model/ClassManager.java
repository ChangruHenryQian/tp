package seedu.classmanager.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.classmanager.commons.util.InvalidationListenerManager;
import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.student.UniqueStudentList;

/**
 * Wraps all data at the class manager level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class ClassManager implements ReadOnlyClassManager {

    private final UniqueStudentList students;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    public ClassManager() {}

    /**
     * Creates a ClassManager using the Students in the {@code toBeCopied}
     */
    public ClassManager(ReadOnlyClassManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code ClassManager} with {@code newData}.
     */
    public void resetData(ReadOnlyClassManager newData) {
        requireNonNull(newData);
        setStudents(newData.getStudentList());
        resetSelectedStudent();
        if (newData.getSelectedStudent() != null) {
            setSelectedStudent(newData.getSelectedStudent());
        }
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in Class Manager.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns the student with the given student number.
     *
     * @param studentNumber the given student number.
     */
    public Student getStudent(StudentNumber studentNumber) {
        requireNonNull(studentNumber);
        return students.getStudent(studentNumber);
    }

    /**
     * Returns the selected student.
     */
    public Student getSelectedStudent() {
        return students.getSelectedStudent();
    }

    /**
     * Adds a student to Class Manager.
     * The student must not already exist in Class Manager.
     */
    public void addStudent(Student p) {
        students.add(p);
        indicateModified();
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in Class Manager.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in Class Manager.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code ClassManager}.
     * {@code key} must exist in Class Manager.
     */
    public void removeStudent(Student key) {
        students.remove(key);
        indicateModified();
    }

    /**
     * Sets the student to be the selected student.
     *
     * @param student to be selected
     */
    public void setSelectedStudent(Student student) {
        students.setSelectedStudent(student);
        indicateModified();
    }

    /**
     * Resets the selected student for load command.
     */
    public void resetSelectedStudent() {
        students.clearSelectedStudent();
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("students", students)
                .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Student> getObservableSelectedStudent() {
        return students.getObservableSelectedStudent();
    }

    /**
     * Adds an {@code InvalidationListener} to this {@code ClassManager}.
     * @param listener The listener to be added.
     */
    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    /**
     * Removes an {@code InvalidationListener} from this {@code ClassManager}.
     * @param listener The listener to be removed.
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that Class Manager has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassManager)) {
            return false;
        }

        ClassManager otherClassManager = (ClassManager) other;
        return students.equals(otherClassManager.students);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
