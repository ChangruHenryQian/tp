package seedu.classmanager.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {
    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }

    @Test
    public void equals() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);

        // same object -> returns true
        assertEquals(guiSettings, guiSettings);

        // same values -> returns true
        GuiSettings differentGuiSettings = new GuiSettings(1, 2, 3, 4);
        assertEquals(guiSettings, differentGuiSettings);

        // null -> returns false
        assertFalse(guiSettings.equals(null));

        // different gui setting -> returns false
        differentGuiSettings = new GuiSettings(2, 3, 4, 5);
        assertNotEquals(guiSettings, differentGuiSettings);
    }
}
