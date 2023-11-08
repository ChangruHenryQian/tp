//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with some modifications
package seedu.classmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ClassManager} that keeps track of its own history.
 */
public class VersionedClassManager extends ClassManager {

    private final List<ReadOnlyClassManager> classManagerStateList;
    private final int stateListSize = 10;
    private int currentStatePointer;

    /**
     * Initializes a VersionedClassManager with the given {@code ReadOnlyClassManager}.
     * @param initialState initial state of Class Manager
     */
    public VersionedClassManager(ReadOnlyClassManager initialState) {
        super(initialState);

        classManagerStateList = new ArrayList<>(stateListSize);
        classManagerStateList.add(new ClassManager(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ClassManager} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        if (classManagerStateList.size() == stateListSize) {
            classManagerStateList.remove(0);
        }
        classManagerStateList.add(new ClassManager(this));
        if (currentStatePointer < stateListSize - 1) {
            currentStatePointer++;
        }
        resetData(classManagerStateList.get(currentStatePointer));
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        classManagerStateList.subList(currentStatePointer + 1, classManagerStateList.size()).clear();
    }

    /**
     * Resets class manager after a load command.
     */
    public void loadReset(ReadOnlyClassManager newData) {
        classManagerStateList.clear();
        classManagerStateList.add(newData);
        currentStatePointer = 0;
        resetData(classManagerStateList.get(currentStatePointer));
    }

    /**
     * Removes all states other than the current state after a config command.
     */
    public void configReset() {
        // Removes all states before the current state, if any
        if (currentStatePointer > 0) {
            classManagerStateList.subList(0, currentStatePointer).clear();
        }
        // Removes all states after the current state, if any
        if (classManagerStateList.size() > 1) {
            classManagerStateList.subList(1, classManagerStateList.size()).clear();
        }
        currentStatePointer = 0;
    }

    /**
     * Restores Class Manager to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(classManagerStateList.get(currentStatePointer));
    }

    /**
     * Restores Class Manager to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(classManagerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has Class Manager states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has Class Manager states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < classManagerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedClassManager)) {
            return false;
        }

        VersionedClassManager otherVersionedClassManager = (VersionedClassManager) other;

        // state check
        return super.equals(otherVersionedClassManager)
                && classManagerStateList.equals(otherVersionedClassManager.classManagerStateList)
                && currentStatePointer == otherVersionedClassManager.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of classManagerStateList, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of classManagerStateList, unable to redo.");
        }
    }
}
//@@author
