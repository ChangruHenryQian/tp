package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_DEFAULT_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkAbsentCommand;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Tests MarkAbsentCommandParser.
 */
public class MarkAbsentCommandParserTest {
    private MarkAbsentCommandParser parser = new MarkAbsentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        // first tutorial
        assertParseSuccess(parser, STUDENT_NUMBER_DESC_BOB + TEST_FIRST_TUTORIAL_DESC,
                new MarkAbsentCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL),
                        new StudentNumber(VALID_STUDENT_NUMBER_BOB)));

        // default tutorial
        assertParseSuccess(parser, STUDENT_NUMBER_DESC_BOB + TEST_DEFAULT_TUTORIAL_DESC,
                new MarkAbsentCommand(Index.fromOneBased(TEST_DEFAULT_TUTORIAL),
                        new StudentNumber(VALID_STUDENT_NUMBER_BOB)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_FIRST_TUTORIAL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentNumber_throwsParseException() {
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + TEST_FIRST_TUTORIAL_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTutorialIndex_throwsParseException() {
        // index not a number
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB + " " + TEST_FIRST_TUTORIAL_DESC + " test",
                ClassDetails.getMessageInvalidTutorialIndex());

        // index negative
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB + " " + PREFIX_TUTORIAL_INDEX + " -1",
                ClassDetails.getMessageInvalidTutorialIndex());
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " test" + TEST_FIRST_TUTORIAL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_additionalPrefix_throwsParseException() {
        assertParseFailure(parser, TEST_FIRST_TUTORIAL_DESC + STUDENT_NUMBER_DESC_AMY + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_FIRST_TUTORIAL_DESC + NAME_DESC_AMY + STUDENT_NUMBER_DESC_AMY ,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAbsentCommand.MESSAGE_USAGE));
    }
}
