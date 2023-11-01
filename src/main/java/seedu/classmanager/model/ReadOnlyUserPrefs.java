package seedu.classmanager.model;

import java.nio.file.Path;

import seedu.classmanager.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getClassManagerFilePath();

    boolean getConfigured();

    int getAssignmentCount();

    int getTutorialCount();

    String getTheme();
}
