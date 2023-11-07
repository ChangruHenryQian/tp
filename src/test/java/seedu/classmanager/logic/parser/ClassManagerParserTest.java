package seedu.classmanager.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_FIRST_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_COUNT;
import static seedu.classmanager.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.AddCommand;
import seedu.classmanager.logic.commands.ClearCommand;
import seedu.classmanager.logic.commands.CommentCommand;
import seedu.classmanager.logic.commands.ConfigCommand;
import seedu.classmanager.logic.commands.DeleteCommand;
import seedu.classmanager.logic.commands.EditCommand;
import seedu.classmanager.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.classmanager.logic.commands.ExitCommand;
import seedu.classmanager.logic.commands.HelpCommand;
import seedu.classmanager.logic.commands.HistoryCommand;
import seedu.classmanager.logic.commands.ListCommand;
import seedu.classmanager.logic.commands.LoadCommand;
import seedu.classmanager.logic.commands.LookupCommand;
import seedu.classmanager.logic.commands.MarkAbsentAllCommand;
import seedu.classmanager.logic.commands.MarkAbsentCommand;
import seedu.classmanager.logic.commands.MarkPresentAllCommand;
import seedu.classmanager.logic.commands.MarkPresentCommand;
import seedu.classmanager.logic.commands.RandomCommand;
import seedu.classmanager.logic.commands.RecordClassParticipationCommand;
import seedu.classmanager.logic.commands.RedoCommand;
import seedu.classmanager.logic.commands.SetGradeCommand;
import seedu.classmanager.logic.commands.TagCommand;
import seedu.classmanager.logic.commands.ThemeCommand;
import seedu.classmanager.logic.commands.UndoCommand;
import seedu.classmanager.logic.commands.ViewCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.ClassDetails;
import seedu.classmanager.model.student.Comment;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentContainsKeywordsPredicate;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.testutil.EditStudentDescriptorBuilder;
import seedu.classmanager.testutil.StudentBuilder;
import seedu.classmanager.testutil.StudentUtil;
import seedu.classmanager.testutil.TypicalStudents;

public class ClassManagerParserTest {

