package seedu.classmanager.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.ReadOnlyUserPrefs;
import seedu.classmanager.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ClassManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getClassManagerFilePath();

    @Override
    Optional<ReadOnlyClassManager> readClassManager() throws DataLoadingException;

    @Override
    void saveClassManager(ReadOnlyClassManager classManager) throws IOException;

}
