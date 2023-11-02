package seedu.classmanager.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.model.ReadOnlyClassManager;

/**
 * Represents a storage for {@link seedu.classmanager.model.ClassManager}.
 */
public interface ClassManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClassManagerFilePath();

    /**
     * Returns ClassManager data as a {@link ReadOnlyClassManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyClassManager> readClassManager() throws DataLoadingException;

    /**
     * @see #getClassManagerFilePath()
     */
    Optional<ReadOnlyClassManager> readClassManager(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyClassManager} to the storage.
     * @param classManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClassManager(ReadOnlyClassManager classManager) throws IOException;

    /**
     * @see #saveClassManager(ReadOnlyClassManager)
     */
    void saveClassManager(ReadOnlyClassManager classManager, Path filePath) throws IOException;

}
