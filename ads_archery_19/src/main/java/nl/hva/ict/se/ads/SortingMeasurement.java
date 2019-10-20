package nl.hva.ict.se.ads;

import java.util.List;

/**
 * @Doel
 * @Author Jesse van Bree
 */
public class SortingMeasurement {
    // private constants used in this class
    private static final int TIMES_TO_RUN = 10;
    private static final int MIN_ARCHERS = 100;
    private static final int ALGORITHM_COUNT = 3;
    private static final int MAX_ARCHERS = 5000000;
    private static final int MAX_RUN_TIME_SEC = 20;
    private static final int ARCHER_COUNT_LIMIT = 16;
    private static final long NANOSEC_TO_ONE_SECOND = 1_000_000_000;
    private static final String[] AINDEX_STRINGS = new String[]{"Insertion sort", "Quick sort", "Collections sort"};

    private static int timeRun;
    private static List<Archer> archers;
    private static ArcherComparator archerComparator = new ArcherComparator();
    private static double[][][] sortTimes = new double[TIMES_TO_RUN][ALGORITHM_COUNT][ARCHER_COUNT_LIMIT];

    public static void main(String[] args) {
        System.out.println("This process will take a while");

        // Test the sorting algorithms x amount of times based on int TIMES_TO_RUN
        for (timeRun = 0; timeRun < TIMES_TO_RUN; timeRun++) {
            System.out.println("Run:" + (timeRun+1));
            int archerCount = MIN_ARCHERS;
            int testIndex = 0;

            // Tests Insertion sort until the amount of archers is above the limit MAX_ARCHERS
            while (archerCount <= MAX_ARCHERS) {
                archers = Archer.generateArchers(archerCount);

                measureInsSortingTime(archers, testIndex);

                // Aborts the loop if the time taken of the last sort exceeded the limit of the int MAX_RUN_TIME_SEC
                if ((sortTimes[timeRun][0][testIndex] / NANOSEC_TO_ONE_SECOND) > MAX_RUN_TIME_SEC) {
                    break;
                }

                testIndex++;
                archerCount = archerCount * 2;
            }

            // reset the archer count to MIN_ARCHERS and reset the index to 0
            archerCount = MIN_ARCHERS;
            testIndex = 0;
            // Tests Quick sort until the amount of archers is above the limit MAX_ARCHERS
            while (archerCount <= MAX_ARCHERS) {
                archers = Archer.generateArchers(archerCount);

                measureQuickSortingTime(archers, testIndex);

                // Aborts the loop if the time taken of the last sort exceeded the limit of the int MAX_RUN_TIME_SEC
                if ((sortTimes[timeRun][1][testIndex] / NANOSEC_TO_ONE_SECOND) > MAX_RUN_TIME_SEC) {
                    break;
                }

                testIndex++;
                archerCount = archerCount * 2;
            }

            // reset the archer count to MIN_ARCHERS and reset the index to 0
            archerCount = MIN_ARCHERS;
            testIndex = 0;
            // Tests Collection sort until the amount of archers is above the limit MAX_ARCHERS
            while (archerCount <= MAX_ARCHERS) {
                archers = Archer.generateArchers(archerCount);

                measureCollectionSortingTime(archers, testIndex);

                // Aborts the loop if the time taken of the last sort exceeded the limit of the int MAX_RUN_TIME_SEC
                if ((sortTimes[timeRun][2][testIndex] / NANOSEC_TO_ONE_SECOND) > MAX_RUN_TIME_SEC) {
                    break;
                }

                testIndex++;
                archerCount = archerCount * 2;
            }
        }
        printResult();
    }

    /**
     * Sorts the archer lists using the insertion sort method
     * and adds the time taken to the array sortTimes
     * @param archers List of Archers to sort
     * @param index index of the run of the test above
     */
    private static void measureInsSortingTime(List<Archer> archers, int index) {
        long startTime;

        startTime = System.nanoTime();
        ChampionSelector.selInsSort(archers, archerComparator);
        sortTimes[timeRun][0][index] = (double) (System.nanoTime() - startTime);
    }

