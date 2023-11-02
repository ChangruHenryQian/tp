package seedu.classmanager.model.student.information;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import seedu.classmanager.commons.core.index.Index;

/**
 * Represents a Student's class participation grades in the class manager.
 * Guarantees: is valid as declared in {@link #isValidClassParticipation(int)}
 */
public class ClassParticipationTracker implements Tracker {

    public static final String MESSAGE_CONSTRAINTS = "Class participation needs to be a positive integer";

    private ClassParticipation[] classParticipationList;

    /**
     * Constructs an {@code ClassParticipationTracker}.
     *
     * @param numOfTut A valid integer for the number of tutorials in a class.
     *
     */
    public ClassParticipationTracker(int numOfTut) {
        checkArgument(isValidClassParticipation(numOfTut), MESSAGE_CONSTRAINTS);
        classParticipationList = new ClassParticipation[numOfTut];
        IntStream.range(0, numOfTut).forEach(i -> classParticipationList[i] = new ClassParticipation());
    }

    /**
     * Constructs an {@code ClassParticipationTracker}. With a given class participation tracker list.
     *
     * @param classParticipationTracker A list of booleans representing the class participation.
     */
    public ClassParticipationTracker(List<Boolean> classParticipationTracker) {
        requireNonNull(classParticipationTracker);
        classParticipationList = new ClassParticipation[classParticipationTracker.size()];
        IntStream.range(0, classParticipationTracker.size())
                .forEach(i -> classParticipationList[i] = new ClassParticipation(classParticipationTracker.get(i)));
    }

    /**
     * Constructs an {@code ClassParticipationTracker} with a given class participation list.
     * Used for duplication.
     * @param classParticipationList A list of booleans stored in {@code ClassParticipation}.
     */
    public ClassParticipationTracker(ClassParticipation[] classParticipationList) {
        requireNonNull(classParticipationList);
        this.classParticipationList = classParticipationList;
    }

    /**
     * Returns a deep copy of the class participation tracker.
     * @return A deep copy of {@code ClassParticipationTracker}.
     */
    public ClassParticipationTracker copy() {
        ClassParticipation[] newClassParticipationList = new ClassParticipation[this.classParticipationList.length];
        for (int i = 0; i < this.classParticipationList.length; i++) {
            newClassParticipationList[i] = new ClassParticipation();
            if (this.classParticipationList[i].getHasParticipated()) {
                newClassParticipationList[i].mark();
            }
        }
        return new ClassParticipationTracker(newClassParticipationList);
    }


    /**
     * Returns true if a given int is a valid number of tutorials.
     */
    public static boolean isValidClassParticipation(int numOfTut) {
        return numOfTut >= 0;
    }

    /**
     * Marks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     */
    public void markParticipated(Index tutNum) {
        classParticipationList[tutNum.getZeroBased()].mark();
    }

    /**
     * Unmarks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     */
    public void markDidNotParticipate(Index tutNum) {
        classParticipationList[tutNum.getZeroBased()].unmark();
    }

    /**
     * Marks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     * @param participated Whether the student participated.
     */
    public void markParticipation(Index tutNum, boolean participated) {
        if (participated) {
            markParticipated(tutNum);
        } else {
            markDidNotParticipate(tutNum);
        }
    }

    /**
     * Returns a Json friendly version of the classParticipationTracker.
     */
    public List<Boolean> getJson() {
        List<Boolean> classParticipationTracker = new ArrayList<>();
        for (ClassParticipation classParticipation : classParticipationList) {
            classParticipationTracker.add(classParticipation.getHasParticipated());
        }
        return classParticipationTracker;
    }

    /**
     * Returns the percentage of class participation.
     *
     * @return Percentage of class participation.
     */
    public double getPercentage() {
        // Case when there are no tutorials
        if (classParticipationList.length == 0) {
            return 100;
        }
        int score = 0;
        int totalScore = 0;
        for (int i = 0; i < classParticipationList.length; i++) {
            if (classParticipationList[i] != null) {
                totalScore += 1;
                if (classParticipationList[i].getHasParticipated()) {
                    score += 1;
                }
            }
        }
        return (double) score / totalScore * 100;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Class participation:\n");
        for (int i = 0; i < classParticipationList.length; i++) {
            ret.append("Tutorial ").append(i + 1).append(": ")
                    .append(classParticipationList[i].toString()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassParticipationTracker)) {
            return false;
        }

        ClassParticipationTracker otherClassParticipationTracker = (ClassParticipationTracker) other;
        return Arrays.equals(classParticipationList, otherClassParticipationTracker.classParticipationList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(classParticipationList);
    }

    /**
     * Updates the length of the class participation tracker. Whenever the tutorial count changes.
     */
    public void updateTutorialCountChange(int tutorialCount) {
        if (tutorialCount == classParticipationList.length) {
            return;
        }
        ClassParticipation[] newClassParticipationList = new ClassParticipation[tutorialCount];
        for (int i = 0; i < tutorialCount; i++) {
            if (i < classParticipationList.length) {
                newClassParticipationList[i] = classParticipationList[i];
            } else {
                newClassParticipationList[i] = new ClassParticipation();
            }
        }
        classParticipationList = newClassParticipationList;
    }
}
