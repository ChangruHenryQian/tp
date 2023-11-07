package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_CLASS_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.classmanager.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_CLASS_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_WILDCARD;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.classmanager.testutil.TypicalStudents.AMY;
import static seedu.classmanager.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.AddCommand;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Email;
import seedu.classmanager.model.student.Name;
import seedu.classmanager.model.student.Phone;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;
import seedu.classmanager.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));


        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                        + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStudentString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple student number
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // multiple class number
        assertParseFailure(parser, CLASS_NUMBER_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS_NUMBER));


        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStudentString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + STUDENT_NUMBER_DESC_AMY
                        + CLASS_NUMBER_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENT_NUMBER, PREFIX_CLASS_NUMBER,
                        PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid student number
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // invalid class number
        assertParseFailure(parser, INVALID_CLASS_NUMBER_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS_NUMBER));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedStudentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid student number
        assertParseFailure(parser, validExpectedStudentString + INVALID_STUDENT_NUMBER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // invalid class number
        assertParseFailure(parser, validExpectedStudentString + INVALID_CLASS_NUMBER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS_NUMBER));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + STUDENT_NUMBER_DESC_AMY
                + CLASS_NUMBER_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                        + CLASS_NUMBER_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                        + CLASS_NUMBER_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + STUDENT_NUMBER_DESC_BOB
                        + CLASS_NUMBER_DESC_BOB,
                expectedMessage);

        // missing student number prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_STUDENT_NUMBER_BOB
                    + CLASS_NUMBER_DESC_BOB,
                expectedMessage);

        // missing class number prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                    + VALID_CLASS_NUMBER_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_STUDENT_NUMBER_BOB
                    + VALID_CLASS_NUMBER_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + STUDENT_NUMBER_DESC_BOB
                + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid student number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_STUDENT_NUMBER_DESC
                + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, StudentNumber.MESSAGE_CONSTRAINTS);

        // invalid class number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + INVALID_CLASS_NUMBER_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, ClassDetails.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + CLASS_NUMBER_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_STUDENT_NUMBER_DESC
                        + CLASS_NUMBER_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // additional unexpected prefixes - tut/
        assertParseFailure(parser, NAME_DESC_BOB + " " + PREFIX_TUTORIAL_INDEX + "1" + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // additional unexpected prefixes - /
        assertParseFailure(parser, NAME_DESC_BOB + " " + PREFIX_WILDCARD + "add" + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // additional unexpected prefixes - #a/
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + STUDENT_NUMBER_DESC_BOB + CLASS_NUMBER_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + " " + PREFIX_ASSIGNMENT_COUNT + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
