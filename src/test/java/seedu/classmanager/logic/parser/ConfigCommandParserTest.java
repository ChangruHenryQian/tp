package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_WILDCARD;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.classmanager.logic.parser.ConfigCommandParser.MESSAGE_INVALID_CONFIG_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ConfigCommandParser.MESSAGE_INVALID_COUNT_VALUE_TOO_LARGE;
import static seedu.classmanager.logic.parser.ConfigCommandParser.MESSAGE_INVALID_COUNT_VALUE_TOO_SMALL;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.ConfigCommand;

public class ConfigCommandParserTest {

    private final ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_validArg_success() {
        int tutorialCount = 1;
        int assignmentCount = 2;
        ConfigCommand expectedCommand = new ConfigCommand(tutorialCount, assignmentCount);
        String argument = " " + PREFIX_TUTORIAL_COUNT + tutorialCount + " "
                + PREFIX_ASSIGNMENT_COUNT + assignmentCount;
        assertParseSuccess(parser, argument, expectedCommand);
    }

    @Test
    public void parse_validArg2_success() {
        int tutorialCount = 40;
        int assignmentCount = 39;
        ConfigCommand expectedCommand = new ConfigCommand(tutorialCount, assignmentCount);
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + assignmentCount + " "
                + PREFIX_TUTORIAL_COUNT + tutorialCount;
        assertParseSuccess(parser, argument, expectedCommand);
    }

    @Test
    public void parse_nonIntArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "1 "
                + PREFIX_TUTORIAL_COUNT + "t";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_CONFIG_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntArg2_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "1.4 "
                + PREFIX_TUTORIAL_COUNT + "1";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_CONFIG_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_intOverflowArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "42984732998379823983289733332332 "
                + PREFIX_TUTORIAL_COUNT + "2";
        assertParseFailure(parser, argument,
                String.format(MESSAGE_INVALID_CONFIG_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooSmallIntArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "0 "
                + PREFIX_TUTORIAL_COUNT + "1";
        assertParseFailure(parser, argument, String.format(MESSAGE_INVALID_COUNT_VALUE_TOO_SMALL, "assignments"));
    }

    @Test
    public void parse_tooSmallIntArg2_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "1 "
                + PREFIX_TUTORIAL_COUNT + "-4";
        assertParseFailure(parser, argument, String.format(MESSAGE_INVALID_COUNT_VALUE_TOO_SMALL, "tutorials"));
    }

    @Test
    public void parse_tooLargeIntArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "41 "
                + PREFIX_TUTORIAL_COUNT + "1";
        assertParseFailure(parser, argument, String.format(MESSAGE_INVALID_COUNT_VALUE_TOO_LARGE, "assignments"));
    }

    @Test
    public void parse_tooLargeIntArg2_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "1 "
                + PREFIX_TUTORIAL_COUNT + "41";
        assertParseFailure(parser, argument, String.format(MESSAGE_INVALID_COUNT_VALUE_TOO_LARGE, "tutorials"));
    }

    @Test
    public void parse_emptyArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + " "
                + PREFIX_TUTORIAL_COUNT + "2";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_CONFIG_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_additionalArg_failure() {
        String argument = " " + PREFIX_ASSIGNMENT_COUNT + "1 " + PREFIX_FILE + "sample "
                + PREFIX_TUTORIAL_COUNT + "2";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));

        argument = " " + PREFIX_WILDCARD + "add " + PREFIX_ASSIGNMENT_COUNT + "1 "
                + PREFIX_TUTORIAL_COUNT + "2";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));

        argument = " " + PREFIX_ASSIGNMENT_COUNT + "1 " + PREFIX_TUTORIAL_COUNT + "2 "
                + PREFIX_NAME + "alice";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String argument = " test " + PREFIX_ASSIGNMENT_COUNT + "1 "
                + PREFIX_TUTORIAL_COUNT + "2";
        assertParseFailure(parser, argument, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }
}
