package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.util.SampleDataUtil;
import seedu.classmanager.storage.ClassManagerStorage;
import seedu.classmanager.storage.JsonClassManagerStorage;

/**
 * Loads student information from an existing JSON file in the data folder.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Load student information from an existing JSON file in the data folder. "
            + "The file becomes the new default save file.\n"
            + "Parameters: " + PREFIX_FILE + "FILE_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILE + "export-v1";
    public static final String MESSAGE_LOAD_SUCCESS = "The file %1$s.json has successfully loaded!\n"
            + "This will be the new default save file.\n";
    public static final String MESSAGE_FILE_NOT_FOUND = "The file %1$s.json cannot be found.\n"
            + "Please make sure the file is in the /data folder.\n";
    public static final String MESSAGE_FILE_CANNOT_LOAD = "The file %1$s.json cannot be loaded.\n"
            + "Please make sure the file is formatted correctly.\n"
            + "The number of tutorials and assignments of each student in the file must match"
            + " the current configuration of Class Manager.\n"
            + "You can configure Class Manager before loading the file.";

    private final String fileName;
    private final Path filePath;

    /**
     * Constructor for {@code LoadCommand}
     * @param fileName New save file
     * @param filePath Relative path of the new save file
     */
    public LoadCommand(String fileName, Path filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * Checks if the file exists and loads the file into Class Manager.
     * If the file cannot be loaded or does not exist, an exception is thrown.
     * @param  model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} with the success message.
     * @throws CommandException If the file cannot be loaded or does not exist.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        File f = filePath.toFile();
        if (!f.isFile()) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, fileName));
        }
        requireNonNull(model);
        ClassManagerStorage tempClassManagerStorage = new JsonClassManagerStorage(filePath);
        Optional<ReadOnlyClassManager> classManagerOptional;
        ReadOnlyClassManager newData;
        try {
            classManagerOptional = tempClassManagerStorage.readClassManager();
            newData = classManagerOptional.orElseGet(SampleDataUtil::getSampleClassManager);
        } catch (DataLoadingException e) {
            throw new CommandException(String.format(MESSAGE_FILE_CANNOT_LOAD, fileName));
        }
        model.setClassManagerFilePath(filePath);
        model.loadReset(newData);

        return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, fileName), false, false, true, false);
    }

    /**
     * Returns the string representation of the file path and file name.
     * @return String representation of the file path and file name.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("load", fileName)
                .toString();
    }

    /**
     * Checks if the file path and file name are the same.
     * @param other Object to compare with.
     * @return True if the file path and file name are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoadCommand)) {
            return false;
        }

        LoadCommand e = (LoadCommand) other;
        return filePath.equals(e.filePath) && fileName.equals(e.fileName);
    }

    /**
     * Returns the hashcode of the file path and file name.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(fileName, filePath);
    }
}
