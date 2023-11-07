package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.classmanager.logic.commands.DeleteCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT_NUMBER);

        if (!argMultimap.arePrefixesPresent(PREFIX_STUDENT_NUMBER)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_STUDENT_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_NUMBER);

        StudentNumber studentNumber = ParserUtil.parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());
        return new DeleteCommand(studentNumber);
    }
}
