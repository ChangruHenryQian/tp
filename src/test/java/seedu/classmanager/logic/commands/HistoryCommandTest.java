package seedu.classmanager.logic.commands;

import static seedu.classmanager.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel,
                commandHistory);

        String command1 = "clear";
        commandHistory.add(command1);
        assertCommandSuccess(new HistoryCommand(), model,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel, commandHistory);

        String command2 = "randomCommand";
        String command3 = "select 1";
        commandHistory.add(command2);
        commandHistory.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, expectedMessage, expectedModel, commandHistory);
    }
}
