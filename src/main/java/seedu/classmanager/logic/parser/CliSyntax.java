package seedu.classmanager.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_WILDCARD = new Prefix("/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("s/");
    public static final Prefix PREFIX_CLASS_NUMBER = new Prefix("c/");
    public static final Prefix PREFIX_FILE = new Prefix("f/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_ASSIGNMENT = new Prefix("a/");
    public static final Prefix PREFIX_TUTORIAL_INDEX = new Prefix("tut/");
    public static final Prefix PREFIX_PARTICIPATION = new Prefix("part/");
    public static final Prefix PREFIX_TUTORIAL_COUNT = new Prefix("#t/");
    public static final Prefix PREFIX_ASSIGNMENT_COUNT = new Prefix("#a/");
    public static final Prefix PREFIX_COMMENT = new Prefix("cm/");
    public static final Prefix[] PREFIX_LIST = new Prefix[] { PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_TAG,
        PREFIX_WILDCARD,
        PREFIX_STUDENT_NUMBER,
        PREFIX_CLASS_NUMBER,
        PREFIX_FILE,
        PREFIX_GRADE,
        PREFIX_ASSIGNMENT,
        PREFIX_TUTORIAL_INDEX,
        PREFIX_PARTICIPATION,
        PREFIX_TUTORIAL_COUNT,
        PREFIX_ASSIGNMENT_COUNT,
        PREFIX_COMMENT };
}
