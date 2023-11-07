package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_CLASS_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_WILDCARD;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.EditCommand;
import seedu.classmanager.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.EditStudentDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, " " + VALID_STUDENT_NUMBER_BOB, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_STUDENT_NUMBER + STUDENT_NUMBER_DESC_AMY,
                MESSAGE_INVALID_FORMAT); // invalid 'old' student number

        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_STUDENT_NUMBER_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS); // invalid 'new' student number
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_CLASS_NUMBER_DESC,
                ClassDetails.MESSAGE_CONSTRAINTS); // invalid class number

        // invalid phone followed by valid email
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                VALID_STUDENT_NUMBER_BOB + INVALID_NAME_DESC
                        + INVALID_EMAIL_DESC + VALID_STUDENT_NUMBER_AMY
                        + VALID_CLASS_NUMBER_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;

        String userInput = targetStudentNumber + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                .withClassNumber(VALID_CLASS_NUMBER_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + PHONE_DESC_AMY + EMAIL_DESC_AMY;

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + NAME_DESC_AMY;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetStudentNumber + PHONE_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetStudentNumber + EMAIL_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student number
        userInput = targetStudentNumber + STUDENT_NUMBER_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withStudentNumber(VALID_STUDENT_NUMBER_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class number
        userInput = targetStudentNumber + CLASS_NUMBER_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withClassNumber(VALID_CLASS_NUMBER_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetStudentNumber + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetStudentNumber + PHONE_DESC_AMY + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY
                + EMAIL_DESC_AMY + PHONE_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_NUMBER));

        // multiple invalid values
        userInput = targetStudentNumber + INVALID_PHONE_DESC + INVALID_STUDENT_NUMBER_DESC
                + INVALID_EMAIL_DESC + INVALID_PHONE_DESC
                + INVALID_STUDENT_NUMBER_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_NUMBER));
    }

    @Test
    public void parse_emptyPreamble_failure() {
        assertParseFailure(parser, NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_additionalPrefix_failure() {
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + NAME_DESC_AMY
                        + " " + PREFIX_ASSIGNMENT_COUNT + "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + NAME_DESC_AMY
                + " " + PREFIX_GRADE + "100", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + NAME_DESC_AMY
                + " " + PREFIX_WILDCARD + "add", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB
                + " " + PREFIX_WILDCARD + "add" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }
}
