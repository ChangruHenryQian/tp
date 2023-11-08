//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications
package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.CommandTestUtil.deleteFirstStudent;
import static seedu.classmanager.testutil.TypicalStudents.getTypicalClassManagerMore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalClassManagerMore(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClassManagerMore(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history. Class Manager supports up to 9 redos.
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        deleteFirstStudent(model);
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();
        model.undoClassManager();

        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        deleteFirstStudent(expectedModel);
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
        expectedModel.undoClassManager();
    }

    @Test
    public void execute() {
        // 9 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 8 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 7 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 6 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 5 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 4 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 3 redoable states in model
        expectedModel.redoClassManager();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 2 redoable states in model
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
