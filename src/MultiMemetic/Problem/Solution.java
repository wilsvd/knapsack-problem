package MultiMemetic.Problem;

import MultiMemetic.Memes.Meme;

import java.util.Random;

/**
 * Class which represents the solution containing the bits that represent
 * the items that have been selected as well as the memes which represent
 * which heuristics to use.
 */
public class Solution {

    private static final int IOM_MEME = 2;
    private static final int DOS_MEME = 3;
    private int[] m_Solution;
    private Meme[] m_Memes;
    private int m_NumberOfItems;
    private int m_NumberOfMemes;
    private int[] m_MemeOptions;
    private Random m_Random;

    /**
     * Gets the solution.
     *
     * @return an int[], which contains items represented by either a 1 or 0.
     */
    public int[] GetM_Solution() {
        return m_Solution;
    }

    /**
     * Gets the memes.
     * @return an int[], which is the array of memes.
     */
    private Meme[] getM_Memes() {
        return m_Memes;
    }

    /**
     * Gets the meme
     * @param memeIndex an int, which is the index of the meme.
     * @return a Meme
     */
    public Meme GetMeme(int memeIndex) {
        return this.m_Memes[memeIndex];
    }

    /**
     * Gets the number of items in a solution.
     * @return an int, which is number of items in a solution.
     */
    public int getM_NumberOfItems() {
        return m_NumberOfItems;
    }

    /**
     * Constructor to create the solutions according to the number of items,
     * memes and number of options.
     *
     * @param numberOfItems an int, which is number of items.
     * @param numberOfMemes an int, which is number of memes.
     * @param memeOptions an array, which contains options for each meme.
     * @param random a Random, which generates a stream of random numbers.
     */
    public Solution(int numberOfItems, int numberOfMemes, int[] memeOptions,
                    Random random) {

        m_NumberOfMemes = numberOfMemes;
        m_MemeOptions = memeOptions;
        m_Random = random;

        m_NumberOfItems = numberOfItems;
        m_Solution = new int[numberOfItems];
        for (int j = 0; j < m_Solution.length; j++) {

            double probability = random.nextDouble();
            if (probability < 0.5) {
                m_Solution[j] = 1;
            }
            else {
                m_Solution[j] = 0;
            }
        }

        this.m_Memes = new Meme[numberOfMemes];

        for(int i = 0; i < m_Memes.length; i++) {
            if (i == IOM_MEME || i == DOS_MEME) {
                m_Memes[i] = new Meme(random.nextInt(1, memeOptions[i]),
                        memeOptions[i]);
            } else {
                m_Memes[i] = new Meme(random.nextInt(memeOptions[i]), memeOptions[i]);
            }
        }
    }

    /**
     * Makes a deep copy of the original solution.
     *
     * @param original a Solution, which is the original solution.
     * @return a Solution, which is the deep copy of the original solution.
     */
    public Solution deepCopy(Solution original) {

        Solution copy = new Solution(original.getM_NumberOfItems(),
                original.m_NumberOfMemes, original.m_MemeOptions,
                original.m_Random);

        System.arraycopy(original.GetM_Solution(), 0, copy.GetM_Solution(), 0,
                copy.GetM_Solution().length);

        System.arraycopy(original.getM_Memes(), 0, copy.getM_Memes(), 0,
                copy.getM_Memes().length);

        return copy;
    }


}
