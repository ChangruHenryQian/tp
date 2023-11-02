package seedu.classmanager.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classmanager.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.commons.util.JsonUtil;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.testutil.TypicalStudents;

public class JsonSerializableClassManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableClassManagerTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsClassManager.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentClassManager.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentClassManager.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableClassManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableClassManager.class).get();
        ClassManager classManagerFromFile = dataFromFile.toModelType();
        ClassManager typicalStudentsClassManager = TypicalStudents.getTypicalClassManager();
        assertEquals(classManagerFromFile, typicalStudentsClassManager);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableClassManager dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableClassManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableClassManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableClassManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableClassManager.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

}
