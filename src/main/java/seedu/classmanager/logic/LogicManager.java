package seedu.classmanager.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.logic.commands.Command;
import seedu.classmanager.logic.commands.CommandResult;
import seedu.classmanager.logic.commands.ConfigCommand;
import seedu.classmanager.logic.commands.LoadCommand;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.logic.parser.ClassManagerParser;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";
    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final ClassManagerParser classManagerParser;
    private boolean classManagerModified;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        classManagerParser = new ClassManagerParser();

        // Set classManagerModified to true whenever the models' Class Manager is modified.
        model.getClassManager().addListener(observable -> classManagerModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        classManagerModified = false;
        Command command;

        CommandResult commandResult;
        try {
            command = classManagerParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (command instanceof LoadCommand | command instanceof ConfigCommand) {
            try {
                storage.saveClassManager(model.getClassManager(), model.getClassManagerFilePath());
                storage.saveUserPrefs(model.getUserPrefs());
                if (command instanceof LoadCommand) {
                    logger.info("Class Manager has loaded the save file.");
                } else {
                    logger.info("Class Manager has been configured.");
                }
            } catch (AccessDeniedException e) {
                throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
            } catch (IOException ioe) {
                throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
            }
        } else if (classManagerModified) {
            logger.info("Class Manager modified, saving to file.");
            try {
                storage.saveClassManager(model.getClassManager(), model.getClassManagerFilePath());
            } catch (AccessDeniedException e) {
                throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
            } catch (IOException ioe) {
                throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyClassManager getClassManager() {
        return model.getClassManager();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Student> getSelectedStudent() {
        return model.getSelectedStudent();
    }

    @Override
    public void setSelectedStudent(Student student) {
        model.setSelectedStudent(student);
    }

    @Override
    public Path getClassManagerFilePath() {
        return model.getClassManagerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public String getTheme() {
        return model.getTheme();
    }
}
