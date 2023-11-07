//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications
package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.CommandTestUtil.deleteFirstStudent;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClassManager(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        model.undoClassManager();
        model.undoClassManager();

        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // single redoable state in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel,
                commandHistory);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE, commandHistory);
    }
}
//@@author
