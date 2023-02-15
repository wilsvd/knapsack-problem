package MultiMemetic.CrossoverHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Uniform crossover which is a type of crossover heuristic.
 */
public class UniformXO extends CrossoverHeuristic {

    /**
     * Constructor of the uniform crossover which passes problem and
     * random to its parent class.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public UniformXO(Problem problem, Random random) {
        super(problem, random);
    }

    /**
     * Applies the uniform crossover heuristic, so that all bits have a
     * chance of being flipped. Generates random numbers and if
     * it is less than the 0.5 threshold then the bit is flipped.
     *
     * @param parent1Index int, the index in the population that is the first
     *                    parent.
     * @param parent2Index int, the index in the population that is the
     *                     second parent.
     * @param child1Index int, the index in the population that is the first
     *                    child.
     * @param child2Index int, the index in the population that is the second
     *                   child.
     */
    @Override
    public void applyHeuristic(int parent1Index, int parent2Index,
                               int child1Index, int child2Index) {

        m_Problem.copySolution(parent1Index, child1Index);
        m_Problem.copySolution(parent2Index, child2Index);

        for  (int j = 0; j < m_Problem.GetNumberOfItems(); j++)
        {
            double probability = m_Random.nextDouble();
            if (probability < 0.5)
                m_Problem.exchangeBits(child1Index, child2Index, j);
        }

    }
}

