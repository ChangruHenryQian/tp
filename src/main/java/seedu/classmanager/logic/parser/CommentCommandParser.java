package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.classmanager.commons.exceptions.IllegalValueException;
import seedu.classmanager.logic.commands.CommentCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new CommentCommand object.
 */
public class CommentCommandParser implements Parser<CommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CommentCommand
     * @param args User input
     * @return CommentCommand with parsed values
     * @throws ParseException if the user input does not conform the expected format
     */
    public CommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_NUMBER, PREFIX_COMMENT);
        String a = argMultimap.getPreamble();
        if (!argMultimap.arePrefixesPresent(PREFIX_STUDENT_NUMBER, PREFIX_COMMENT)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_STUDENT_NUMBER, PREFIX_COMMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
        }

        StudentNumber studentNumber;
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_NUMBER, PREFIX_COMMENT);
            studentNumber = ParserUtil.parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE), ive);
        }

        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).orElse(""));

        return new CommentCommand(studentNumber, comment);
    }

}
