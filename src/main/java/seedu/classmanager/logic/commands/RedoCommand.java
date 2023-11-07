//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications
package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;

/**
 * Reverts the {@code model}'s Class Manager to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    /**
     * Executes a redo command and reverts to the previously undone Class Manager.
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult that indicates a successful redo.
     * @throws CommandException if there is no more commands to redo.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoClassManager()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoClassManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
