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
    public static final String MESSAGE_RANDOM_SUCCESS = "The following students are selected.\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a specific number of students randomly.\n"
            + "Parameters: NUM_OF_STUDENT\n"
            + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_INVALID_NUM_OF_STUDENT = "Number of student to be selected must be more than 0 "
        + "and cannot be more than current number of student displayed";
    private final Integer numOfStudent;

    /**
     * Constructs a RandomCommand object.
     *
     * @param numOfStudent the number of students to be selected.
     */
    public RandomCommand(Integer numOfStudent) {
        requireNonNull(numOfStudent);

        this.numOfStudent = numOfStudent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();
        if (numOfStudent > lastShownList.size() || numOfStudent <= 0) {
            throw new CommandException(MESSAGE_INVALID_NUM_OF_STUDENT);
        }

        Random random = new Random();
        HashSet<Integer> distinctInt = new HashSet<>();

        while (distinctInt.size() < numOfStudent) {
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
        return numOfStudent.equals(e.numOfStudent);
    }
}
