package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Next descent hill climb class that implements greater than or equal.
 */
public class NextDescentHC_GE extends NextDescentHC{

    /**
     * Constructor which passes problem instance and random object to the
     * parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a stream of numbers.
     */
    public NextDescentHC_GE(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Abstract method to check if a move is acceptable by checking if the
     * delta is greater than or equal to the threshold.
     *
     * @param delta a double, which is change between the old solution and
     *                the new solution.
     * @param threshold a double, which is the threshold the delta is
     *                  compared to.
     * @return a boolean, to indicate if the move was accepted or not.
     */
    @Override
    public boolean acceptMove(double delta, double threshold) {
        return delta >= threshold;
    }
}
