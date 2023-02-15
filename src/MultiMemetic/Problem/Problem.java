package MultiMemetic.Problem;

import MultiMemetic.Memes.Meme;
import MultiMemetic.Settings;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Class which represents the problem instance.
 */
public class Problem {

    private Solution[] m_Population;

    private BigDecimal[] m_Profits;
    private BigDecimal[] m_Weights;
    private BigDecimal m_MaxWeight;

    private int m_NumOfMemes;
    private int[] m_MemeOptions;

    private Random m_Random;

    /**
     * Gets the population.
     *
     * @return a Solution[], which is an array of solutions.
     */
    public Solution[] GetM_Population() {
        return m_Population;
    }

    /**
     * Gets the number of memes.
     *
     * @return an int, which is the number of memes.
     */
    public int GetM_NumOfMemes() {
        return m_NumOfMemes;
    }

    /**
     * Gets the profit of an item.
     *
     * @param index an int, which is the index of the item.
     * @return a double, which is the profit.
     */
    private double getItemProfit(int index) {
        return m_Profits[index].doubleValue();
    }

    /**
     * Get the meme for a solution.
     *
     * @param solutionIndex an int, which is the index of a solution.
     * @param memeIndex an int, which is the index of the meme.
     * @return a Meme
     */
    public Meme GetMeme(int solutionIndex, int memeIndex) {
        return this.m_Population[solutionIndex].GetMeme(memeIndex);
    }

    /**
     * Gets the number of items to select from.
     * @return an int, which is number of items to select from.
     */
    public int GetNumberOfItems() {
        return m_Population[0].getM_NumberOfItems();
    }

    /**
     * Gets the maximum weight for the knapsack.
     * @return a double, which is weight capacity of the bag.
     */
    public double GetMaxWeight() {
        return m_MaxWeight.doubleValue();
    }

    /**
     * Gets the best solution.
     *
     * @return an int[], which is the solution array of items.
     */
    public int[] GetBestSolution() {
        double bestValue = -Double.MAX_VALUE;
        int bestIndex = -1;
        for (int i = 0; i < Settings.POPULATION_SIZE; i++) {

            double tmpScore = EvaluateObjectiveFunction(i);
            if (tmpScore > bestValue) {
                bestValue = tmpScore;
                bestIndex = i;
            }
        }
        return m_Population[bestIndex].GetM_Solution();
    }

    /**
     * Gets the best objective function.
     *
     * @return a double, which is the profit for the best solution.
     */
    public double GetBestObjectiveValue() {
        double bestValue = -Double.MAX_VALUE;
        for (int i = 0; i < Settings.POPULATION_SIZE; i++) {

            double tmpScore = EvaluateObjectiveFunction(i);
            if (tmpScore > bestValue) {
                bestValue = tmpScore;
            }
        }
        return bestValue;
    }

    /**
     * Gets the worst objective function.
     *
     * @return a double, which is the profit for the worst solution.
     */
    public double GetWorstObjectiveValue() {
        double worstValue = Double.MAX_VALUE;
        for (int i = 0; i < Settings.POPULATION_SIZE; i++) {

            double tmpScore = EvaluateObjectiveFunction(i);
            if (tmpScore < worstValue) {
                worstValue = tmpScore;
            }
        }
        return worstValue;
    }

    /**
     * Constructor of the problem class which creates the problem,
     * storing the prices and weights and initialising the population.
     *
     * @param config an int[], which stores the configuration which is
     *               number of items and total weight.
     * @param data a BigDecimal[][], which stores weight and profit for each item.
     * @param random a Random, which can generate a stream of random numbers.
     * @param memeCount an int, which is the number of memes.
     * @param memeOptions an int[], which contains the options for each meme.
     */
    public Problem(int[] config, BigDecimal[][] data, Random random, int memeCount,
                   int[] memeOptions) {
        m_NumOfMemes = memeCount;
        m_MemeOptions = memeOptions;
        m_Random = random;

        storePrices(config, data);
        storeWeights(config, data);
        initialisePopulation(config);
    }

    /**
     * Store the prices in an array.
     *
     * @param config an int[], which stores the configuration which is
     *               number of items and total weight.
     * @param data a BigDecimal[][], which stores weight and profit for each item.
     */
    private void storePrices(int[] config, BigDecimal[][] data) {
        m_Profits = new BigDecimal[(int) config[0]];
        for (int i = 0; i < m_Profits.length; i++) {
            m_Profits[i] = data[i][0];
        }
    }

