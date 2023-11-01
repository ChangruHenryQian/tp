package seedu.classmanager.model.student.information;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.classmanager.model.student.Comment;

public class CommentTest {

    @Test
    public void isValidComment_validComments_returnTrue() {
        assertTrue(Comment.isValidComment("This is a valid comment."));
        assertTrue(Comment.isValidComment("12345"));
        assertTrue(Comment.isValidComment("!@#$%"));
    }

    @Test
    public void isValidComment_invalidComments_returnFalse() {
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));
    }

    @Test
    public void constructor_validComment_constructsComment() {
        Comment comment = new Comment("This is a valid comment.");
        assertNotNull(comment);
        assertEquals("This is a valid comment.", comment.toString());
    }

    @Test
    public void constructor_invalidComment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void equalsAndHashCode_commentsWithSameText_areEqual() {
        Comment comment1 = new Comment("Hello");
        Comment comment2 = new Comment("Hello");
        assertEquals(comment1, comment2);
        assertEquals(comment1.hashCode(), comment2.hashCode());
    }

    @Test
    public void equals_commentsWithDifferentText_areNotEqual() {
        Comment comment1 = new Comment("Hello");
        Comment comment2 = new Comment("World");
        assertNotEquals(comment1, comment2);
    }
}
