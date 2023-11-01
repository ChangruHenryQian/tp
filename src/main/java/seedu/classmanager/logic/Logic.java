package seedu.classmanager.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.logic.commands.CommandResult;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.student.Student;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ClassManager.
     *
     * @see seedu.classmanager.model.Model#getClassManager()
     */
    ReadOnlyClassManager getClassManager();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /** Returns view of selected student. */
    ObservableList<Student> getSelectedStudent();

    /** Sets a student to be selected to view class details. */
    void setSelectedStudent(Student student);

    /**
     * Returns the user prefs' Class Manager file path.
     */
    Path getClassManagerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' theme.
     */
    String getTheme();
}
