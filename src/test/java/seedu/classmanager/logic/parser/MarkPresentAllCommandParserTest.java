package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL_DESC;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkPresentAllCommand;
import seedu.classmanager.model.student.ClassDetails;

/**
 * Tests MarkPresentAllCommandParser.
 */
public class MarkPresentAllCommandParserTest {
    private MarkPresentAllCommandParser parser = new MarkPresentAllCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkPresentAllCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkPresentAllCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        // first tutorial
        assertParseSuccess(parser, TEST_FIRST_TUTORIAL_DESC,
                new MarkPresentAllCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL)));

        // default tutorial
        assertParseSuccess(parser, TEST_DEFAULT_TUTORIAL_DESC,
                new MarkPresentAllCommand(Index.fromOneBased(TEST_DEFAULT_TUTORIAL)));
    }

    @Test
    public void parse_invalidTutorialIndex_throwsParseException() {
        ClassDetails.setTutorialCount(ClassDetails.DEFAULT_COUNT);

        // index not a number
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_INDEX + " test",
                ClassDetails.getMessageInvalidTutorialIndex());

        // index negative
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_INDEX + " -1",
                ClassDetails.getMessageInvalidTutorialIndex());
    }
}
