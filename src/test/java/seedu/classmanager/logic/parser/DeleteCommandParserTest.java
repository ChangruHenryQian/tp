package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.DeleteCommand;
import seedu.classmanager.model.student.StudentNumber;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, STUDENT_NUMBER_DESC_BOB,
                new DeleteCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC, StudentNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " test" + STUDENT_NUMBER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleStudentNumber_throwsParseException() {
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB + STUDENT_NUMBER_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + STUDENT_NUMBER_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));
    }

    @Test
    public void parse_additionalPrefix_throwsParseException() {
        assertParseFailure(parser, CLASS_NUMBER_DESC_BOB + STUDENT_NUMBER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB + INVALID_EMAIL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
