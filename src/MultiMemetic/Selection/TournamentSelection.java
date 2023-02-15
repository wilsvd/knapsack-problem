package MultiMemetic.Selection;

import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Class for tournament selection where the best individual from a random
 * sample size if found.
 */
public class TournamentSelection {

    private Random m_Random;
    private int POPULATION_SIZE;
    private Problem m_Problem;

    /**
     * Constructor which takes in problem instance and random object and the
     * population size to find the best individual from a random selection of
     * individuals.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a stream of numbers.
     * @param POPULATION_SIZE an int, which is the size of population.
     */
    public TournamentSelection(Problem problem, Random random,
                               int POPULATION_SIZE) {

        this.m_Problem = problem;
        this.m_Random = random;
        this.POPULATION_SIZE = POPULATION_SIZE;
    }

    /**
     * Selects the best individual from a random selection of individuals.
     * Initially, individuals are randomly chosen to compete against each
     * other where the individual with the best value wins.
     *
     * @param tournamentSize an int, which is the number of individuals to be
     *                      randomly selected.
     * @return an int, which is the index of the best individual.
     */
    public int tournamentSelection (int tournamentSize)
    {
        double bestValue = -Double.MAX_VALUE;
        int bestIndex = -1;
        int indexArray[] = new int[tournamentSize];

        for (int i = 0; i < tournamentSize; i ++)
        {
            int random = m_Random.nextInt(POPULATION_SIZE);
            indexArray[i] = random;
        }
        for (int i = 0; i < tournamentSize; i++)
        {
            double tmpValue = m_Problem.EvaluateObjectiveFunction(indexArray[i]);
            if (tmpValue > bestValue) {
                bestValue = tmpValue;
                bestIndex = indexArray[i];
            }
        }
        return bestIndex;
    }

}
