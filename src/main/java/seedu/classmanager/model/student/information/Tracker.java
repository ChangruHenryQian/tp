package seedu.classmanager.model.student.information;

import java.util.List;

/**
 * Represents a tracker for a particular class grade.
 */
public interface Tracker {

    /**
     * Returns the average result of objects in the tracker.
     *
     * @return Average result of objects in the tracker.
     */
    double getPercentage();

    /**
     * Returns a Json Friendly representation of the tracker.
     *
     * @return Json Friendly representation of the tracker.
     */
    List getJson();
}
