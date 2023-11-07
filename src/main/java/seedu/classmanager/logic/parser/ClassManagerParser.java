package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.logic.commands.AddCommand;
import seedu.classmanager.logic.commands.ClearCommand;
import seedu.classmanager.logic.commands.Command;
import seedu.classmanager.logic.commands.CommentCommand;
import seedu.classmanager.logic.commands.ConfigCommand;
import seedu.classmanager.logic.commands.DeleteCommand;
import seedu.classmanager.logic.commands.EditCommand;
import seedu.classmanager.logic.commands.ExitCommand;
import seedu.classmanager.logic.commands.HelpCommand;
import seedu.classmanager.logic.commands.HistoryCommand;
import seedu.classmanager.logic.commands.ListCommand;
import seedu.classmanager.logic.commands.LoadCommand;
import seedu.classmanager.logic.commands.LookupCommand;
import seedu.classmanager.logic.commands.MarkAbsentAllCommand;
import seedu.classmanager.logic.commands.MarkAbsentCommand;
import seedu.classmanager.logic.commands.MarkPresentAllCommand;
import seedu.classmanager.logic.commands.MarkPresentCommand;
import seedu.classmanager.logic.commands.RandomCommand;
import seedu.classmanager.logic.commands.RecordClassParticipationCommand;
import seedu.classmanager.logic.commands.RedoCommand;
import seedu.classmanager.logic.commands.SetGradeCommand;
import seedu.classmanager.logic.commands.TagCommand;
import seedu.classmanager.logic.commands.ThemeCommand;
import seedu.classmanager.logic.commands.UndoCommand;
import seedu.classmanager.logic.commands.ViewCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ClassManagerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ClassManagerParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case CommentCommand.COMMAND_WORD:
            return new CommentCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case LookupCommand.COMMAND_WORD:
            return new LookupCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        case SetGradeCommand.COMMAND_WORD:
            return new SetGradeCommandParser().parse(arguments);

        case RecordClassParticipationCommand.COMMAND_WORD:
            return new RecordClassParticipationCommandParser().parse(arguments);

        case MarkPresentCommand.COMMAND_WORD:
            return new MarkPresentCommandParser().parse(arguments);

        case MarkPresentAllCommand.COMMAND_WORD:
            return new MarkPresentAllCommandParser().parse(arguments);

        case MarkAbsentCommand.COMMAND_WORD:
            return new MarkAbsentCommandParser().parse(arguments);

        case MarkAbsentAllCommand.COMMAND_WORD:
            return new MarkAbsentAllCommandParser().parse(arguments);

        case RandomCommand.COMMAND_WORD:
            return new RandomCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ConfigCommand.COMMAND_WORD:
            return new ConfigCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
