package MultiMemetic;

/**
 * Class that specifies all the parameters to be used during the running of
 * the multi-memetic algorithm
 */
public class Settings {

    private static final String NORMAL = "test";
    private static final String HIDDEN = "hidden";

    /**
     * File selection.
     * Select between HIDDEN or NORMAL for file type
     */
    public static final String FILE_TYPE = NORMAL;
    /**
     * Instance selection.
     * HIDDEN file type has 5 instances -> 1,2,3,4,5
     * NORMAL file type has 3 instances -> 1,2,3
     */
    public static final int INSTANCE_NUM = 1;

    /** Trial is number of times experiment is to be repeated. */
    public static final int TRIALS = 5;
    /** Number of results to be shown in the output files. */
    public static final int NUMBER_OF = 100;

    /**
     * Fine tuned parameters to improve results.
     */
    public static final int POPULATION_SIZE = 16;
    /** Number of individuals to be randomly selected. */
    public static final int TOURNAMENT_SIZE = 2;
    /** Chance of a meme mutating. */
    public static final double INNOVATION_RATE = 0.44;
    /** Number of generations program is running for. */
    public static final int GENERATIONS = 150;

    /** 4 memes that can be used.*/
    public static final int MEMES = 4;
    /**
     * Index 0 is CrossoverHeuristics: {Single Point, Uniform}
     * Index 1 is PopulationHeuristics:
     *           {Davis Bit GE and G, Steepest GE and G, Next GE and G}
     * Index 2 is Intensity of Mutation: {1,2,3,4,5,6}
     * Index 3 is Depth of Search: {1,2,3,4,5,6}
     *
     * 0 is excluded from IOM and DOS because:
     * IOM of 0 means there will be no mutation.
     * DOS of 0 means there will be no localSearch.
     */
    public static final int[] OPTIONS_PER_MEME = new int[] {2,6,7,7};

}
