package seedu.classmanager.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.ALICE;
import static seedu.classmanager.testutil.TypicalStudents.HOON;
import static seedu.classmanager.testutil.TypicalStudents.IDA;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.ReadOnlyClassManager;

public class JsonClassManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClassManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClassManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClassManager(null));
    }

    private java.util.Optional<ReadOnlyClassManager> readClassManager(String filePath) throws Exception {
        return new JsonClassManagerStorage(Paths.get(filePath)).readClassManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClassManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readClassManager("notJsonFormatClassManager.json"));
    }

    @Test
    public void readClassManager_invalidStudentClassManager_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClassManager("invalidStudentClassManager.json"));
    }

    @Test
    public void readClassManager_invalidAndValidStudentClassManager_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClassManager("invalidAndValidStudentClassManager.json"));
    }

    @Test
    public void readAndSaveClassManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClassManager.json");
        ClassManager original = getTypicalClassManager();
        JsonClassManagerStorage jsonClassManagerStorage = new JsonClassManagerStorage(filePath);

        // Save in new file and read back
        jsonClassManagerStorage.saveClassManager(original, filePath);
        ReadOnlyClassManager readBack = jsonClassManagerStorage.readClassManager(filePath).get();
        assertEquals(original, new ClassManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonClassManagerStorage.saveClassManager(original, filePath);
        readBack = jsonClassManagerStorage.readClassManager(filePath).get();
        assertEquals(original, new ClassManager(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonClassManagerStorage.saveClassManager(original); // file path not specified
        readBack = jsonClassManagerStorage.readClassManager().get(); // file path not specified
        assertEquals(original, new ClassManager(readBack));
    }

    @Test
    public void saveClassManager_nullClassManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClassManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code classManager} at the specified {@code filePath}.
     */
    private void saveClassManager(ReadOnlyClassManager classManager, String filePath) {
        try {
            new JsonClassManagerStorage(Paths.get(filePath))
                    .saveClassManager(classManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClassManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClassManager(new ClassManager(), null));
    }
}
