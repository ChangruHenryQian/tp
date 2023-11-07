package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.RecordClassParticipationCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new RecordClassParticipationCommand object
 */
public class RecordClassParticipationCommandParser implements Parser<RecordClassParticipationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordClassParticipationCommand
     * and returns an SetGradeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordClassParticipationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_NUMBER,
                PREFIX_TUTORIAL_INDEX, PREFIX_PARTICIPATION);

        if (!argMultimap.arePrefixesPresent(PREFIX_STUDENT_NUMBER, PREFIX_TUTORIAL_INDEX, PREFIX_PARTICIPATION)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_STUDENT_NUMBER,
                PREFIX_TUTORIAL_INDEX, PREFIX_PARTICIPATION)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordClassParticipationCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_NUMBER,
                PREFIX_TUTORIAL_INDEX, PREFIX_PARTICIPATION);
        StudentNumber studentNumber = ParserUtil.parseStudentNumber(
                argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());
        Index tutorialIndex;
        try {
            tutorialIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());
        } catch (ParseException e) {
            throw new ParseException(ClassDetails.getMessageInvalidTutorialIndex());
        }

        String participation = argMultimap.getValue(PREFIX_PARTICIPATION).get();
        if (!participation.equalsIgnoreCase("true")
                && !participation.equalsIgnoreCase("false")) {
            throw new ParseException(ClassDetails.MESSAGE_INVALID_PARTICIPATION);
        }
        boolean hasParticipated = Boolean.parseBoolean(participation);
        return new RecordClassParticipationCommand(studentNumber, tutorialIndex, hasParticipated);
    }

}
