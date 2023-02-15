package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Steepest descent hill climb class that implements strictly greater.
 */
public class SteepestDescentHC_G extends SteepestDescentHC{

    /**
     * Constructor which passes problem and random to the parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public SteepestDescentHC_G(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Abstract method to check if a move is acceptable by checking if the
     * delta is greater than the threshold.
     *
     * @param delta a double, which is change between the old solution and
     *                the new solution.
     * @param bestDelta a double, which is the biggest improvement between an
     *                 old solution and new solution.
     * @return a boolean, to indicate if the move was accepted or not.
     */
    @Override
    public boolean acceptMove(double delta, double bestDelta) {
        return delta > bestDelta;
    }
}
