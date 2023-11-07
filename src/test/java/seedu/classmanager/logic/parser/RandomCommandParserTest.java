package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.RandomCommand.MESSAGE_INVALID_NUM_OF_STUDENTS;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.RandomCommand;

/**
 * Tests RandomCommandParser.
 */
public class RandomCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        RandomCommand.MESSAGE_USAGE);
    private RandomCommandParser parser = new RandomCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsRandomCommand() {
        assertParseSuccess(parser, "1", new RandomCommand(1));
    }

    @Test
    public void parse_invalidNumberOfStudent_throwsParseException() {
        // Not a number
        assertParseFailure(parser, " text", MESSAGE_INVALID_NUM_OF_STUDENTS);
        assertParseFailure(parser, " 1 n/ alice", MESSAGE_INVALID_NUM_OF_STUDENTS);

        // Not an integer
        assertParseFailure(parser, " 1.1", MESSAGE_INVALID_NUM_OF_STUDENTS);
    }
}
