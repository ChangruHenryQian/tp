package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.CommentCommand;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.StudentNumber;

/**
 * To test CommentCommandParser
 */
public class CommentCommandParserTest {

    private CommentCommandParser parser = new CommentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CommentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCommentCommand() {
        assertParseSuccess(parser, "comment s/ " + VALID_STUDENT_NUMBER_BOB + " cm/ Good student",
                new CommentCommand(new StudentNumber(VALID_STUDENT_NUMBER_BOB), new Comment("Good student")));
    }

    @Test
    public void parse_invalidStudentNumber_throwsParseException() {
        assertParseFailure(parser, "comment s/ " + "T1234567M" + " cm/ Good student",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
    }
}
