package seedu.classmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;
import static seedu.classmanager.testutil.TypicalStudents.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.testutil.ClassManagerBuilder;
import seedu.classmanager.testutil.NameContainsKeywordsPredicate;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ClassManager(), new ClassManager(modelManager.getClassManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setClassManagerFilePath(Paths.get("classmanager/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setClassManagerFilePath(Paths.get("new/classmanager/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setClassManagerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClassManagerFilePath(null));
    }

    @Test
    public void setClassManagerFilePath_validPath_setsClassManagerFilePath() {
        Path path = Paths.get("classmanager/book/file/path");
        modelManager.setClassManagerFilePath(path);
        assertEquals(path, modelManager.getClassManagerFilePath());
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInClassManager_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInClassManager_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void setSelectedStudent_setStudent_success() {
        modelManager.addStudent(ALICE);
        modelManager.setSelectedStudent(ALICE);
        assertEquals(ALICE, modelManager.getSelectedStudent().get(0));
    }

    @Test
    public void equals() {
        ClassManager classManager = new ClassManagerBuilder().withStudent(ALICE).withStudent(BENSON).build();
        ClassManager differentClassManager = new ClassManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(classManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(classManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different Class Manager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentClassManager, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(classManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClassManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(classManager, differentUserPrefs)));
    }

    @Test
    public void test_hashCode() {
        ClassManager classManager = new ClassManagerBuilder().withStudent(ALICE).withStudent(BENSON).build();
        ClassManager differentClassManager = new ClassManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(classManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(classManager, userPrefs);
        assertTrue(modelManager.hashCode() == modelManagerCopy.hashCode());

        // same object -> returns true
        assertTrue(modelManager.hashCode() == modelManager.hashCode());

        // null -> returns false
        assertFalse(modelManager.hashCode() == 0);

        // different types -> returns false
        assertFalse(modelManager.hashCode() == 5);

        // different Class Manager -> returns false
        assertFalse(modelManager.hashCode() == (new ModelManager(differentClassManager, userPrefs)).hashCode());

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.hashCode() == (new ModelManager(classManager, userPrefs)).hashCode());

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClassManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.hashCode() == (new ModelManager(classManager, differentUserPrefs)).hashCode());
    }
}
