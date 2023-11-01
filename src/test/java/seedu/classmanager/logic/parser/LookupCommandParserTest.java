package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.LookupCommand;
import seedu.classmanager.model.student.StudentContainsKeywordsPredicate;

public class LookupCommandParserTest {

    private LookupCommandParser parser = new LookupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLookupCommand() {
        LookupCommand expectedLookupCommand =
                new LookupCommand(new StudentContainsKeywordsPredicate(null,
                        null, "alice", "12345", null, null));

        // different ordering
        assertParseSuccess(parser, " n/alice p/12345", expectedLookupCommand);
        assertParseSuccess(parser, " p/12345 n/alice ", expectedLookupCommand);

        // prefix present but no keywords are ignored when other keywords present
        assertParseSuccess(parser, " c/ n/alice p/12345", expectedLookupCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/ \n alice \n p/\t \n12345  \t", expectedLookupCommand);

        expectedLookupCommand = new LookupCommand(new StudentContainsKeywordsPredicate(
                null, "email1 email2", "alice", "12345 54321",
                null, "tag1 tag2"));

        // multiple keywords for prefix
        assertParseSuccess(parser, " p/12345 54321 n/alice e/email1 email2 t/tag1 tag2", expectedLookupCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no keywords
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " /n Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));

        // no leading whitespaces
        assertParseFailure(parser, "n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));

        // no keyword for prefix
        assertParseFailure(parser, " p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " c/ n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LookupCommand.MESSAGE_USAGE));
    }

}
