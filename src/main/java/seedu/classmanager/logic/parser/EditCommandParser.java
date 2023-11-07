package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.classmanager.logic.commands.EditCommand;
import seedu.classmanager.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_STUDENT_NUMBER, PREFIX_CLASS_NUMBER);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_NUMBER,
                PREFIX_CLASS_NUMBER);

        if (argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_STUDENT_NUMBER, PREFIX_CLASS_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        StudentNumber studentNumber;
        try {
            studentNumber = ParserUtil.parseStudentNumber(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editStudentDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_STUDENT_NUMBER).isPresent()) {
            editStudentDescriptor.setStudentNumber(ParserUtil
                    .parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_CLASS_NUMBER).isPresent()) {
            editStudentDescriptor.setClassNumber(ParserUtil
                    .parseClassNumber(argMultimap.getValue(PREFIX_CLASS_NUMBER).get()));
        }

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(studentNumber, editStudentDescriptor);
    }

}
