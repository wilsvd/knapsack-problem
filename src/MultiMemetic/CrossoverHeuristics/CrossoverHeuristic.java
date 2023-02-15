package MultiMemetic.CrossoverHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Abstract class for crossover heuristics.
 */
public abstract class CrossoverHeuristic {

    protected Random m_Random;
    protected Problem m_Problem;

    /**
     * Constructor for the abstract crossover heuristic.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random steam of numbers.
     */
    public CrossoverHeuristic(Problem problem, Random random) {

        m_Random = random;
        m_Problem = problem;
    }

    /**
     * Abstract method for applying the crossover heuristics.
     *
     * @param parent1Index an int, which is the index of the parent one
     *                     solution.
     * @param parent2Index an int, which is the index of the parent two
     *      *                     solution.
     * @param child1Index an int, which is the index of the child one
     *      *                     solution.
     * @param child2Index an int, which is the index of the child two
     *      *                     solution.
     */
    public abstract void applyHeuristic(int parent1Index, int parent2Index, int child1Index, int child2Index);
}
