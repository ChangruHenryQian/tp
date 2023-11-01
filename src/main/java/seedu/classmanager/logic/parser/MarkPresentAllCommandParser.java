package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/MarkPresentAllCommandParser.java
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
=======
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
>>>>>>> master:src/main/java/seedu/classmanager/logic/parser/MarkPresentAllCommandParser.java

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkPresentAllCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkPresentAllCommand object
 */
public class MarkPresentAllCommandParser implements Parser<MarkPresentAllCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkPresentAllCommand
     * and returns a MarkPresentAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkPresentAllCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_TUTORIAL_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkPresentAllCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_INDEX);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());

        return new MarkPresentAllCommand(index);
    }
}
