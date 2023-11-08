package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.LoadCommand.MESSAGE_FILE_CANNOT_LOAD;
import static seedu.classmanager.logic.commands.LoadCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.classmanager.logic.commands.LoadCommand.MESSAGE_LOAD_SUCCESS;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.util.SampleDataUtil;
import seedu.classmanager.storage.ClassManagerStorage;
import seedu.classmanager.storage.JsonClassManagerStorage;

public class LoadCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLoadCommandTest");
    private final Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void load_validFile_success() throws CommandException {
        String validFileName = "validClassManager";
        Path validFilePath = TEST_DATA_FOLDER.resolve(validFileName + ".json");
        LoadCommand loadCommand = new LoadCommand(validFileName, validFilePath);
        Model expectedModel = new ModelManager();
        loadClassManager(expectedModel, validFileName, validFilePath);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_LOAD_SUCCESS, validFileName), false, false, true, false);
        assertCommandSuccess(loadCommand, model, expectedCommandResult, expectedModel, commandHistory);
    }

    @Test
    public void load_missingFile_throwsCommandException() {
        String missingFileName = "missingClassManager";
        Path missingFilePath = TEST_DATA_FOLDER.resolve(missingFileName + ".json");
        LoadCommand loadCommand = new LoadCommand(missingFileName, missingFilePath);
        assertCommandFailure(
                loadCommand, model, String.format(MESSAGE_FILE_NOT_FOUND, missingFileName), commandHistory);
    }

    @Test
    public void load_corruptFile_throwsCommandException() {
        String corruptFileName = "NotJsonFormatClassManager";
        Path corruptFilePath = TEST_DATA_FOLDER.resolve(corruptFileName + ".json");
        LoadCommand loadCommand = new LoadCommand(corruptFileName, corruptFilePath);
        assertCommandFailure(
                loadCommand, model, String.format(MESSAGE_FILE_CANNOT_LOAD, corruptFileName), commandHistory);
    }

    public void loadClassManager(Model expectedModel, String fileName, Path filePath) throws CommandException {
        ClassManagerStorage tempClassManagerStorage = new JsonClassManagerStorage(filePath);
        Optional<ReadOnlyClassManager> classManagerOptional;
        ReadOnlyClassManager tempData;
        try {
            classManagerOptional = tempClassManagerStorage.readClassManager();
            tempData = classManagerOptional.orElseGet(SampleDataUtil::getSampleClassManager);
        } catch (DataLoadingException e) {
            throw new CommandException(String.format(MESSAGE_FILE_CANNOT_LOAD, fileName));
        }
        expectedModel.setClassManagerFilePath(filePath);
        expectedModel.loadReset(tempData);
    }

    @Test
    public void loadString() {
        String fileName = "validClassManager";
        Path filePath = TEST_DATA_FOLDER.resolve(fileName + ".json");
        LoadCommand loadCommand = new LoadCommand(fileName, filePath);
        String expectedString = "seedu.classmanager.logic.commands.LoadCommand{load=" + fileName + "}";
        assertEquals(loadCommand.toString(), expectedString);
    }

    @Test
    public void equals() {
        String firstFileName = "validClassManager";
        Path firstFilePath = TEST_DATA_FOLDER.resolve(firstFileName + ".json");

        String secondFileName = "classManager";
        Path secondFilePath = TEST_DATA_FOLDER.resolve(secondFileName + ".json");

        LoadCommand loadFirstCommand = new LoadCommand(firstFileName, firstFilePath);
        LoadCommand loadSecondCommand = new LoadCommand(secondFileName, secondFilePath);

        // same object -> returns true
        assertTrue(loadFirstCommand.equals(loadFirstCommand));

        // same values -> returns true
        LoadCommand loadFirstCommandCopy = new LoadCommand(firstFileName, firstFilePath);
        assertTrue(loadFirstCommand.equals(loadFirstCommandCopy));

        // different types -> returns false
        assertFalse(loadFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loadFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(loadFirstCommand.equals(loadSecondCommand));
    }

    @Test
    public void test_hashCode() {
        String firstFileName = "validClassManager";
        Path firstFilePath = TEST_DATA_FOLDER.resolve(firstFileName + ".json");

        String secondFileName = "classManager";
        Path secondFilePath = TEST_DATA_FOLDER.resolve(secondFileName + ".json");

        LoadCommand loadFirstCommand = new LoadCommand(firstFileName, firstFilePath);
        LoadCommand loadSecondCommand = new LoadCommand(secondFileName, secondFilePath);

        // same object -> returns true
        assertTrue(loadFirstCommand.hashCode() == loadFirstCommand.hashCode());

        // same values -> returns true
        LoadCommand loadFirstCommandCopy = new LoadCommand(firstFileName, firstFilePath);
        assertTrue(loadFirstCommand.hashCode() == loadFirstCommandCopy.hashCode());

        // different types -> returns false
        assertFalse(loadFirstCommand.hashCode() == 1);

        // null -> returns false
        assertFalse(loadFirstCommand.hashCode() == 0);

        // different student -> returns false
        assertFalse(loadFirstCommand.hashCode() == loadSecondCommand.hashCode());
    }
}
