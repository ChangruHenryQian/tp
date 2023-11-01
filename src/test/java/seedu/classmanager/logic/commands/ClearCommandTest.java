package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;

public class ClearCommandTest {

    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyClassManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitClassManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }

    @Test
    public void execute_nonEmptyClassManager_success() {
        Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalClassManager(), new UserPrefs());
        expectedModel.setClassManager(new ClassManager());
        expectedModel.commitClassManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }

}
