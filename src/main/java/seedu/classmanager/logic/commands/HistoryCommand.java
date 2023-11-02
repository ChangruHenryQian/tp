package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    /**
     * Executes the command which lists all the commands entered by user from the start of app launch.
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult} that lists all the commands entered by user from the start of app launch.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> previousCommands = new ArrayList<>(history.getHistory());

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }
}
