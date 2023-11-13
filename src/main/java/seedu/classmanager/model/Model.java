package seedu.classmanager.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Sets the assignment count in the user prefs.
     */
    void setAssignmentCount(int assignmentCount);

    /**
     * Sets the tutorial count in the user prefs.
     */
    void setTutorialCount(int tutorialCount);

    /**
     * Toggles the color theme.
     */
    void toggleColourTheme();

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' theme.
     */
    String getTheme();

    /**
     * Returns the user prefs' Class Manager file path.
     */
    Path getClassManagerFilePath();

    /**
     * Sets the user prefs' Class Manager file path.
     */
    void setClassManagerFilePath(Path classManagerFilePath);

    /**
     * Replaces Class Manager data with the data in {@code classManager}.
     */
    void setClassManager(ReadOnlyClassManager classManager);

    /** Returns the ClassManager */
    ReadOnlyClassManager getClassManager();

    /**
     * Returns true if a student with the same identity as {@code student} exists in Class Manager.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in Class Manager.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in Class Manager.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
            * {@code target} must exist in Class Manager.
            * The student identity of {@code editedStudent} must not be the same as
            * another existing student in Class Manager.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Gets the student with the given student number.
     *
     * @param studentNumber the given student number.
     */
    Student getStudent(StudentNumber studentNumber);

    /**
     * Gets the selected student.
     */
    Student getSelectedStudent();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns view of selected student. */
    ObservableList<Student> getObservableSelectedStudent();

    /**
     * Sets a student to be selected to view class details.
     *
     * @param student to be set as the selected student.
     */
    void setSelectedStudent(Student student);

    /**
     * Check if the Student is the selected student.
     *
     * @param student to check if is the selected student.
     */
    boolean isSelectedStudent(Student student);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    //@@author Cikguseven-reused
    //Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
    // with minor modifications
    /**
     * Returns true if the model has previous Class Manager states to restore.
     */
    boolean canUndoClassManager();

    /**
     * Returns true if the model has undone Class Manager states to restore.
     */
    boolean canRedoClassManager();

    /**
     * Restores the model's Class Manager to its previous state.
     */
    void undoClassManager();

    /**
     * Restores the model's Class Manager to its previously undone state.
     */
    void redoClassManager();

    /**
     * Saves the current Class Manager state for undo/redo.
     */
    void commitClassManager();
    //@@author

    /**
     * Resets the history of the model after a load command.
     */
    void loadReset(ReadOnlyClassManager classManager);

    /**
     * Resets the history of the model after a config command.
     */
    void configReset();

    /**
     * Resets the selected student after a clear command.
     */
    void resetSelectedStudent();
}
