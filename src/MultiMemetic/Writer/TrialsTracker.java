package MultiMemetic.Writer;

import MultiMemetic.Problem.Problem;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class which creates a file to track the final results of the trials for a
 * given problem instance.
 */
public class TrialsTracker {

    private FileWriter m_TrialResults;

    /**
     * Constructor which creates the file and names it according to the
     * fileType.
     *
     * @param fileType a String, which is the type of file.
     * @param instanceNum an int, which is the instance number.
     */
    public TrialsTracker(String fileType, int instanceNum) {
        try {
            String path = "OutputFiles/" + fileType + instanceNum + "_";
            m_TrialResults =
                    new FileWriter(path + "trialResults" + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the best solution of the trial for a given problem instance to
     * the file.
     *
     * @param problem a Problem, which is the problem instance.
     * @param trial an int, which is the trial number.
     */
    public void addInfo(Problem problem, int trial) {
        String objectiveValue = String.valueOf(problem.GetBestObjectiveValue());
        String solution = "";
        for (int i = 0; i < problem.GetNumberOfItems(); i++) {
            solution += problem.GetBestSolution()[i];
        }
        System.out.println(objectiveValue);
        System.out.println(solution);

        try {
            m_TrialResults.write("Trial#" + (trial) + "\n");
            m_TrialResults.write(objectiveValue + "\n");
            m_TrialResults.write(solution + "\n");
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
            m_TrialResults.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



}
