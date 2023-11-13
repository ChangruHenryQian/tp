package seedu.classmanager.logic.commands;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;

/**
 * Toggles different themes for the class manager.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String SHOWING_THEME_MESSAGE = "Theme has been toggled.";

    /**
     * Toggles between light and dark themes for Class Manager.
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory The command history to record this command.
     * @return A {@code CommandResult} with the feedback message of the operation result.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        model.toggleColourTheme();
        return new CommandResult(SHOWING_THEME_MESSAGE, false, false, false, true);
    }
}
