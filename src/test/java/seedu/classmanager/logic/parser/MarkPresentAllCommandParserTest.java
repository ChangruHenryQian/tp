package seedu.classmanager.logic.parser;

<<<<<<< HEAD:src/test/java/seedu/address/logic/parser/MarkPresentAllCommandParserTest.java
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
=======
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;
>>>>>>> master:src/test/java/seedu/classmanager/logic/parser/MarkPresentAllCommandParserTest.java

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkPresentAllCommand;

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
        int tut = 1;
        assertParseSuccess(parser, " " + PREFIX_TUTORIAL_INDEX + tut,
                new MarkPresentAllCommand(Index.fromOneBased(tut)));
    }
}
