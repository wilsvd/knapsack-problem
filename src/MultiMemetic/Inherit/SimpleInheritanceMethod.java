package MultiMemetic.Inherit;

import MultiMemetic.Memes.Meme;
import MultiMemetic.Problem.Problem;

import java.util.Random;

/**
 * Class to pass on memes from parents to their children.
 */
public class SimpleInheritanceMethod {

    private final Problem m_Problem;
    private final Random m_Random;

    /**
     * Constructor which takes the problem instance and random to select which
     * parent to use if parents objective value is equivalent.
     *
     * @param problem a Problem, the problem instance.
     * @param random a Random, which generates a random stream of numbers.
     */
    public SimpleInheritanceMethod(Problem problem, Random random) {

        this.m_Problem = problem;
        this.m_Random = random;
    }

    /**
     * Children inherit memes from the best parent. The parents are compared
     * and the one with the best objective value, the meme is passed onto the
     * children.
     *
     * @param parent1 an int, which is the index of the parent one solution.
     * @param parent2 an int, which is the index of the parent two solution.
     * @param child1 an int, which is the index of the child one solution.
     * @param child2 an int, which is the index of the child two solution.
     */
    public void performMemeticInheritance(int parent1, int parent2, int child1, int child2) {

        double p1Value = m_Problem.EvaluateObjectiveFunction(parent1);
        double p2Value = m_Problem.EvaluateObjectiveFunction(parent2);
//        If the parents have the same value, randomly pick one parent.
        if (p1Value == p2Value) {
            int inherit = m_Random.nextInt(2);
            if (inherit == 0) {
                setMemesOptions(parent1, child1, child2);
            }
            else {
                setMemesOptions(parent2, child1, child2);
            }
        }
        else if (p1Value > p2Value) {
            setMemesOptions(parent1, child1, child2);
        }
        else {
            setMemesOptions(parent2, child1, child2);
        }
    }

    /**
     * Sets the meme option for the children.
     *
     * @param parent an int, which is the index of the parent solution.
     * @param child1 an int, which is the index of the child one solution.
     * @param child2 an int, which is the index of the child two solution.
     */
    private void setMemesOptions(int parent, int child1, int child2) {
        for (int meme = 0; meme < m_Problem.GetM_NumOfMemes(); meme++) {
            Meme pMeme = m_Problem.GetMeme(parent, meme);
            Meme c1Meme = m_Problem.GetMeme(child1, meme);
            Meme c2Meme = m_Problem.GetMeme(child2, meme);

            c1Meme.setMemeOption(pMeme.getMemeOption());
            c2Meme.setMemeOption(pMeme.getMemeOption());
        }
    }
}
