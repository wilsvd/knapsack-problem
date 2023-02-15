package MultiMemetic.CrossoverHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Single point crossover which is a type of crossover heuristic.
 */
public class SinglePointXO extends CrossoverHeuristic {

    /**
     * Constructor of the single point crossover which passes problem and
     * random to its parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public SinglePointXO(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Applies the single point crossover heuristic, so that all bits up to
     * the point are swapped. Finds a random point along the variables to be
     * swapped excluding the 0th index, so that there is at least 1 swap.
     *
     * @param parent1Index int, the index in the population that is the first
     *                    parent.
     * @param parent2Index int, the index in the population that is the second
     *      *                    parent.
     * @param child1Index int, the index in the population that is the first
     *      *                    child.
     * @param child2Index int, the index in the population that is the second
     *      *                    child.
     */
    @Override
    public void applyHeuristic(int parent1Index, int parent2Index, int child1Index, int child2Index) {
        int point = m_Random.nextInt(m_Problem.GetNumberOfItems() - 2) + 1;
        m_Problem.copySolution(parent1Index, child1Index);
        m_Problem.copySolution(parent2Index, child2Index);

        for (int swapIndex = 0; swapIndex < m_Problem.GetNumberOfItems(); swapIndex++) {
            if (swapIndex < point) {
                m_Problem.exchangeBits(child1Index, child2Index, swapIndex);
            }
        }
    }
}