    private final ClassManagerParser parser = new ClassManagerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Student student = new StudentBuilder().build();
        DeleteCommand command = (DeleteCommand) parser.parseCommand(StudentUtil.getDeleteCommand(student));
        assertEquals(new DeleteCommand(student.getStudentNumber()), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalStudents.ALICE.getStudentNumber() + " "
                + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalStudents.ALICE.getStudentNumber(), descriptor), command);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD
            + " "
            + PREFIX_STUDENT_NUMBER + TypicalStudents.ALICE.getStudentNumber()
            + " "
            + StudentUtil.getTagDetails(TypicalStudents.ALICE));
        assertEquals(new TagCommand(TypicalStudents.ALICE.getStudentNumber(), TypicalStudents.ALICE.getTags()),
            command);
    }

    @Test
    public void parseCommand_markPresent() throws Exception {
        Student student = new StudentBuilder().build();
        MarkPresentCommand command = (MarkPresentCommand) parser.parseCommand(MarkPresentCommand.COMMAND_WORD
                        + " " + PREFIX_STUDENT_NUMBER + student.getStudentNumber() + TEST_FIRST_TUTORIAL_DESC);
        assertEquals(new MarkPresentCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL),
                student.getStudentNumber()), command);
    }

    @Test
    public void parseCommand_markPresentAll() throws Exception {
        MarkPresentAllCommand command = (MarkPresentAllCommand) parser
                .parseCommand(MarkPresentAllCommand.COMMAND_WORD + TEST_FIRST_TUTORIAL_DESC);
        assertEquals(new MarkPresentAllCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL)), command);
    }

    @Test
    public void parseCommand_markAbsent() throws Exception {
        Student student = new StudentBuilder().build();
        MarkAbsentCommand command = (MarkAbsentCommand) parser
                .parseCommand(MarkAbsentCommand.COMMAND_WORD + " "
                        + PREFIX_STUDENT_NUMBER + student.getStudentNumber() + TEST_FIRST_TUTORIAL_DESC);
        assertEquals(new MarkAbsentCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL),
                student.getStudentNumber()), command);
    }

    @Test
    public void parseCommand_markAbsentAll() throws Exception {
        MarkAbsentAllCommand command = (MarkAbsentAllCommand) parser
                .parseCommand(MarkAbsentAllCommand.COMMAND_WORD + TEST_FIRST_TUTORIAL_DESC);
        assertEquals(new MarkAbsentAllCommand(Index.fromOneBased(TEST_FIRST_TUTORIAL)), command);
    }

    @Test
    public void parseCommand_random() throws Exception {
        int num = 1;
        RandomCommand command = (RandomCommand) parser.parseCommand(RandomCommand.COMMAND_WORD + " "
                        + num);
        assertEquals(new RandomCommand(num), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_lookup() throws Exception {
        LookupCommand command = (LookupCommand) parser.parseCommand(
                LookupCommand.COMMAND_WORD + " c/t11 n/alice");
        assertEquals(new LookupCommand(new StudentContainsKeywordsPredicate("t11", null,
                "alice", null, null, null)), command);
    }

    @Test
    public void parseCommand_setGrade() throws Exception {
        SetGradeCommand command = (SetGradeCommand) parser.parseCommand(SetGradeCommand.COMMAND_WORD
                + STUDENT_NUMBER_DESC_AMY + SetGradeCommandParserTest.VALID_ASSIGNMENT_DESC
                + SetGradeCommandParserTest.VALID_GRADE_DESC);
        assertEquals(new SetGradeCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(SetGradeCommandParserTest.VALID_ASSIGNMENT)),
                Integer.parseInt(SetGradeCommandParserTest.VALID_GRADE)), command);
    }

    @Test
    public void parseCommand_recordClassParticipation() throws Exception {
        RecordClassParticipationCommand command = (RecordClassParticipationCommand) parser.parseCommand(
                RecordClassParticipationCommand.COMMAND_WORD
                + STUDENT_NUMBER_DESC_AMY + RecordClassParticipationCommandParserTest.VALID_TUT_DESC
                + RecordClassParticipationCommandParserTest.VALID_PARTICIPATION_DESC);
        assertEquals(new RecordClassParticipationCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Index.fromOneBased(Integer.parseInt(RecordClassParticipationCommandParserTest.VALID_TUT)),
                true), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_load() throws Exception {
        LoadCommand command = (LoadCommand) parser.parseCommand(
                LoadCommand.COMMAND_WORD + " " + PREFIX_FILE + "export-v1");
        Path path = Paths.get("data", "export-v1.json");
        assertEquals(new LoadCommand("export-v1", path), command);
    }

    @Test
    public void parseCommand_config() throws Exception {
        ConfigCommand command = (ConfigCommand) parser.parseCommand(
                ConfigCommand.COMMAND_WORD + " " + PREFIX_TUTORIAL_COUNT + "23 " + PREFIX_ASSIGNMENT_COUNT + "3");
        assertEquals(new ConfigCommand(23, 3), command);
        ClassDetails.setTutorialCount(13);
        ClassDetails.setAssignmentCount(6);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + STUDENT_NUMBER_DESC_AMY);
        assertEquals(new ViewCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY)), command);
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ()
                        -> parser.parseCommand(ViewCommand.COMMAND_WORD
                        + " " + INVALID_STUDENT_NUMBER));
    }

    @Test
    public void parseCommand_comment() throws Exception {
        Student student = new StudentBuilder().build();
        Comment comment = new Comment("Struggling with tutorials.");
        CommentCommand command = (CommentCommand) parser.parseCommand(CommentCommand.COMMAND_WORD
                + " " + PREFIX_STUDENT_NUMBER + student.getStudentNumber()
                + " " + PREFIX_COMMENT + comment);
        assertEquals(new CommentCommand(student.getStudentNumber(), comment), command);
    }

    @Test
    public void parseCommand_theme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD) instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " 3") instanceof ThemeCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }


    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                        -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
