package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Abstract class for steepest descent hill climb which is a type of population
 * heuristic.
 */
public abstract class SteepestDescentHC extends PopulationHeuristic {
    /**
     * Constructor which passes problem instance and random object to the
     * parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public SteepestDescentHC(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Applies the steepest descent hill climb heuristic which finds the
     * biggest improvement in solutions.
     *
     * Delta evaluation is used to speed up computation so that the result is
     * produced more quickly. Only one bit needs to be checked and if the
     * solution is worse after flipping that one bit then it is flipped back.
     *
     * @param solutionIndex an int, which is the index of the solution.
     */
    @Override
    public void applyHeuristic(int solutionIndex) {
        int bestIndex = -1;
        double biggestDelta = 0;

        for (int i = 0; i < m_Problem.GetNumberOfItems(); i++) {
            m_Problem.bitFlip(i, solutionIndex);
            double currDelta = m_Problem.DeltaProfitEvaluation(solutionIndex, i);

            /**
             * Checks if the move is accepted. If the move is not accepted than
             * the bit is flipped back.
             */
            if (acceptMove(currDelta, biggestDelta)) {
                bestIndex = i;
                biggestDelta = currDelta;
            }
            m_Problem.bitFlip(i, solutionIndex);
        }
        if (bestIndex != -1) {
            m_Problem.bitFlip(bestIndex, solutionIndex);
        }
    }

    /**
     * Abstract method to check if a move is acceptable.
     *
     * @param delta a double, which is change between the old solution and
     *                the new solution.
     * @param bestDelta a double, which is the biggest improvement between
     *                 an old solution and new solution.
     * @return a boolean, to indicate if the move was accepted or not.
     */
    public abstract boolean acceptMove(double delta, double bestDelta);
}
