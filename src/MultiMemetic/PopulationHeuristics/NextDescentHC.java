package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;
/**
 * Abstract class for nest descent hill climb which is a type of population
 * heuristic.
 */
public abstract class NextDescentHC extends PopulationHeuristic {

    /**
     * Constructor which passes the problem and random to its parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public NextDescentHC(Problem problem, Random random) {
        super(problem, random);
    }
    /**
     * Applies the next descent hill climb heuristic which bit flips each of
     * the bits in the solution sequentially.
     *
     * Delta evaluation is used to speed up computation so that the result is
     * produced more quickly. Only one bit needs to be checked and if the
     * solution is worse after flipping that one bit then it is flipped back.
     *
     * @param solutionIndex an int, which is the index of the solution.
     */
    @Override
    public void applyHeuristic(int solutionIndex)
    {
        for (int i = 0; i < m_Problem.GetNumberOfItems(); i++)
        {
            m_Problem.bitFlip(i, solutionIndex);
            double delta = m_Problem.DeltaProfitEvaluation(solutionIndex, i);

            /**
             * Checks if the move is accepted. If the move is not accepted than
             * the bit is flipped back.
             */
            if(!acceptMove(delta, 0)) {
                m_Problem.bitFlip(i, solutionIndex);
            }
        }
    }

    /**
     * Abstract method to check if a move is acceptable.
     *
     * @param delta a double, which is change between the old solution and
     *                the new solution.
     * @param threshold a double, which is the threshold the delta is
     *                  compared to.
     * @return a boolean, to indicate if the move was accepted or not.
     */
    public abstract boolean acceptMove(double delta, double threshold);
}
