package seedu.classmanager.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.classmanager.testutil.StudentBuilder;

public class StudentContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        StudentContainsKeywordsPredicate firstPredicate = new StudentContainsKeywordsPredicate("T11",
                null, "first", null, null, null);
        StudentContainsKeywordsPredicate secondPredicate = new StudentContainsKeywordsPredicate(null,
                "second@example.com", "second", "12345678", "A099999X", "test");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentContainsKeywordsPredicate firstPredicateCopy = new StudentContainsKeywordsPredicate("T11",
                null, "first", null, null, null);
        StudentContainsKeywordsPredicate secondPredicateCopy = new StudentContainsKeywordsPredicate(null,
                "second@example.com", "second", "12345678", "A099999X", "test");
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertTrue(secondPredicate.equals(secondPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_studentContainsKeywords_returnsTrue() {
        // One keyword per prefix
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(null,
                null, "Alice", null, null, null);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").build()));
        predicate = new StudentContainsKeywordsPredicate("T100",
                null, null, null, null, null);
        assertTrue(predicate.test(new StudentBuilder().withClassDetails("T100").build()));
        predicate = new StudentContainsKeywordsPredicate("T100",
                null, "Alice", null, null, null);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").withClassDetails("T100").build()));

        // Multiple keywords per prefix
        predicate = new StudentContainsKeywordsPredicate(StudentBuilder.DEFAULT_CLASS_NUMBER,
                null, "Alice John Bob", StudentBuilder.DEFAULT_PHONE, null, "tag2 tag3");
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withTags("tag1", "tag2").build()));

        // Mixed-case keywords
        predicate = new StudentContainsKeywordsPredicate(null,
                null, "aLiCe", null, null, "TeSttAG");
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").withTags("testTag").build()));
    }

    @Test
    public void test_studentDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(null,
                null, "Carol", null, null, null);
        assertFalse(predicate.test(new StudentBuilder().withName("Alice Bob").build()));

        // Keywords match everything except for Name
        predicate = new StudentContainsKeywordsPredicate("T11 T12", "alice@email.com",
                "Carol", "12345", "A02481972A", "testTag");
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withStudentNumber("A02481972A")
                .withClassDetails("T11").withTags("testTag").build()));
    }

    @Test
    public void toStringMethod() {
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(
                null, "keyword1 keyword2", null,
                "keyword3 keyword4 keyword5", null, "keyword6");

        String expected = StudentContainsKeywordsPredicate.class.getCanonicalName()
                + "{classDetails=Optional.empty, emails=Optional[[keyword1, keyword2]], names=Optional.empty, "
                + "phones=Optional[[keyword3, keyword4, keyword5]], studentNumbers=Optional.empty, "
                + "tags=Optional[[keyword6]]}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_hashCode() {
        StudentContainsKeywordsPredicate predicate = new StudentContainsKeywordsPredicate(
                null, "keyword1 keyword2", null,
                "keyword3 keyword4 keyword5", null, "keyword6");
        StudentContainsKeywordsPredicate predicateCopy = new StudentContainsKeywordsPredicate(
                null, "keyword1 keyword2", null,
                "keyword3 keyword4 keyword5", null, "keyword6");

        // same values -> returns true
        assertTrue(predicate.hashCode() == predicateCopy.hashCode());

        // same object -> returns true
        assertTrue(predicate.hashCode() == predicate.hashCode());

        // null -> returns false
        assertFalse(predicate.hashCode() == 0);

        // different types -> returns false
        assertFalse(predicate.hashCode() == 1);

        // different values -> returns false
        assertFalse(predicate.hashCode() == new StudentContainsKeywordsPredicate(
                null, "keyword1 keyword2", null,
                "keyword3 keyword4 keyword5", null, "keyword7").hashCode());
    }
}
