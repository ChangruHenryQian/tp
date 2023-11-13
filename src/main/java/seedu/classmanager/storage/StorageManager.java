//@@author Cikguseven-reused
//Refactored from AddressBook-Level 3 (https://github.com/se-edu/addressbook-level3)
// Not supposed to own code in file.
package seedu.classmanager.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.ReadOnlyUserPrefs;
import seedu.classmanager.model.UserPrefs;

/**
 * Manages storage of ClassManager data in local storage.
 */
public class StorageManager implements Storage {

    private static Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final ClassManagerStorage classManagerStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ClassManagerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ClassManagerStorage classManagerStorage, UserPrefsStorage userPrefsStorage) {
        this.classManagerStorage = classManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ClassManager methods ==============================

    @Override
    public Path getClassManagerFilePath() {
        return classManagerStorage.getClassManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyClassManager> readClassManager() throws DataLoadingException {
        return readClassManager(classManagerStorage.getClassManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyClassManager> readClassManager(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return classManagerStorage.readClassManager(filePath);
    }

    @Override
    public void saveClassManager(ReadOnlyClassManager classManager) throws IOException {
        saveClassManager(classManager, classManagerStorage.getClassManagerFilePath());
    }

    @Override
    public void saveClassManager(ReadOnlyClassManager classManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        classManagerStorage.saveClassManager(classManager, filePath);
    }

}
//@@author
