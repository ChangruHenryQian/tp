package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.classmanager.logic.commands.LookupCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.StudentContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new LookupCommand object
 */
public class LookupCommandParser implements Parser<LookupCommand> {

    private static final Prefix[] prefixList = new Prefix[] {PREFIX_CLASS_NUMBER,
        PREFIX_EMAIL, PREFIX_NAME, PREFIX_PHONE, PREFIX_STUDENT_NUMBER, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the LookupCommand
     * and returns a LookupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LookupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixList);
        argMultimap.verifyNoDuplicatePrefixesFor(prefixList);
        HashMap<Prefix, String> prefixMap = new HashMap<>();
        for (Prefix prefix : prefixList) {
            String keywords = argMultimap.getValue(prefix).orElse("");
            if (keywords.isEmpty()) {
                continue;
            }
            prefixMap.put(prefix, keywords);
        }
        if (!isPrefixPresent(prefixMap, prefixList)
                || areAdditionalPrefixesPresent(args, prefixList)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
        }
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(
                prefixMap.get(PREFIX_CLASS_NUMBER),
                prefixMap.get(PREFIX_EMAIL),
                prefixMap.get(PREFIX_NAME),
                prefixMap.get(PREFIX_PHONE),
                prefixMap.get(PREFIX_STUDENT_NUMBER),
                prefixMap.get(PREFIX_TAG));
        return new LookupCommand(predicate);
    }

    /**
     * Returns true if at least one prefix contains non-empty values in the given
     * {@code prefixMap}.
     */
    private static boolean isPrefixPresent(HashMap<Prefix, String> prefixMap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefixMap::containsKey);
    }

}
