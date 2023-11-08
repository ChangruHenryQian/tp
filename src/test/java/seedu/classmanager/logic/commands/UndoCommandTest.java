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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalClassManagerMore(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClassManagerMore(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        // set up of models' undo/redo history. Class Manager supports up to 9 undos.
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
    }

    @Test
    public void execute() {
        // 9 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 8 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 7 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 6 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 5 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 4 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 3 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // 2 undoable states in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // single undoable state in model
        expectedModel.undoClassManager();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE, commandHistory);
    }
}
//@@author
