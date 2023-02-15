package MultiMemetic.Replacement;

import MultiMemetic.Problem.Problem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for trans-generational replacement with elitism where the best
 * solutions in the population are kept and worst are removed.
 */
public class TransGenElitism {

    /**
     * Replaces the old population with the new population that is found by
     * getting the next generation.
     *
     * @param problem Problem, the problem instance
     * @param populationSize int, the size of the population
     */
    public void doReplacement(Problem problem, int populationSize) {

        int[] newPopulation = getNextGeneration(problem, populationSize);
        Arrays.sort(newPopulation); //prevents overwriting important solutions
        for(int i = 0; i < populationSize; i++) {
            if(i != newPopulation[i]) {
                problem.copySolution(newPopulation[i], i);
            }
        }
    }

    /**
     * Finds the best solutions in the population by using a hashmap to sort
     * the values in descending order while keeping their key intact. The
     * first populationSize of keys are the best solutions.
     *
     * @param problem Problem, the problem instance.
     * @param iPopulationSize int, the size of the population
     * @return an int[], which are the indices of the best solutions
     */
    protected int[] getNextGeneration(Problem problem, int iPopulationSize) {
        int iTotalPopulationSize = iPopulationSize << 1;

        HashMap<Integer, Double> map = new HashMap<>();

        for(int i = 0; i < iTotalPopulationSize; i++) {
            double solutionScore = problem.EvaluateObjectiveFunction(i);
            map.put(i, solutionScore);
        }
        Map<Integer, Double> sortedMap =
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));

        int[] keysArr = new int[iTotalPopulationSize];
        int i = 0;
        for (int key : sortedMap.keySet()) {
            keysArr[i] = key;
            i++;
        }

        int[] bestIndices = new int[iPopulationSize];

        for (int j = iPopulationSize; j < iTotalPopulationSize; j++) {
            bestIndices[j % iPopulationSize] = keysArr[j];
        }

        return bestIndices;
    }
}
