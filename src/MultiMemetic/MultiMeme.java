package MultiMemetic;

import MultiMemetic.CrossoverHeuristics.CrossoverHeuristic;
import MultiMemetic.CrossoverHeuristics.SinglePointXO;
import MultiMemetic.CrossoverHeuristics.UniformXO;
import MultiMemetic.Inherit.SimpleInheritanceMethod;
import MultiMemetic.Memes.Meme;
import MultiMemetic.Mutation.BitMutation;
import MultiMemetic.PopulationHeuristics.*;
import MultiMemetic.Problem.Problem;
import MultiMemetic.Replacement.TransGenElitism;
import MultiMemetic.Selection.TournamentSelection;

import java.util.Random;

/**
 * Class for the multi-memetic algorithm.
 */
public class MultiMeme {

    private static final int CROSSOVER_MEME = 0;
    private static final int HILL_CLIMB_MEME = 1;
    private static final int IOM_MEME = 2;
    private static final int DOS_MEME = 3;

    private Problem m_Problem;
    private Random m_Random;
    private int m_PopSize;
    private final double m_InnovationRate;

    private CrossoverHeuristic[] m_Crossover;
    private BitMutation m_Mutation;
    private PopulationHeuristic[] m_LocalSearch;
    private TransGenElitism m_Replacement;
    private TournamentSelection m_Selection;
    private SimpleInheritanceMethod m_Inheritance;

    /**
     *
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     * @param populationSize an int, which is the size of the population.
     * @param innovationRate a double, which is the chance of a meme mutating.
     * @param crossover an array containing the different crossover heuristics.
     * @param mutation mutates the solutions.
     * @param replacement replaces the previous generation with a new one.
     * @param selection selects the best individual from randomly selected
     *                  individuals.
     * @param inheritance passes on the meme from the parent to the child.
     * @param lss an array containing the different local search heuristics.
     */
    private MultiMeme(Problem problem, Random random, int populationSize,
                     double innovationRate, CrossoverHeuristic[] crossover,
                     BitMutation mutation, TransGenElitism replacement,
                     TournamentSelection selection, SimpleInheritanceMethod inheritance,
                     PopulationHeuristic[] lss) {

        m_Problem = problem;
        m_Random = random;
        m_PopSize = populationSize;

        this.m_InnovationRate = innovationRate;
        this.m_Crossover = crossover;
        this.m_Mutation = mutation;
        this.m_Replacement = replacement;
        this.m_Selection = selection;
        this.m_Inheritance = inheritance;
        this.m_LocalSearch = lss;
    }

    /**
     * A constructor for the multi-meme where the relevant classes are
     * instantiated in order for the multi-meme to run properly.
     *
     * @param problem a Problem, which is the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     * @param populationSize an int, which is the size of the population.
     * @param innovationRate a double, which is chance of a meme mutating.
     */
    public MultiMeme(Problem problem, Random random, int populationSize,
                     double innovationRate) {

        this(problem, random, populationSize, innovationRate,
                // Crossover heuristics
                new CrossoverHeuristic[] {
                    new UniformXO(problem, random), // [0]
                    new SinglePointXO(problem, random), //[1]
                },
                // mutation
                new BitMutation(problem, random),
                // replacement algorithm
                new TransGenElitism(),
                // parent selection
                new TournamentSelection(problem, random, populationSize),
                // memeplex inheritance
                new SimpleInheritanceMethod(problem, random),
                // Population heuristics
                new PopulationHeuristic[] {
                        new DavisBitHC_G(problem, random), // [0]
                        new DavisBitHC_GE(problem, random), // [1]
                        new SteepestDescentHC_G(problem, random), // [2]
                        new SteepestDescentHC_GE(problem, random), // [3]
                        new NextDescentHC_G(problem, random), // [4]
                        new NextDescentHC_GE(problem, random) // [5]
                }
        );
    }

