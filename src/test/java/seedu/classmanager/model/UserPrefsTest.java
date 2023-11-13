package seedu.classmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classmanager.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setClassManagerFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setClassManagerFilePath(null));
    }

    @Test
    public void toggleTheme() {
        UserPrefs userPrefs = new UserPrefs();
        String theme = userPrefs.getTheme();
        String expectedTheme = "light";
        if (theme.equalsIgnoreCase("light")) {
            expectedTheme = "dark";
        }
        userPrefs.toggleColourTheme();
        assertTrue(userPrefs.getTheme().equals(expectedTheme));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same object -> returns true
        assertEquals(userPrefs, userPrefs);

        // same values -> returns true
        UserPrefs userPrefsCopy = new UserPrefs();
        assertEquals(userPrefs, userPrefsCopy);

        // null -> returns false
        assertFalse(userPrefs.equals(null));

        // different classManagerFilePath -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClassManagerFilePath(Path.of("differentFilePath"));
        assertNotEquals(userPrefs, differentUserPrefs);

        // different guiSettings -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(1, 1, 1, 1));
        assertNotEquals(userPrefs, differentUserPrefs);
    }

}
