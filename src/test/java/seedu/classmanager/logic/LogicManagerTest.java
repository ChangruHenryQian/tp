package seedu.classmanager.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.LogicManager.FILE_OPS_ERROR_FORMAT;
import static seedu.classmanager.logic.Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER;
import static seedu.classmanager.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.testutil.Assert.assertThrows;
import static seedu.classmanager.testutil.TypicalStudents.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.logic.commands.AddCommand;
import seedu.classmanager.logic.commands.CommandResult;
import seedu.classmanager.logic.commands.ConfigCommand;
import seedu.classmanager.logic.commands.HistoryCommand;
import seedu.classmanager.logic.commands.ListCommand;
import seedu.classmanager.logic.commands.LoadCommand;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.ReadOnlyUserPrefs;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.util.SampleDataUtil;
import seedu.classmanager.storage.ClassManagerStorage;
import seedu.classmanager.storage.JsonClassManagerStorage;
import seedu.classmanager.storage.JsonUserPrefsStorage;
import seedu.classmanager.storage.StorageManager;
import seedu.classmanager.testutil.StudentBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager();
    private Logic logic;
    private final int tutorialCount = 13;
    private final int assignmentCount = 6;
    private final String fileName = "logicmanagertest";
    private final String loadCommand = "load f/" + fileName;

    @BeforeEach
    public void setUp() {
        JsonClassManagerStorage classManagerStorage =
                new JsonClassManagerStorage(temporaryFolder.resolve("logicmanagertest.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(classManagerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);

    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete s/A922P";
        assertCommandException(deleteCommand, MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_loadCommand_success() throws CommandException, ParseException {
        assertCommandSuccess(loadCommand, String.format(LoadCommand.MESSAGE_LOAD_SUCCESS, fileName), model);
        assertHistoryCorrect(loadCommand);
    }

    @Test
    public void execute_configCommand_success() throws CommandException, ParseException {
        String configCommand = "config #t/" + tutorialCount + " #a/" + assignmentCount;
        assertCommandSuccess(configCommand,
                String.format(ConfigCommand.MESSAGE_CONFIG_SUCCESS, tutorialCount, assignmentCount), model);
        assertHistoryCorrect(configCommand);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component for
     * Config Commands.
     */
    @Test
    public void execute_configCommand_failure() {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with a UserPrefsStorage that throws the IOException e when saving
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(prefPath) {
            @Override
            public void saveUserPrefs(ReadOnlyUserPrefs userPrefs)
                    throws IOException {
                throw DUMMY_IO_EXCEPTION;
            }
        };

        JsonClassManagerStorage classManagerStorage =
                new JsonClassManagerStorage(temporaryFolder.resolve("ExceptionClassManager.json"));
        StorageManager storage = new StorageManager(classManagerStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveClassManager method by executing an add command
        String configCommand = "config #t/" + tutorialCount + " #a/" + assignmentCount;
        ModelManager expectedModel = new ModelManager();
        assertCommandFailure(configCommand, CommandException.class,
                String.format(FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()), expectedModel);
        assertHistoryCorrect(configCommand);
    }


    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws IOException {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() throws IOException {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void executeLoadCommand_storageThrowsAdException_throwsCommandException() {
        assertLoadCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void executeLoadCommand_storageThrowsIoException_throwsCommandException() {
        assertLoadCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredStudentList().remove(0));
    }

    @Test
    public void getClassManager_success() {
        ReadOnlyClassManager classManager = logic.getClassManager();
        assertNotNull(classManager);
    }

    @Test
    public void getSelectedStudent_success() {
        logic.setSelectedStudent(AMY);
        assertEquals(AMY, logic.getObservableSelectedStudent().get(0));
    }

    @Test
    public void getClassManagerFilePath_success() {
        Path path = logic.getClassManagerFilePath();
        assertNotNull(path);
    }

    @Test
    public void getGuiSettings_success() {
        GuiSettings gui = logic.getGuiSettings();
        assertNotNull(gui);
    }

    @Test
    public void setGuiSettings_success() {
        GuiSettings newGuiSettings = new GuiSettings(400.0, 400.0, 0, 0);
        logic.setGuiSettings(newGuiSettings);

        assertEquals(newGuiSettings, logic.getGuiSettings());
    }

    @Test
    public void getHistory_success() {
        ObservableList<String> history = logic.getHistory();
        assertNotNull(history);
    }

    @Test
    public void getTheme_success() {
        String theme = logic.getTheme();
        assertTrue(theme.equalsIgnoreCase("dark") || theme.equalsIgnoreCase("light"));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getClassManager(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) throws IOException {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with a ClassManagerStorage that throws the IOException e when saving
        JsonClassManagerStorage classManagerStorage = new JsonClassManagerStorage(prefPath) {
            @Override
            public void saveClassManager(ReadOnlyClassManager classManager, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(classManagerStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveClassManager method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY;
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addStudent(expectedStudent);
        expectedModel.commitClassManager();
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
        assertHistoryCorrect(addCommand);
    }

    /**
     * Tests the Logic component's saving of data to the Storage component.
     */
    @Test
    public void assertCommandSuccessWithSave() throws IOException,
            CommandException, ParseException {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        JsonClassManagerStorage classManagerStorage = new JsonClassManagerStorage(prefPath);

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(classManagerStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveClassManager method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY;
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addStudent(expectedStudent);
        expectedModel.commitClassManager();
        storage.saveClassManager(expectedModel.getClassManager(), expectedModel.getClassManagerFilePath());
        assertCommandSuccess(addCommand,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedStudent)), expectedModel);
        assertHistoryCorrect(addCommand);
    }

    private void assertLoadCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with a ClassManagerStorage that throws the IOException e when saving
        JsonClassManagerStorage classManagerStorage = new JsonClassManagerStorage(prefPath) {
            @Override
            public void saveClassManager(ReadOnlyClassManager classManager, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(classManagerStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveClassManager method by executing a load command
        Path filePath = Paths.get("data", this.fileName + ".json");
        ClassManagerStorage tempClassManagerStorage = new JsonClassManagerStorage(filePath);
        Optional<ReadOnlyClassManager> classManagerOptional;
        ReadOnlyClassManager newData;
        try {
            classManagerOptional = tempClassManagerStorage.readClassManager();
            newData = classManagerOptional.orElseGet(SampleDataUtil::getSampleClassManager);
        } catch (DataLoadingException dle) {
            throw new AssertionError("Data should not be invalid", dle);
        }
        ModelManager expectedModel = new ModelManager();
        expectedModel.setClassManagerFilePath(filePath);
        expectedModel.loadReset(newData);
        assertCommandFailure(loadCommand, CommandException.class, expectedMessage, expectedModel);
        assertHistoryCorrect(loadCommand);
    }


    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }
}
