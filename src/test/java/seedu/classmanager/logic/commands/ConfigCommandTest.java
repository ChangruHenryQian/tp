package seedu.classmanager.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.Messages.MESSAGE_USER_PREFS_CANNOT_LOAD;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.ConfigCommand.MESSAGE_CONFIG_SUCCESS;
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
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.storage.JsonUserPrefsStorage;
import seedu.classmanager.storage.UserPrefsStorage;

public class ConfigCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void configure_validArg_success() throws CommandException {
        int tutorialCount = 26;
        int assignmentCount = 5;
        ConfigCommand configCommand = new ConfigCommand(tutorialCount, assignmentCount);

        Path userPrefsPath = Paths.get(
                "src", "test", "data", "JsonUserPrefsStorageTest", "TypicalUserPref.json");
        Model model = new ModelManager(getTypicalClassManager(), getUserPrefs(userPrefsPath));

        Path validUserPrefsPath = Paths.get(
                "src", "test", "data", "JsonUserPrefsStorageTest", "AfterConfiguredUserPref.json");
        Model expectedModel = new ModelManager(getTypicalClassManager(), getUserPrefs(validUserPrefsPath));

        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_CONFIG_SUCCESS, tutorialCount, assignmentCount));

        assertCommandSuccess(configCommand, model, expectedCommandResult, expectedModel, commandHistory);
        ClassDetails.setTutorialCount(13);
        ClassDetails.setAssignmentCount(6);
    }

    public UserPrefs getUserPrefs(Path userPrefsPath) throws CommandException {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Optional<UserPrefs> userPrefsOptional;
        try {
            userPrefsOptional = userPrefsStorage.readUserPrefs();
            return userPrefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_USER_PREFS_CANNOT_LOAD);
        }
    }

    @Test
    public void loadString() {
        int tutorialCount = 3;
        int assignmentCount = 2;
        ConfigCommand configCommand = new ConfigCommand(tutorialCount, assignmentCount);
        String expectedString = "seedu.classmanager.logic.commands.ConfigCommand{tutorialCount=" + tutorialCount + ", "
                + "assignmentCount=" + assignmentCount + "}";
        assertEquals(configCommand.toString(), expectedString);
        ClassDetails.setTutorialCount(13);
        ClassDetails.setAssignmentCount(6);
    }

    @Test
    public void equals() {
        ConfigCommand configCommand = new ConfigCommand(3, 2);
        ConfigCommand diffValueConfigCommand = new ConfigCommand(3, 3);
        ConfigCommand otherConfigCommand = new ConfigCommand(2, 2);

        // same object -> returns true
        assertTrue(configCommand.equals(configCommand));

        // same values -> returns true
        ConfigCommand configCommandCopy = new ConfigCommand(3, 2);
        assertTrue(configCommand.equals(configCommandCopy));

        // different types -> returns false
        assertFalse(configCommand.equals(1));

        // null -> returns false
        assertFalse(configCommand.equals(null));

        // different values -> returns false
        assertFalse(configCommand.equals(diffValueConfigCommand));
        assertFalse(configCommand.equals(otherConfigCommand));
        ClassDetails.setTutorialCount(13);
        ClassDetails.setAssignmentCount(6);
    }

    @Test
    public void test_hashCode() {
        ConfigCommand configCommand = new ConfigCommand(3, 2);
        ConfigCommand otherConfigCommand = new ConfigCommand(2, 3);

        // same object -> returns true
        assertTrue(configCommand.hashCode() == configCommand.hashCode());

        // same values -> returns true
        ConfigCommand configCommandCopy = new ConfigCommand(3, 2);
        assertTrue(configCommand.hashCode() == configCommandCopy.hashCode());

        // different types -> returns false
        assertFalse(configCommand.hashCode() == 1);

        // null -> returns false
        assertFalse(configCommand.hashCode() == 0);

        // different student -> returns false
        assertFalse(configCommand.hashCode() == otherConfigCommand.hashCode());
        ClassDetails.setTutorialCount(13);
        ClassDetails.setAssignmentCount(6);
    }
}