    /**
     * Runs the memetic algorithm where two parents are selected using
     * tournament selection and apply a crossover based on the better parents
     * meme to generate offspring.
     *
     * Inherit the memes using simple inheritance method and then mutate the
     * memes of the children.
     *
     * apply mutation to the offspring based on the mutation meme and then
     * carry out a local search a number of times based on the depth of search
     * meme, additionally the local search method is dependent on the childs
     * local search meme.
     *
     * Finally, the old generation is replaced with the best solutions.
     */
    public void runMainLoop() {

        for (int i = 0; i < this.m_PopSize - 1; i+=2)
        {
            // select two parents. we don't care if they are the same this week
            /**
             * Selects two parents using tournament selection, if both parents
             * are the same get the next solution in the population and
             * assign it to parent 2.
             */
            int p1 = m_Selection.tournamentSelection(Settings.TOURNAMENT_SIZE);
            int p2 = m_Selection.tournamentSelection(Settings.TOURNAMENT_SIZE);
            if (p1 == p2)
            {
                p2 = (p2 + 1) % m_PopSize;
            }

            /** Select the children*/
            int c1 = m_PopSize + i ;
            int c2 = m_PopSize + i + 1;

            /** Select crossover meme and apply crossover*/
            int crossMeme = findMemeOptionDependentOnParent(p1, p2, CROSSOVER_MEME);
            m_Crossover[crossMeme].applyHeuristic(p1, p2, c1, c2);

            this.m_Inheritance.performMemeticInheritance(p1, p2, c1, c2);

            /** Perform mutation on the memes of the children*/
            this.performMutationOfMemeplex(c1);
            this.performMutationOfMemeplex(c2);

            /** Mutate the children*/
            this.applyMutationForChildDependentOnMeme(c1, IOM_MEME);
            this.applyMutationForChildDependentOnMeme(c2, IOM_MEME);

            /** Apply local search number of items based on DOS meme on the
             * children
             */
            for (int depth = 0; depth < findMemeOptionDependentOnParent(p1, p2, DOS_MEME); depth++) {
                this.applyLocalSearchForChildDependentOnMeme(c1, HILL_CLIMB_MEME);
                this.applyLocalSearchForChildDependentOnMeme(c2, HILL_CLIMB_MEME);
            }
        }
        /** Replace the old generation with the best in the population.*/
        this.m_Replacement.doReplacement(m_Problem, m_PopSize);
    }

    /**
     * Gets the meme option of the best parent.
     *
     * @param p1 an int, which is the index of the parent 1 solution.
     * @param p2 an int, which is the index of the parent 2 solution.
     * @param memeIndex an int, which is the meme to be found.
     * @return an int, which is meme option.
     */
    public int findMemeOptionDependentOnParent(int p1, int p2,
                                               int memeIndex) {

        double p1Value = m_Problem.EvaluateObjectiveFunction(p1);
        double p2Value =  m_Problem.EvaluateObjectiveFunction(p2);
        int option;
        if (p1Value == p2Value) {
            double random = m_Random.nextDouble();
            if (random < 0.5) {
                option = m_Problem.GetMeme(p1, memeIndex).getMemeOption();
            }
            else {
                option = m_Problem.GetMeme(p2, memeIndex).getMemeOption();
            }
        }
        else if (p1Value > p2Value) {
            option = m_Problem.GetMeme(p1, memeIndex).getMemeOption();
        }
        else {
            option = m_Problem.GetMeme(p2, memeIndex).getMemeOption();
        }
        return option;
    }

    /**
     * Applies a mutation depending on the child's meme option
     * for the mutation meme.
     *
     * @param childIndex an int, which is the index of the child solution.
     * @param memeIndex an int, which is the meme related to mutation.
     */
    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {
        Meme meme = m_Problem.GetMeme(childIndex, memeIndex);
        m_Mutation.setMutationRate(meme.getMemeOption());
        m_Mutation.applyHeuristic(childIndex);
    }

    /**
     * Applies a local search depending on the child's meme option for the
     * local search meme.
     *
     * @param childIndex an int, which is the index of the child solution.
     * @param memeIndex an int, which is the meme related to local search.
     */
    public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {

        Meme meme = m_Problem.GetMeme(childIndex, memeIndex);
        m_LocalSearch[meme.getMemeOption()].applyHeuristic(childIndex); //
    }

    /**
     * Applies a random mutation to each meme within the memeplex of the
     * solution.
     *
     * The meme option for a meme is randomly changed to a different option
     * if the random number generated is less than the innovation rate.
     *
     * @param solutionIndex an int, which is the index of the solution.
     */
    public void performMutationOfMemeplex(int solutionIndex) {
        for (int n = 0; n< m_Problem.GetM_NumOfMemes() ; n++) {
            double random = m_Random.nextDouble();

            Meme meme = m_Problem.GetMeme(solutionIndex, n);
            int memeOption = meme.getMemeOption();

            if (random < Settings.INNOVATION_RATE)
            {
                int rndOption = m_Random.nextInt(meme.getTotalOptions());
                /**
                 * if random option is the same as the current option then
                 * the next meme option is chosen.
                 */
                if (rndOption == memeOption)
                    rndOption = (memeOption + 1) % meme.getTotalOptions();

                meme.setMemeOption(rndOption);
            }
        }
    }
}
