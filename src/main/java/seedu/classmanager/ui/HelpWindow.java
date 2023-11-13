package seedu.classmanager.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.classmanager.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USER_GUIDE_URL = "https://ay2324s1-cs2103t-t11-1.github.io/tp/UserGuide.html";
    public static final String COMMAND_TABLE = "Useful Commands:\n"
            + "Configure Class Manager - config #t/TUTORIAL_COUNT #a/ASSIGNMENT_COUNT\n"
            + "Open help window - help\n\n"
            + "Core Commands without Parameters:\n"
            + "Clear student list - clear\n"
            + "Exit Class Manager - exit\n"
            + "View command history - history\n"
            + "List all students - list\n"
            + "Undo previous command - undo\n"
            + "Redo previous command - redo\n"
            + "Toggle theme - theme\n\n"
            + "Core Commands with Parameters:\n"
            + "Add - add s/STUDENT_NUMBER n/NAME p/PHONE_NUMBER e/EMAIL c/CLASS_NUMBER [t/TAG]…\n"
            + "Comment - comment s/STUDENT_NUMBER cm/COMMENT\n"
            + "Delete - delete s/STUDENT_NUMBER\n"
            + "Edit - edit STUDENT_NUMBER [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [s/NEW_STUDENT_NUMBER] "
            + "[c/CLASS_NUMBER]\n"
            + "Lookup - lookup [c/CLASS_NUMBER] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [s/STUDENT_NUMBER] [t/TAG]\n"
            + "Load - load f/FILE_NAME\n"
            + "Present - present s/STUDENT_NUMBER tut/TUTORIAL_INDEX\n"
            + "Absent - absent s/STUDENT_NUMBER tut/TUTORIAL_INDEX\n"
            + "Present All - present-all tut/TUTORIAL_INDEX\n"
            + "Absent All - absent-all tut/TUTORIAL_INDEX\n"
            + "Random - random NUMBER_OF_STUDENTS\n"
            + "Record Class participation - class-part s/STUDENT_NUMBER tut/TUTORIAL_INDEX part/PARTICIPATION\n"
            + "Set Grade - grade s/STUDENT_NUMBER a/ASSIGNMENT_INDEX g/GRADE\n"
            + "Tag - tag s/STUDENT_NUMBER [/add] [/delete] t/[TAG]…\n"
            + "View - view s/STUDENT_NUMBER\n";

    public static final String HELP_MESSAGE = COMMAND_TABLE + "\nRefer to the user guide: " + USER_GUIDE_URL;
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USER_GUIDE_URL);
        clipboard.setContent(url);
    }
}
