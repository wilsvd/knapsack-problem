package MultiMemetic.PopulationHeuristics;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Abstract class for all the different population heuristics.
 */
public abstract class PopulationHeuristic {

    protected Problem m_Problem;
    protected Random m_Random;

    /**
     * Constructor which takes in problem instance and random object, so that
     * all children have access to it.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a stream of numbers.
     */
    public PopulationHeuristic(Problem problem, Random random) {

        this.m_Problem = problem;
        this.m_Random = random;
    }

    /**
     * Abstract method for applying the population heuristics
     * @param solutionIndex an int, which is the index of the solution in the
     *                     population.
     */
    public abstract void applyHeuristic(int solutionIndex);

}
