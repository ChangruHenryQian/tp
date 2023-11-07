package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;

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
    public static final String MESSAGE_RANDOM_SUCCESS = "The following students are selected:\n";
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

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();
        if (numOfStudents > lastShownList.size() || numOfStudents <= 0) {
            throw new CommandException(MESSAGE_INVALID_NUM_OF_STUDENTS);
        }

        Random random = new Random();
        HashSet<Integer> distinctInt = new HashSet<>();

        while (distinctInt.size() < numOfStudents) {
            int i = random.nextInt(lastShownList.size());
            distinctInt.add(i);
        }

        Integer[] randomInt = distinctInt.toArray(new Integer[0]);

        StringBuilder result = new StringBuilder(MESSAGE_RANDOM_SUCCESS);

        for (Integer i : randomInt) {
            Student s = lastShownList.get(i);
            result.append(s.getName()).append(" ").append(s.getStudentNumber()).append("\n");
        }

        return new CommandResult(result.toString());
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
