package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.classmanager.logic.parser.LoadCommandParser.MESSAGE_INVALID_FILE_NAME;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.classmanager.logic.commands.LoadCommand;

public class LoadCommandParserTest {

    private final LoadCommandParser parser = new LoadCommandParser();

    @Test
    public void parse_validFileName_success() {
        String validFileName = "savefile";
        String fileNameWithPrefix = " " + PREFIX_FILE + validFileName;
        Path validFilePath = Paths.get("data", validFileName + ".json");
        LoadCommand expectedCommand = new LoadCommand(validFileName, validFilePath);
        assertParseSuccess(parser, fileNameWithPrefix, expectedCommand);
    }

    @Test
    public void parse_emptyFileName_failure() {
        String emptyFileName = "";
        String fileNameWithPrefix = " " + PREFIX_FILE + emptyFileName;
        assertParseFailure(parser, fileNameWithPrefix, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_spaceAsFileName_failure() {
        String spaceFileName = " ";
        String fileNameWithPrefix = " " + PREFIX_FILE + spaceFileName;
        assertParseFailure(parser, fileNameWithPrefix, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_slashInFileName_failure() {
        String slashInFileName = "a/";
        String fileNameWithPrefix = " " + PREFIX_FILE + slashInFileName;
        assertParseFailure(parser, fileNameWithPrefix, MESSAGE_INVALID_FILE_NAME);
    }

    @Test
    public void parse_nullArguments_failure() {
        assertParseFailure(parser, null,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, " test" + " " + PREFIX_FILE + "savefile",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_additionalPrefixPreamble_failure() {
        assertParseFailure(parser, NAME_DESC_AMY + " " + PREFIX_FILE + "savefile",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_FILE + "savefile" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_FILE + "savefile" + STUDENT_NUMBER_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }
}
