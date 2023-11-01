package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_COUNT;

import java.util.Objects;

import seedu.classmanager.commons.util.ToStringBuilder;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.ClassDetails;

/**
 * Configures Class Manager with the module information.
 */
public class ConfigCommand extends Command {

    public static final String COMMAND_WORD = "config";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Configures Class Manager with the module information.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_COUNT + "TUTORIAL_COUNT "
            + PREFIX_ASSIGNMENT_COUNT + "ASSIGNMENT_COUNT\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_COUNT + "13 "
            + PREFIX_ASSIGNMENT_COUNT + "4";
    public static final String MESSAGE_CONFIG_SUCCESS = "Class Manager has been configured with the following "
            + "information:\n"
            + "Tutorial Count: %1$d\n"
            + "Assignment Count: %2$d\n";
    public static final String MESSAGE_CONFIG_FAILED = "Class Manager has failed to be configured.\n"
            + "Please try entering the config command again.\n";

    private final int tutorialCount;
    private final int assignmentCount;

    /**
     * Constructor for {@code ConfigCommand}
     * @param tutorialCount Number of tutorials in module
     * @param assignmentCount Number of assignments in module
     */
    public ConfigCommand(int tutorialCount, int assignmentCount) {
        this.tutorialCount = tutorialCount;
        this.assignmentCount = assignmentCount;
    }

    /**
     * Configures {@code ClassDetails} with module information.
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} with the success message.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        try {
            requireNonNull(model);
            ClassDetails.setTutorialCount(tutorialCount);
            ClassDetails.setAssignmentCount(assignmentCount);
            model.setConfigured(true);
            model.setAssignmentCount(assignmentCount);
            model.setTutorialCount(tutorialCount);
            return new CommandResult(String.format(MESSAGE_CONFIG_SUCCESS, tutorialCount, assignmentCount));
        } catch (Exception e) {
            return new CommandResult(MESSAGE_CONFIG_FAILED);
        }
    }

    /**
     * Checks if two {@code ConfigCommand}s are equal.
     * @return True if two {@code ConfigCommand}s are equal.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialCount", tutorialCount)
                .add("assignmentCount", assignmentCount)
                .toString();
    }

    /**
     * Checks if two {@code ConfigCommand}s are equal.
     * @param other Object to compare with.
     * @return True if two {@code ConfigCommand}s are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConfigCommand)) {
            return false;
        }

        ConfigCommand e = (ConfigCommand) other;
        return tutorialCount == e.tutorialCount
                && assignmentCount == e.assignmentCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorialCount, assignmentCount);
    }
}
