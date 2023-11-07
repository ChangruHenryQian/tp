package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.SetGradeCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new SetGradeCommand object
 */
public class SetGradeCommandParser implements Parser<SetGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetGradeCommand
     * and returns an SetGradeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_NUMBER,
               PREFIX_ASSIGNMENT, PREFIX_GRADE);

        if (!argMultimap.arePrefixesPresent(PREFIX_STUDENT_NUMBER, PREFIX_ASSIGNMENT, PREFIX_GRADE)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_STUDENT_NUMBER, PREFIX_ASSIGNMENT, PREFIX_GRADE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGradeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_NUMBER, PREFIX_ASSIGNMENT, PREFIX_GRADE);
        StudentNumber studentNumber = ParserUtil.parseStudentNumber(
                argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());
        Index assignmentIndex;
        try {
            assignmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSIGNMENT).get());
        } catch (ParseException e) {
            throw new ParseException(ClassDetails.getMessageInvalidAssignmentIndex());
        }

        int grade;
        try {
            grade = Integer.parseInt(argMultimap.getValue(PREFIX_GRADE).get());
        } catch (NumberFormatException e) {
            throw new ParseException(ClassDetails.MESSAGE_INVALID_GRADE);
        }
        return new SetGradeCommand(studentNumber, assignmentIndex, grade);
    }

}
