package seedu.classmanager.model;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Represents the in-memory model of Class Manager data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedClassManager versionedClassManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given Class Manager and userPrefs.
     */
    public ModelManager(ReadOnlyClassManager classManager, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(classManager, userPrefs);

        logger.fine("Initializing with Class Manager: " + classManager + " and user prefs " + userPrefs);

        this.versionedClassManager = new VersionedClassManager(classManager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.versionedClassManager.getStudentList());
    }

    public ModelManager() {
        this(new ClassManager(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public String getTheme() {
        return userPrefs.getTheme();
    }

    @Override
    public Path getClassManagerFilePath() {
        return userPrefs.getClassManagerFilePath();
    }

    @Override
    public void setClassManagerFilePath(Path classManagerFilePath) {
        requireNonNull(classManagerFilePath);
        userPrefs.setClassManagerFilePath(classManagerFilePath);
    }

    /**
     * Assignment count that the user configured.
     */
    @Override
    public void setAssignmentCount(int assignmentCount) {
        userPrefs.setAssignmentCount(assignmentCount);
    }

    /**
     * Tutorial count that the user configured.
     */
    @Override
    public void setTutorialCount(int tutorialCount) {
        userPrefs.setTutorialCount(tutorialCount);
    }

    @Override
    public void toggleColourTheme() {
        userPrefs.toggleColourTheme();
    }

    //=========== ClassManager ================================================================================

    @Override
    public void setClassManager(ReadOnlyClassManager classManager) {
        this.versionedClassManager.resetData(classManager);
    }

    @Override
    public ReadOnlyClassManager getClassManager() {
        return versionedClassManager;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return versionedClassManager.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        versionedClassManager.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        versionedClassManager.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        versionedClassManager.setStudent(target, editedStudent);
    }

    @Override
    public Student getStudent(StudentNumber studentNumber) {
        requireAllNonNull(studentNumber);
        return versionedClassManager.getStudent(studentNumber);
    }

    @Override
    public Student getSelectedStudent() {
        return versionedClassManager.getSelectedStudent();
    }

    @Override
    public void setSelectedStudent(Student student) {
        requireNonNull(student);
        versionedClassManager.setSelectedStudent(student);
    }

    @Override
    public boolean isSelectedStudent(Student student) {
        requireNonNull(student);
        return student.isSameStudent(getSelectedStudent());
    }
    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedClassManager}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public ObservableList<Student> getObservableSelectedStudent() {
        return versionedClassManager.getObservableSelectedStudent();
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //@@author Cikguseven-reused
    //Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
    // with minor modifications
    /**
     * Returns true if the model has previous Class Manager states to restore.
     */
    @Override
    public boolean canUndoClassManager() {
        return versionedClassManager.canUndo();
    }

    /**
     * Returns true if the model has undone Class Manager states to restore.
     */
    @Override
    public boolean canRedoClassManager() {
        return versionedClassManager.canRedo();
    }

    /**
     * Restores the model's Class Manager to its previous state.
     */
    @Override
    public void undoClassManager() {
        versionedClassManager.undo();
    }

    /**
     * Restores the model's Class Manager to its previously undone state.
     */
    @Override
    public void redoClassManager() {
        versionedClassManager.redo();
    }

    /**
     * Saves the current Class Manager state for undo/redo.
     */
    @Override
    public void commitClassManager() {
        versionedClassManager.commit();
    }
    //@@author

    /**
     * Resets the history of the model after a load command.
     */
    @Override
    public void loadReset(ReadOnlyClassManager classManager) {
        this.versionedClassManager.loadReset(classManager);
        versionedClassManager.resetSelectedStudent();
    }

    /**
     * Resets the history of the model after a config command.
     */
    @Override
    public void configReset() {
        this.versionedClassManager.configReset();
    }

    /**
     * Resets the selected student after a clear command.
     */
    @Override
    public void resetSelectedStudent() {
        versionedClassManager.resetSelectedStudent();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return versionedClassManager.equals(otherModelManager.versionedClassManager)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredStudents.equals(otherModelManager.filteredStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionedClassManager, userPrefs, filteredStudents);
    }
}