    /**
     * Store the weights in an array.
     *
     * @param config an int[], which stores the configuration which is
     *               number of items and total weight.
     * @param data a BigDecimal[][], which stores weight and profit for each item.
     */
    private void storeWeights(int[] config, BigDecimal[][] data) {
        m_Weights = new BigDecimal[(int) config[0]];
        m_MaxWeight = new BigDecimal(config[1]);

        for (int i = 0; i < m_Weights.length; i++) {
            m_Weights[i] = data[i][1];
        }
    }

    /**
     * Copies one solution to another solution.
     *
     * @param originIndex an int, which is the index of the original solution.
     * @param destinationIndex an int, which is index of destination solution.
     */
    public void copySolution(int originIndex, int destinationIndex) {
        m_Population[destinationIndex] =
                m_Population[destinationIndex].deepCopy(m_Population[originIndex]);
    }

    /**
     * Exchanges the bits between solutions.
     *
     * @param popMember1 an int, which is the index of population member one.
     * @param popMember2 an int, which is the index of population member two.
     * @param swapIndex an int, which is the index of the bit in the solutions
     *                  to be swapped.
     */
    public void exchangeBits(int popMember1, int popMember2, int swapIndex) {
        int tmpIndex = m_Population[popMember2].GetM_Solution()[swapIndex];
        m_Population[popMember2].GetM_Solution()[swapIndex] =
                m_Population[popMember1].GetM_Solution()[swapIndex];
        m_Population[popMember1].GetM_Solution()[swapIndex] = tmpIndex;
    }

    /**
     * Flips a bit in the solution
     *
     * @param index an int, which is the index of the bit to be flipped.
     * @param populationMember an int, which is the index of the solution.
     */
    public void bitFlip(int index, int populationMember) {

        if (m_Population[populationMember].GetM_Solution()[index] == 0) {
            m_Population[populationMember].GetM_Solution()[index] = 1;
        }
        else {
            m_Population[populationMember].GetM_Solution()[index] = 0;
        }
    }

    /**
     * Initialises the population by creating an array of solutions.
     *
     * @param config an int[], which stores the configuration which is
     *                     number of items and total weight.
     */
    private void initialisePopulation(int[] config) {

        m_Population = new Solution[2 * Settings.POPULATION_SIZE];

        for (int i = 0; i < m_Population.length; i++) {

            m_Population[i] = new Solution(config[0], m_NumOfMemes,
                    m_MemeOptions, m_Random);
        }
    }

    /**
     * Calculates the profit for a solution and checks if it is feasible.
     *
     * @param solutionIndex an int, which is the index of the solution.
     * @return a double, which is the profit for a solution.
     */
    public double EvaluateObjectiveFunction(int solutionIndex) {

        if (weightExceeds(solutionIndex)) {
            return -1;
        }
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (int i = 0; i < GetNumberOfItems(); i++) {
            if (m_Population[solutionIndex].GetM_Solution()[i] == 1) {
                totalProfit = totalProfit.add(m_Profits[i]);
            }
        }
        return totalProfit.doubleValue();
    }

    /**
     * Checks if solution exceeds the total carrying capacity of the bag.
     *
     * @param solutionIndex an int, which is the index of the solution.
     * @return a boolean, whether weight exceeds or not.
     */
    private boolean weightExceeds(int solutionIndex) {
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (int i = 0; i < GetNumberOfItems(); i++) {
            if (m_Population[solutionIndex].GetM_Solution()[i] == 1) {
                totalWeight =
                        totalWeight.add(m_Weights[i]);
            }
        }
        return (totalWeight.doubleValue() > m_MaxWeight.doubleValue());
    }

    /**
     * Delta evaluation function where if a bit is set to 1 then it means an
     * item as added and thus the profit increases and if a bit is set to 0
     * then it means an item was removed and thus profit decreases.
     *
     * @param solutionIndex an int, which is the index of the solution in the
     *                     population.
     * @param flipBitIndex an int, which is the index of the bit that was changed.
     * @return a double, which is the profit for the solution.
     */
    public double DeltaProfitEvaluation(int solutionIndex, int flipBitIndex) {
        double delta;
        if (weightExceeds(solutionIndex)) {
            return -1;
        }
            if (GetM_Population()[solutionIndex].GetM_Solution()[flipBitIndex] == 1) {
                delta = getItemProfit(flipBitIndex);
            }
            else {
                delta = -getItemProfit(flipBitIndex);
            }
        return delta;
    }
}
