package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL_DESC;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkAbsentAllCommand;
import seedu.classmanager.model.student.ClassDetails;

/**
 * Tests MarkAbsentAllCommandParser.
 */
public class MarkAbsentAllCommandParserTest {
    private MarkAbsentAllCommandParser parser = new MarkAbsentAllCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAbsentAllCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAbsentAllCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        // first tutorial
        assertParseSuccess(parser, TEST_FIRST_TUTORIAL_DESC,
                new MarkAbsentAllCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL)));

        // default tutorial
        assertParseSuccess(parser, TEST_DEFAULT_TUTORIAL_DESC,
                new MarkAbsentAllCommand(Index.fromOneBased(TEST_DEFAULT_TUTORIAL)));
    }

    @Test
    public void parse_invalidTutorialIndex_throwsParseException() {
        // index not a number
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_INDEX + " test",
                ClassDetails.getMessageInvalidTutorialIndex());

        // index negative
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_INDEX + " -1",
                ClassDetails.getMessageInvalidTutorialIndex());
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " test" + TEST_FIRST_TUTORIAL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentAllCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_additionalPrefix_throwsParseException() {
        assertParseFailure(parser, TEST_FIRST_TUTORIAL_DESC + STUDENT_NUMBER_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentAllCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_FIRST_TUTORIAL_DESC + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentAllCommand.MESSAGE_USAGE));
    }
}
