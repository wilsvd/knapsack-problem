package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Abstract class for davis bit hill climb which is a type of population
 * heuristic.
 */
public abstract class DavisBitHC extends PopulationHeuristic {

    /**
     * Constructor which passes problem instance and random object to the
     * parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a stream of numbers.
     */
    public DavisBitHC(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Applies the davis bit hill climb heuristic which creates an array from
     * 0 to the size of population and shuffles the array to create a random
     * order of bits to be flipped.
     *
     * Delta evaluation is used to speed up computation so that the result is
     * produced more quickly. Only one bit needs to be checked and if the
     * solution is worse after flipping that one bit then it is flipped back.
     *
     * @param solutionIndex an int, which is the index of the solution.
     */
    @Override
    public void applyHeuristic(int solutionIndex) {
        int[] indexArray =
                IntStream.range(0, m_Problem.GetNumberOfItems()).toArray();

        this.shuffleArray(indexArray);

        for(int i = 0; i < m_Problem.GetNumberOfItems(); i++) {
//  0,1,1,1
            m_Problem.bitFlip(indexArray[i], solutionIndex);

            double delta = m_Problem.DeltaProfitEvaluation(solutionIndex, indexArray[i]);

            /**
             * Checks if the move is accepted. If the move is not accepted than
             * the bit is flipped back.
             */
            if (!acceptMove(delta, 0) ) {
                m_Problem.bitFlip(indexArray[i], solutionIndex);
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

    /**
     * Shuffles an array.
     *
     * @param unshuffledArray an int[], which is an unshuffled array.
     */
    public void shuffleArray(int[] unshuffledArray)
    {
        for (int i = unshuffledArray.length - 1; i > 0; i--)
        {
            int index = m_Random.nextInt(i + 1);
            // Simple swap
            int a = unshuffledArray[index];
            unshuffledArray[index] = unshuffledArray[i];
            unshuffledArray[i] = a;
        }
    }

}
