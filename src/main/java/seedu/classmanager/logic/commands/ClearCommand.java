package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;

/**
 * Clears Class Manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Class Manager has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        model.setClassManager(new ClassManager());
        model.resetSelectedStudent();
        model.commitClassManager();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
