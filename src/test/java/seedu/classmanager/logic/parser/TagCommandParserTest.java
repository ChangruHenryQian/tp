package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_WILDCARD;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.AddTagCommand;
import seedu.classmanager.logic.commands.DeleteTagCommand;
import seedu.classmanager.logic.commands.TagCommand;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;
import seedu.classmanager.testutil.StudentBuilder;

public class TagCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_TAG_FAILED + TagCommand.MESSAGE_USAGE);
    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no Student Number specified
        assertParseFailure(parser, TAG_EMPTY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY,
            MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidArgs_failure() {
        // student number not starting with "A"
        assertParseFailure(parser, "s/B2103818N" + TAG_EMPTY, MESSAGE_INVALID_FORMAT);

        // invalid arguments
        assertParseFailure(parser, "A2103818N some random tag", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed
        assertParseFailure(parser, "s/A2103818N /t label", MESSAGE_INVALID_FORMAT);

        // invalid action identifier
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + " /asd " + TAG_EMPTY,
            TagCommand.MESSAGE_INVALID_ACTION_IDENTIFIER);

        // duplicate prefix
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + " /add /delete " + TAG_EMPTY,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WILDCARD));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY
            + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAGS} alone will reset the tags of the {@code Student} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY
                + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY
                + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND;

        Student student = new StudentBuilder().withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        TagCommand expectedCommand = new TagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), student.getTags());

        assertParseSuccess(parser, userInput, expectedCommand);

        String addUserInput = STUDENT_NUMBER_DESC_AMY + " " + PREFIX_WILDCARD
            + TagCommand.ADD_TAGS + " " + TAG_DESC_FRIEND;
        Student studentToAddTag = new StudentBuilder().withTags(VALID_TAG_FRIEND).build();
        TagCommand expectedAddTagCommand = new AddTagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), studentToAddTag.getTags());

        assertParseSuccess(parser, addUserInput, expectedAddTagCommand);

        String deleteUserInput = STUDENT_NUMBER_DESC_AMY + " " + PREFIX_WILDCARD
            + TagCommand.DELETE_TAGS + " " + TAG_DESC_FRIEND;
        Student studentToDeleteTag = new StudentBuilder().withTags(VALID_TAG_FRIEND).build();
        TagCommand expectedDeleteTagCommand = new DeleteTagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), studentToDeleteTag.getTags());

        assertParseSuccess(parser, deleteUserInput, expectedDeleteTagCommand);

    }

    @Test
    public void parse_resetTags_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + TAG_EMPTY;

        TagCommand expectedCommand = new TagCommand(
            new StudentNumber(VALID_STUDENT_NUMBER_AMY), new HashSet<>());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, " test" + STUDENT_NUMBER_DESC_AMY + TAG_DESC_FRIEND,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_additionalPrefix_failure() {
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + TAG_DESC_FRIEND
                + " " + PREFIX_ASSIGNMENT_COUNT + "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + " " + PREFIX_ASSIGNMENT_COUNT + "1"
                + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + PREFIX_ASSIGNMENT_COUNT + "1" + STUDENT_NUMBER_DESC_AMY
                + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + " " + PREFIX_COMMENT + "test"
                + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + " " + PREFIX_CLASS_NUMBER + "t11"
                + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);
    }
}
