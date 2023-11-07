package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.RandomCommand.MESSAGE_INVALID_NUM_OF_STUDENTS;

import seedu.classmanager.logic.commands.RandomCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RandomCommand object
 */
public class RandomCommandParser implements Parser<RandomCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RandomCommand
     * and returns a RandomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RandomCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RandomCommand.MESSAGE_USAGE));
        }

        int numOfStudent;
        try {
            numOfStudent = Integer.parseInt(argMultimap.getPreamble());
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_STUDENTS);
        }

        return new RandomCommand(numOfStudent);
    }
}
