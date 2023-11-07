package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.RecordClassParticipationCommand;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.StudentNumber;

public class RecordClassParticipationCommandParserTest {
    public static final String INVALID_TUT_DESC = " " + PREFIX_TUTORIAL_INDEX + "first";
    public static final String INVALID_PARTICIPATION_DESC = " " + PREFIX_PARTICIPATION + "execellent";
    public static final String VALID_TUT = "1";
    public static final String VALID_PARTICIPATION = "true";
    public static final String VALID_TUT_DESC = " " + PREFIX_TUTORIAL_INDEX + VALID_TUT;
    public static final String VALID_PARTICIPATION_DESC = " " + PREFIX_PARTICIPATION + VALID_PARTICIPATION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordClassParticipationCommand.MESSAGE_USAGE);

    private final RecordClassParticipationCommandParser parser = new RecordClassParticipationCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // non-empty but nothing specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // empty user input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "       ", MESSAGE_INVALID_FORMAT);

        // missing field
        assertParseFailure(parser, VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_TUT_DESC + VALID_PARTICIPATION_DESC,
                MESSAGE_INVALID_FORMAT);

        // missing prefix
        assertParseFailure(parser, String.format("%s %s %s", VALID_STUDENT_NUMBER_AMY,
                        VALID_TUT, VALID_PARTICIPATION), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        ClassDetails.setTutorialCount(13);
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + VALID_TUT_DESC + VALID_PARTICIPATION_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS); // invalid student number

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + INVALID_TUT_DESC + VALID_PARTICIPATION_DESC,
                ClassDetails.getMessageInvalidTutorialIndex()); // invalid tut

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + INVALID_PARTICIPATION_DESC,
                ClassDetails.MESSAGE_INVALID_PARTICIPATION); // invalid participation
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + VALID_PARTICIPATION_DESC;

        RecordClassParticipationCommand expectedCommand = new RecordClassParticipationCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(VALID_TUT)),
                Boolean.parseBoolean(VALID_PARTICIPATION));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_differentOrdering_success() {
        String userInput = VALID_PARTICIPATION_DESC + VALID_TUT_DESC + STUDENT_NUMBER_DESC_AMY;

        RecordClassParticipationCommand expectedCommand = new RecordClassParticipationCommand(
                new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(VALID_TUT)),
                Boolean.parseBoolean(VALID_PARTICIPATION));
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = VALID_TUT_DESC + VALID_PARTICIPATION_DESC + STUDENT_NUMBER_DESC_AMY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + VALID_PARTICIPATION_DESC;

        assertParseFailure(parser, userInput + STUDENT_NUMBER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        assertParseFailure(parser, userInput + VALID_TUT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_INDEX));

        // multiple valid fields repeated
        userInput += VALID_TUT_DESC + VALID_PARTICIPATION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_INDEX, PREFIX_PARTICIPATION));

        // multiple invalid fields followed by multiple valid repeated fields
        userInput = INVALID_STUDENT_NUMBER_DESC + INVALID_TUT_DESC + INVALID_PARTICIPATION_DESC
                + STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + VALID_PARTICIPATION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_INDEX,
                        PREFIX_PARTICIPATION,
                        PREFIX_STUDENT_NUMBER));
    }

    @Test
    public void parse_additionalPrefix_failure() {
        // additional prefix at different location
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC
                + NAME_DESC_AMY + VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC
                + VALID_PARTICIPATION_DESC + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + NAME_DESC_AMY + VALID_TUT_DESC
                + VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, NAME_DESC_AMY + STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC
                + VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);

        // different additional prefix
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + TAG_DESC_FRIEND
                + VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_TUT_DESC + CLASS_NUMBER_DESC_AMY
                + VALID_PARTICIPATION_DESC, MESSAGE_INVALID_FORMAT);
    }
}
