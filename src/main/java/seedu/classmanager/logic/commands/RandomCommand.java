package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;

/**
 * Selects a specific number of students randomly from the students displayed.
 */
public class RandomCommand extends Command {
    public static final String COMMAND_WORD = "random";
    public static final String MESSAGE_RANDOM_SUCCESS_SINGLE_STUDENT = "The following student is selected:\n";
    public static final String MESSAGE_RANDOM_SUCCESS_MULTIPLE_STUDENT = "The following students are selected:\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a specific number of students randomly.\n"
            + "Parameters: NUMBER_OF_STUDENTS\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_INVALID_NUM_OF_STUDENTS = "Number of students to be selected must be a positive "
            + "integer(without decimal places) smaller than or equal to the current number of students displayed.\n";
    private final Integer numOfStudents;

    /**
     * Constructs a RandomCommand object.
     *
     * @param numOfStudents the number of students to be selected.
     */
    public RandomCommand(Integer numOfStudents) {
        requireNonNull(numOfStudents);

        this.numOfStudents = numOfStudents;
    }

    /**
     * Selects a specific number of students randomly from the list of displayed students.
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory The command history to record this command.
     * @return A {@code CommandResult} with the feedback message of the operation result.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();
        if (numOfStudents > lastShownList.size() || numOfStudents <= 0) {
            throw new CommandException(MESSAGE_INVALID_NUM_OF_STUDENTS);
        }

        Integer[] randomInt = generateRandomInt(numOfStudents, lastShownList.size());

        StringBuilder result = numOfStudents == 1
                ? new StringBuilder(MESSAGE_RANDOM_SUCCESS_SINGLE_STUDENT)
                : new StringBuilder(MESSAGE_RANDOM_SUCCESS_MULTIPLE_STUDENT);
        for (Integer i : randomInt) {
            Student s = lastShownList.get(i);
            result.append(s.getName()).append(" ").append(s.getStudentNumber()).append("\n");
        }

        return new CommandResult(result.toString());
    }

    /**
     * Generates an array of distinct non-negative random integers.
     *
     * @param size the size of the array.
     * @param upper the upper bound.
     */
    public static Integer[] generateRandomInt(int size, int upper) {
        requireAllNonNull(size, upper);
        assert upper >= 0 && size >= 0;

        HashSet<Integer> distinctInt = new HashSet<>();
        Random random = new Random();
        while (distinctInt.size() < size) {
            int i = random.nextInt(upper);
            distinctInt.add(i);
        }
        return distinctInt.toArray(new Integer[0]);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RandomCommand)) {
            return false;
        }

        RandomCommand e = (RandomCommand) other;
        return numOfStudents.equals(e.numOfStudents);
    }
}
