package seedu.classmanager.logic.commands;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;

/**
 * Toggles different themes for the class manager.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String SHOWING_THEME_MESSAGE = "Theme has been toggled.";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        model.toggleColorTheme();
        return new CommandResult(SHOWING_THEME_MESSAGE, false, false, false, true);
    }
}
