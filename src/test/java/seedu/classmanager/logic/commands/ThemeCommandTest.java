package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.classmanager.logic.commands.ThemeCommand.SHOWING_THEME_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;

public class ThemeCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_THEME_MESSAGE,
                false, false, false, true);
        assertCommandSuccess(new ThemeCommand(), model, expectedCommandResult, expectedModel, commandHistory);
    }
}
