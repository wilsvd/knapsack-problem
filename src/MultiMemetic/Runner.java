package MultiMemetic;

import MultiMemetic.Problem.Problem;
import MultiMemetic.Writer.GenerationTracker;
import MultiMemetic.Writer.TrialsTracker;
import MultiMemetic.Reader.InstanceReader;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 * Class which runs the program.
 */
public class Runner {

    private static final String NORMAL = "test";
    private static final String HIDDEN = "hidden";

    /**
     * The main method which runs the program to solve the knapsack
     * problem.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        Random random = new Random(50);

        String fileType = Settings.FILE_TYPE;
        int instanceNum = Settings.INSTANCE_NUM;

        verifyFileValidity(fileType, instanceNum);

        InstanceReader instanceReader = new InstanceReader(fileType,instanceNum);
        int[] config = instanceReader.GetNumConfig();
        BigDecimal[][] data = instanceReader.GetNumData();

        TrialsTracker trialsTracker = new TrialsTracker(fileType, instanceNum);

        for (int trial = 1; trial <= Settings.TRIALS; trial++) {
            System.out.println("Trial#" + trial + ":");
            Problem problem = new Problem(config, data, random,
                    Settings.MEMES, Settings.OPTIONS_PER_MEME);

            MultiMeme multiMeme = new MultiMeme(problem,
                    random, Settings.POPULATION_SIZE, Settings.INNOVATION_RATE);

            GenerationTracker generationTracker =
                    new GenerationTracker(fileType, instanceNum, problem, trial);

            for (int gen = 0; gen < Settings.GENERATIONS; gen++) {
                if (gen < Settings.NUMBER_OF) {
                    generationTracker.addInfo();
                }
                multiMeme.runMainLoop();
            }
            generationTracker.closeFile();
            trialsTracker.addInfo(problem, trial);
        }
        trialsTracker.closeFile();
    }

    /**
     * Verifies the validity of the file select and if it is not a valid file
     * then the program will exit.
     *
     * @param fileType a String, which is the type of file.
     * @param instanceNum an int, which is the number of the instance.
     */
    private static void verifyFileValidity(String fileType, int instanceNum) {
        if (fileType == NORMAL && (instanceNum > 3 || instanceNum < 0)) {
            System.out.println("File does not exist. Stopping program.");
            System.exit(1);
        }
        if (fileType == HIDDEN && (instanceNum > 5 || instanceNum < 0)) {
            System.out.println("File does not exist. Stopping program.");
            System.exit(1);
        }
    }
}

