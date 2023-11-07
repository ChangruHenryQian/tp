package seedu.classmanager.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.parser.ArgumentMultimap.areAdditionalPrefixesPresent;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_WILDCARD;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.classmanager.logic.commands.AddTagCommand;
import seedu.classmanager.logic.commands.DeleteTagCommand;
import seedu.classmanager.logic.commands.TagCommand;
import seedu.classmanager.logic.parser.exceptions.ParseException;
import seedu.classmanager.model.student.StudentNumber;
import seedu.classmanager.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    private Set<Tag> tags;

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG, PREFIX_WILDCARD, PREFIX_STUDENT_NUMBER);

        String number = argMultimap.getValue(PREFIX_STUDENT_NUMBER).orElse("");
        if (!StudentNumber.isValidStudentNumber(number)
                || !argMultimap.getPreamble().isEmpty()
                || areAdditionalPrefixesPresent(args, PREFIX_TAG, PREFIX_WILDCARD, PREFIX_STUDENT_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_TAG_FAILED + TagCommand.MESSAGE_USAGE));
        }

        StudentNumber studentNumber = new StudentNumber(number);

        parseTags(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(this::setTags);

        if (this.tags == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagCommand.MESSAGE_TAG_FAILED + TagCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WILDCARD);
        String action = argMultimap.getValue(PREFIX_WILDCARD).orElse("");

        switch (action) {
        case TagCommand.ADD_TAGS:
            return new AddTagCommand(studentNumber, this.tags);
        case TagCommand.DELETE_TAGS:
            return new DeleteTagCommand(studentNumber, this.tags);
        case TagCommand.DEFAULT:
            return new TagCommand(studentNumber, this.tags);
        default:
            throw new ParseException(TagCommand.MESSAGE_INVALID_ACTION_IDENTIFIER);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTags(Collection<String> tags) throws ParseException {
        assert tags != null;
        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }
}
