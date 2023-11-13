//@@author Cikguseven-reused
//Refactored from AddressBook-Level 3 (https://github.com/se-edu/addressbook-level3)
// Not supposed to own code in file.
package seedu.classmanager.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.commons.util.FileUtil;
import seedu.classmanager.commons.util.JsonUtil;
import seedu.classmanager.model.ReadOnlyClassManager;

/**
 * A class to access ClassManager data stored as a json file on the hard disk.
 */
public class JsonClassManagerStorage implements ClassManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonClassManagerStorage.class);

    private final Path filePath;

    public JsonClassManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClassManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClassManager> readClassManager() throws DataLoadingException {
        return readClassManager(filePath);
    }

    /**
     * Similar to {@link #readClassManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyClassManager> readClassManager(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableClassManager> jsonClassManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableClassManager.class);
        if (jsonClassManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonClassManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveClassManager(ReadOnlyClassManager classManager) throws IOException {
        saveClassManager(classManager, filePath);
    }

    /**
     * Similar to {@link #saveClassManager(ReadOnlyClassManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveClassManager(ReadOnlyClassManager classManager, Path filePath) throws IOException {
        requireNonNull(classManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableClassManager(classManager), filePath);
    }
}
//@@author
