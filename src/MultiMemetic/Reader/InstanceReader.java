package MultiMemetic.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Class that reads the files representing instances to retrieve the data
 * regarding the instances.
 */
public class InstanceReader {

    private static final String NORMAL = "test";
    private static final String HIDDEN = "hidden";

    private static final int INSTANCE_ONE = 1;
    private static final int INSTANCE_TWO = 2;
    private static final int INSTANCE_THREE = 3;
    private static final int INSTANCE_FOUR = 4;
    private static final int INSTANCE_FIVE = 5;

    private int[] numConfig = new int[2];
    private BigDecimal[][] numData;

    /**
     * Gets an array containing configuration which is number of items and
     * maximum weight.
     *
     * @return an int[], storing number of items and max weight.
     */
    public int[] GetNumConfig() {
        return numConfig;
    }

    /**
     * Gets a 2 dimensional array containing the profit and weight for each of
     * the items.
     *
     * @return a BigDecimal[][], which stores profit and weight for each item.
     */
    public BigDecimal[][] GetNumData() {
        return numData;
    }

    /**
     * Constructor which takes in the file type and the instance number as
     * parameters, so that it reads the correct file.
     *
     * @param fileType a String, which is the type of file.
     * @param instanceNum an int, which is the instance number.
     */
    public InstanceReader(String fileType, int instanceNum) {
        String pathPrefix = "initialTestInstances/";
        String pathInstance;
        pathInstance = getPathInstance(fileType, instanceNum);
        readFile(pathPrefix, pathInstance);
    }

    /**
     * Reads the file and stores the configuration in the configuration array
     * and stores the data of each of the items in the 2d BigDecimal array.
     * Data is read as a BigDecimal because it is more precise than a double.
     *
     * @param pathPrefix a String, which is the prefix of the path.
     * @param pathInstance a String, which details which file to select.
     */
    private void readFile(String pathPrefix, String pathInstance) {
        try {
            File myObj = new File(pathPrefix + pathInstance);
            Scanner myReader = new Scanner(myObj);
            String[] instanceConfig = myReader.nextLine().split(" ");

            for (int i = 0; i < 2; i++) {
                numConfig[i] = Integer.valueOf(instanceConfig[i]);
            }
            int n_items = Integer.valueOf((int) numConfig[0]);

            String[][] instanceData = new String[n_items][2];
            numData = new BigDecimal[n_items][2];

            int i = 0;
            while (myReader.hasNextLine()) {
                instanceData[i] = myReader.nextLine().split(" ");
                for (int j = 0; j < 2; j++) {
                    numData[i][j] = new BigDecimal(instanceData[i][j]);
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    /**
     * Gets the path of the file based on which file type is selected and the
     * instance that is wanted.
     *
     * @param fileType a String, which tells the type of file.
     * @param instanceNum an int, which is the number of the instance.
     * @return a String, which is the path to the file.
     */
    private String getPathInstance(String fileType, int instanceNum) {
        String pathInstance = "";
        if (fileType == NORMAL) {
            if (instanceNum == INSTANCE_ONE) {
                pathInstance = "test1_4_20.txt";
            }
            else if (instanceNum == INSTANCE_TWO) {
                pathInstance = "test2_10_269.txt";
            }
            else if (instanceNum == INSTANCE_THREE) {
                pathInstance = "test3_20_879.txt";
            }
        }
        else if (fileType == HIDDEN){
            if (instanceNum == INSTANCE_ONE) {
                pathInstance = "hidden1_5_80.txt";
            }
            else if (instanceNum == INSTANCE_TWO) {
                pathInstance = "hidden2_7_50.txt";
            }
            else if (instanceNum == INSTANCE_THREE) {
                pathInstance = "hidden3_10_60.txt";
            }
            else if (instanceNum == INSTANCE_FOUR) {
                pathInstance = "hidden4_15_375.txt";
            }
            else if (instanceNum == INSTANCE_FIVE) {
                pathInstance = "hidden5_23_10000.txt";
            }
        }
        return pathInstance;
    }
}