    /**
     * Sorts the archer lists using the quick sort method
     * and adds the time taken to the array sortTimes
     * @param archers List of Archers to sort
     * @param index index of the run of the test above
     */
    private static void measureQuickSortingTime(List<Archer> archers, int index) {
        long startTime;

        startTime = System.nanoTime();
        ChampionSelector.quickSort(archers, archerComparator);
        sortTimes[timeRun][1][index] = (double) System.nanoTime() - startTime;
    }

    /**
     * Sorts the archer lists using the collection sort method
     * and adds the time taken to the array sortTimes
     * @param archers List of Archers to sort
     * @param index index of the run where the time taken needs to be inserted
     */
    private static void measureCollectionSortingTime(List<Archer> archers, int index) {
        long startTime;

        startTime = System.nanoTime();
        ChampionSelector.collectionSort(archers, archerComparator);
        sortTimes[timeRun][2][index] = (double) (System.nanoTime() - startTime);
    }

    /**
     * Prints the result of the values in the array sort times
     */
    private static void printResult() {
        int ac = MIN_ARCHERS; //Archer count
        double[][] total = new double[TIMES_TO_RUN][ALGORITHM_COUNT];
        int[][] iterations = new int[TIMES_TO_RUN][ALGORITHM_COUNT];

        for (int run = 0; run < sortTimes.length; run++) {
            for (int i = 0; i < sortTimes[run].length; i++) {
                iterations[run][i] = 0;

                if(sortTimes[run][i][0] != 0)
                    System.out.println("|---------| Run:" + (run+1) + " |---------|");

                for (int j = 0; j < sortTimes[run][i].length; j++) {
                    double time = sortTimes[run][i][j];
                    if (time != 0) {
                        System.out.printf("Algorithm: %20s Archers count: %10d Time in nanoseconds: %20f Time in seconds: %10f \n", AINDEX_STRINGS[i], ac, time, (time / NANOSEC_TO_ONE_SECOND));
                        total[run][i] += time / NANOSEC_TO_ONE_SECOND;
                        iterations[run][i]++;
                        ac = ac * 2;
                    }
                }
                ac = MIN_ARCHERS;
            }
        }

        System.out.println("\n|---------| Totals and averages |---------|");

        System.out.println("\nTotal of insertion: " +calculateTotal(total, 0));
        System.out.println("Iterations of insertion: " +calculateIterations(iterations, 0));
        System.out.println("Average of insertion: " + calculateAverage(total, 0, iterations));

        System.out.println("\nTotal of quick: " + calculateTotal(total, 1));
        System.out.println("Iterations of quick: " +calculateIterations(iterations, 1));
        System.out.println("Average of quick: " + calculateAverage(total, 1, iterations));

        System.out.println("\nTotal of collection: " + calculateTotal(total, 2));
        System.out.println("Iterations of collection: " +calculateIterations(iterations, 2));
        System.out.println("Average of collection: " + calculateAverage(total, 2, iterations));
    }

    /**
     * Calculates the average of the time taken based on the total time taken of all the tests
     * divided by the times the algorithm has ran
     * @return double average time taken
     */
    private static double calculateAverage(double[][] totals, int sortIndex, int[][] iterations){
        double total = calculateTotal(totals, sortIndex);
        int iterationsCount = calculateIterations(iterations, sortIndex);

        return total / iterationsCount;
    }

    /**
     * Calculates the times the algorithm has run based
     * @return int the times the algorithm has ran
     */
    private static int calculateIterations(int[][] iterations, int sortIndex){
        int iterationsCount = 0;

        for (int[] subIterations: iterations) {
            iterationsCount += subIterations[sortIndex];
        }

        return iterationsCount;
    }

    /**
     * Calculates the total values of the array totals
     * @return double the total time the algorithm took to finish the test
     */
    private static double calculateTotal(double[][] totals, int sortIndex){
        double total = 0;

        for (double[] subValues: totals){
            total += subValues[sortIndex];
        }

        return total;
    }
}