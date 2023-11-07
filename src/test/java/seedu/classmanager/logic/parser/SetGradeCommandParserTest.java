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
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.SetGradeCommand;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.StudentNumber;

public class SetGradeCommandParserTest {
    public static final String INVALID_ASSIGNMENT_DESC = " " + PREFIX_ASSIGNMENT + "assign1";
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "grade=1";
    public static final String VALID_ASSIGNMENT = "1";
    public static final String VALID_GRADE = "1";
    public static final String VALID_ASSIGNMENT_DESC = " " + PREFIX_ASSIGNMENT + VALID_ASSIGNMENT;
    public static final String VALID_GRADE_DESC = " " + PREFIX_GRADE + VALID_GRADE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGradeCommand.MESSAGE_USAGE);

    private final SetGradeCommandParser parser = new SetGradeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // non-empty but nothing specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // empty user input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "       ", MESSAGE_INVALID_FORMAT);

        // missing field
        assertParseFailure(parser, VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC,
                MESSAGE_INVALID_FORMAT);

        // missing prefix
        assertParseFailure(parser, String.format("%s %s %s", VALID_STUDENT_NUMBER_AMY,
                VALID_ASSIGNMENT, VALID_GRADE), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        ClassDetails.setAssignmentCount(6);
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS); // invalid student number

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + INVALID_ASSIGNMENT_DESC + VALID_GRADE_DESC,
                ClassDetails.getMessageInvalidAssignmentIndex()); // invalid assignment

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + INVALID_GRADE_DESC,
                ClassDetails.MESSAGE_INVALID_GRADE); // invalid grade
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC;

        SetGradeCommand expectedCommand = new SetGradeCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(VALID_ASSIGNMENT)), Integer.parseInt(VALID_GRADE));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_differentOrdering_success() {
        String userInput = VALID_GRADE_DESC + VALID_ASSIGNMENT_DESC + STUDENT_NUMBER_DESC_AMY;

        SetGradeCommand expectedCommand = new SetGradeCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(VALID_ASSIGNMENT)), Integer.parseInt(VALID_GRADE));
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC + STUDENT_NUMBER_DESC_AMY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC;

        assertParseFailure(parser, userInput + STUDENT_NUMBER_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        assertParseFailure(parser, userInput + VALID_ASSIGNMENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT));

        // multiple valid fields repeated
        userInput += VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT, PREFIX_GRADE));

        // multiple invalid fields followed by multiple valid repeated fields
        userInput = INVALID_STUDENT_NUMBER_DESC + INVALID_ASSIGNMENT_DESC + INVALID_GRADE_DESC
                + STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + VALID_GRADE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT, PREFIX_GRADE, PREFIX_STUDENT_NUMBER));
    }

    @Test
    public void parse_additionalPrefix_failure() {
        // additional prefix at different location
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC
                + NAME_DESC_AMY + VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC
                + VALID_GRADE_DESC + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + NAME_DESC_AMY + VALID_ASSIGNMENT_DESC
                + VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, NAME_DESC_AMY + STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC
                + VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);

        // different additional prefix
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + TAG_DESC_FRIEND
                + VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + VALID_ASSIGNMENT_DESC + CLASS_NUMBER_DESC_AMY
                + VALID_GRADE_DESC, MESSAGE_INVALID_FORMAT);
    }
}
