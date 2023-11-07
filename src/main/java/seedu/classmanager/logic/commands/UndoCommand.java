//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications
package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;

/**
 * Reverts the {@code model}'s Class Manager to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    /**
     * Executes an undo command and reverts to the previously saved Class Manager.
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult} that indicates a successful undo.
     * @throws CommandException if there are no more commands to undo.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoClassManager()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoClassManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
