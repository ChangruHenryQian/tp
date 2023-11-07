//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications
package seedu.classmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.AMY;
import static seedu.classmanager.testutil.TypicalStudents.BOB;
import static seedu.classmanager.testutil.TypicalStudents.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.classmanager.testutil.ClassManagerBuilder;

public class VersionedClassManagerTest {

    private final ReadOnlyClassManager classManagerWithAmy = new ClassManagerBuilder().withStudent(AMY).build();
    private final ReadOnlyClassManager classManagerWithBob = new ClassManagerBuilder().withStudent(BOB).build();
    private final ReadOnlyClassManager classManagerWithCarl = new ClassManagerBuilder().withStudent(CARL).build();
    private final ReadOnlyClassManager emptyClassManager = new ClassManagerBuilder().build();

    @Test
    public void commit_singleClassManager_noStatesRemovedCurrentStateSaved() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);

        versionedClassManager.commit();
        assertClassManagerListStatus(versionedClassManager,
                Collections.singletonList(emptyClassManager),
                emptyClassManager,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleClassManagerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);

        versionedClassManager.commit();
        assertClassManagerListStatus(versionedClassManager,
                Arrays.asList(emptyClassManager, classManagerWithAmy, classManagerWithBob),
                classManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleClassManagerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 2);

        versionedClassManager.commit();
        assertClassManagerListStatus(versionedClassManager,
                Collections.singletonList(emptyClassManager),
                emptyClassManager,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleClassManagerPointerAtEndOfStateList_returnsTrue() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);

        assertTrue(versionedClassManager.canUndo());
    }

    @Test
    public void canUndo_multipleClassManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 1);

        assertTrue(versionedClassManager.canUndo());
    }

    @Test
    public void canUndo_singleClassManager_returnsFalse() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);

        assertFalse(versionedClassManager.canUndo());
    }

    @Test
    public void canUndo_multipleClassManagerPointerAtStartOfStateList_returnsFalse() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 2);

        assertFalse(versionedClassManager.canUndo());
    }

    @Test
    public void canRedo_multipleClassManagerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 1);

        assertTrue(versionedClassManager.canRedo());
    }

    @Test
    public void canRedo_multipleClassManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 2);

        assertTrue(versionedClassManager.canRedo());
    }

    @Test
    public void canRedo_singleClassManager_returnsFalse() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);

        assertFalse(versionedClassManager.canRedo());
    }

    @Test
    public void canRedo_multipleClassManagerPointerAtEndOfStateList_returnsFalse() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);

        assertFalse(versionedClassManager.canRedo());
    }

    @Test
    public void undo_multipleClassManagerPointerAtEndOfStateList_success() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);

        versionedClassManager.undo();
        assertClassManagerListStatus(versionedClassManager,
                Collections.singletonList(emptyClassManager),
                classManagerWithAmy,
                Collections.singletonList(classManagerWithBob));
    }

    @Test
    public void undo_multipleClassManagerPointerNotAtStartOfStateList_success() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 1);

        versionedClassManager.undo();
        assertClassManagerListStatus(versionedClassManager,
                Collections.emptyList(),
                emptyClassManager,
                Arrays.asList(classManagerWithAmy, classManagerWithBob));
    }

    @Test
    public void undo_singleClassManager_throwsNoUndoableStateException() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);

        assertThrows(VersionedClassManager.NoUndoableStateException.class, versionedClassManager::undo);
    }

    @Test
    public void undo_multipleClassManagerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 2);

        assertThrows(VersionedClassManager.NoUndoableStateException.class, versionedClassManager::undo);
    }

    @Test
    public void redo_multipleClassManagerPointerNotAtEndOfStateList_success() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 1);

        versionedClassManager.redo();
        assertClassManagerListStatus(versionedClassManager,
                Arrays.asList(emptyClassManager, classManagerWithAmy),
                classManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleClassManagerPointerAtStartOfStateList_success() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 2);

        versionedClassManager.redo();
        assertClassManagerListStatus(versionedClassManager,
                Collections.singletonList(emptyClassManager),
                classManagerWithAmy,
                Collections.singletonList(classManagerWithBob));
    }

    @Test
    public void redo_singleClassManager_throwsNoRedoableStateException() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);

        assertThrows(VersionedClassManager.NoRedoableStateException.class, versionedClassManager::redo);
    }

    @Test
    public void redo_multipleClassManagerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(
                emptyClassManager, classManagerWithAmy, classManagerWithBob);

        assertThrows(VersionedClassManager.NoRedoableStateException.class, versionedClassManager::redo);
    }

    @Test
    public void equals() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(classManagerWithAmy, classManagerWithBob);

        // same values -> returns true
        VersionedClassManager copy = prepareClassManagerList(classManagerWithAmy, classManagerWithBob);
        assertTrue(versionedClassManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedClassManager.equals(versionedClassManager));

        // null -> returns false
        assertFalse(versionedClassManager.equals(null));

        // different types -> returns false
        assertFalse(versionedClassManager.equals(0));

        // different state list -> returns false
        VersionedClassManager differentClassManagerList =
                prepareClassManagerList(classManagerWithBob, classManagerWithCarl);
        assertFalse(versionedClassManager.equals(differentClassManagerList));

        // different current pointer index -> returns false
        VersionedClassManager differentCurrentStatePointer = prepareClassManagerList(
                classManagerWithAmy, classManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedClassManager, 1);
        assertFalse(versionedClassManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedClassManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedClassManager#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedClassManager#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertClassManagerListStatus(VersionedClassManager versionedClassManager,
                                             List<ReadOnlyClassManager> expectedStatesBeforePointer,
                                             ReadOnlyClassManager expectedCurrentState,
                                             List<ReadOnlyClassManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ClassManager(versionedClassManager), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedClassManager.canUndo()) {
            versionedClassManager.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyClassManager expectedClassManager : expectedStatesBeforePointer) {
            assertEquals(expectedClassManager, new ClassManager(versionedClassManager));
            versionedClassManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyClassManager expectedClassManager : expectedStatesAfterPointer) {
            versionedClassManager.redo();
            assertEquals(expectedClassManager, new ClassManager(versionedClassManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedClassManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedClassManager.undo());
    }

    /**
     * Creates and returns a {@code versionedClassManager} with the {@code ClassManagerStates} added into it, and the
     * {@code versionedClassManager#currentStatePointer} at the end of list.
     */
    private VersionedClassManager prepareClassManagerList(ReadOnlyClassManager... classManagerStates) {
        assertFalse(classManagerStates.length == 0);

        VersionedClassManager versionedClassManager = new VersionedClassManager(classManagerStates[0]);
        for (int i = 1; i < classManagerStates.length; i++) {
            versionedClassManager.resetData(classManagerStates[i]);
            versionedClassManager.commit();
        }

        return versionedClassManager;
    }

    /**
     * Shifts the {@code versionedClassManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedClassManager versionedClassManager, int count) {
        for (int i = 0; i < count; i++) {
            versionedClassManager.undo();
        }
    }

    @Test
    public void equalsMethod() {
        VersionedClassManager versionedClassManager = prepareClassManagerList(emptyClassManager);
        VersionedClassManager newVersionedClassManager = prepareClassManagerList(emptyClassManager);

        // same object -> returns true
        assertTrue(versionedClassManager.equals(versionedClassManager));

        // same value -> returns true
        assertTrue(versionedClassManager.equals(newVersionedClassManager));

        // null -> return false
        assertFalse(versionedClassManager.equals(null));

        newVersionedClassManager.addStudent(AMY);
        newVersionedClassManager.setSelectedStudent(AMY);

        // different value -> returns false;
        assertFalse(versionedClassManager.equals(newVersionedClassManager));
    }
}
//@@author
