package MultiMemetic.Writer;

import MultiMemetic.Problem.Problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class which creates a file to track each of the generations in a given
 * instance.
 */
public class GenerationTracker {

    private FileWriter myWriter;
    private Problem m_Problem;

    /**
     * Constructor which creates the file that is going to be written into.
     *
     * @param fileType a String, which is tells the type of file.
     * @param instanceNum an int, which is the instance number.
     * @param problem a Problem, which is the problem instance.
     * @param trial an int, which is the trial number.
     */
    public GenerationTracker(String fileType, int instanceNum, Problem problem,
                             int trial) {
        m_Problem = problem;
        try {
            String path = "OutputFiles/" + fileType + instanceNum + "_";
            String problemInfo =
                    problem.GetNumberOfItems() + "_" + problem.GetMaxWeight() + "_";
            myWriter =
                    new FileWriter(path + problemInfo +
                            "trial" + trial + "_" + "output" + ".txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Adds information regarding each generation to the previously created
     * file.
     *
     */
    public void addInfo() {
        try {

            myWriter.write("Best: " + m_Problem.GetBestObjectiveValue() +
                    " Current: " + m_Problem.GetWorstObjectiveValue() + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Closes the file once finished reading.
     */
    public void closeFile() {
        try {
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Fail to close");
        }

    }
}
