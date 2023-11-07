package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkAbsentAllCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.ClassDetails;

/**
 * Parses input arguments and creates a new MarkAbsentAllCommand object
 */
public class MarkAbsentAllCommandParser implements Parser<MarkAbsentAllCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAbsentAllCommand
     * and returns a MarkAbsentAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAbsentAllCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_TUTORIAL_INDEX)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_TUTORIAL_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAbsentAllCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_INDEX);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());
        } catch (ParseException e) {
            throw new ParseException(ClassDetails.getMessageInvalidTutorialIndex());
        }

        return new MarkAbsentAllCommand(index);
    }
}
