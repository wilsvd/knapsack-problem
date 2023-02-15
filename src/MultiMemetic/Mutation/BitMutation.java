package MultiMemetic.Mutation;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Class that mutates the meme randomly.
 */
public class BitMutation {

    private static final int ONE_FLIP = 1;

    private double m_MutationRate;

     private Problem m_Problem;
     private Random m_Random;

    /**
     * Constructor which sets the default mutation rate to one flip, so that
     * only one bit flip should occur.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which is an object that generates stream of
     *               random numbers.
     */
    public BitMutation(Problem problem, Random random) {
        m_Problem = problem;
        m_Random = random;
        setMutationRate(ONE_FLIP);
    }

    /**
     * Sets the mutation rate.
     *
     * @param IOM an int, which tells how many bits should be flipped.
     */
    public void setMutationRate(int IOM) {

        m_MutationRate = (IOM / m_Problem.GetNumberOfItems());
    }

    /**
     * Applies the mutation heuristic where bits are flipped by generating a
     * random number and comparing it to the mutation rate.
     *
     * @param solutionIndex an int, which is the index of the solution.
     */
    public void applyHeuristic(int solutionIndex) {

        for (int i = 0; i < m_Problem.GetNumberOfItems(); i++) {

            double probability = m_Random.nextDouble();

            if (probability < m_MutationRate) {
                m_Problem.bitFlip(i, solutionIndex);
            }
        }
    }
}
