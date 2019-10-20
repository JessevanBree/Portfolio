package nl.hva.ict.se.ads;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Given a list of Archer's this class can be used to sort the list using one of three sorting algorithms.
 */
public class ChampionSelector {
    /**
     * This method uses either selection sort or insertion sort for sorting the archers.
     */
    public static List<Archer> selInsSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        // Loop to all archers in the List
        for (Archer archer: archers) {
            int previousArcherIndex = archers.indexOf(archer) - 1;

            // Loop backwards through the List and check if previousArcher has a higher score
            // If the previousArcher score is lower than set the previousArcherIndex subtracted by 1 and try again until
            // the previousArcher has a higher score then switch the position of archer and previous archer
            while (previousArcherIndex >= 0 && scoringScheme.compare(archers.get(previousArcherIndex), archer) < 0) {
                archers.set(previousArcherIndex + 1, archers.get(previousArcherIndex));
                previousArcherIndex = previousArcherIndex - 1;
            }
            archers.set(previousArcherIndex + 1, archer);
        }

        // Return the sorted List
        return archers;
    }

    /**
     * This method calls the private helper method quickSort which starts a recursive quicksort algorithm.
     *
     * @param archers List of archers to sort
     * @param scoringScheme Comparator used to compare archers
     * @return sorted list of archers from high to low
     */
    public static List<Archer> quickSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        quickSort(archers, scoringScheme, 0, archers.size() - 1);
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers.
     *
     * @param archers List of archers to sort
     * @param scoringScheme Comparator used to compare archers
     * @param low beginning of the list
     * @param high end of the list
     */
    private static void quickSort(List<Archer> archers, Comparator<Archer> scoringScheme, int low, int high) {
        // Check if there are items left to sort
        if (low < high) {
            // Execute the partitioning code and store the index of the pivot
            int partitioningIndex = partition(archers, scoringScheme, low, high);

            // Sort left and right side of the pivot
            quickSort(archers, scoringScheme, low, partitioningIndex - 1);
            quickSort(archers, scoringScheme, partitioningIndex + 1, high);
        }
    }

    /**
     * This method takes the last element of the list as the pivot. It checks each
     * element and swaps it before the pivot if the score is higher than the pivot.
     * This way, the partition being sorted will be sorted from high to low.
     *
     * @param archers List of archers to sort
     * @param scoringScheme Comparator used to compare archers
     * @param low beginning of the partition
     * @param high end of the partition
     * @return pivot index
     */
    private static int partition(List<Archer> archers, Comparator<Archer> scoringScheme, int low, int high) {
        // Take the last archer as the pivot
        Archer pivot = archers.get(high);
        int i = low - 1;

        // Loop from beginning of the partition to the end
        for (int j = low; j < high; j++) {
            // If the archer left of the pivot has a higher score than the right, swap them
            if (scoringScheme.compare(archers.get(j), pivot) > 0) {
                i++;
                swap(archers, i, j);
            }
        }

        swap(archers, i + 1, high);
        return i + 1;
    }

    /**
     * Method for swapping items in the archer list
     * @param archers List of archers
     * @param firstIndex index of the first item
     * @param secondIndex index of the second item
     */
    private static void swap(List<Archer> archers, int firstIndex, int secondIndex) {
        Archer temp = archers.get(firstIndex);
        archers.set(firstIndex, archers.get(secondIndex));
        archers.set(secondIndex, temp);
    }

    /**
     * This method uses the Java collections sort algorithm for sorting the archers.
     */
    public static List<Archer> collectionSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        // Sort the archers List backwards because highest score is the first value
        Collections.sort(archers, Collections.reverseOrder(scoringScheme));
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers in such a way that it is able to cope with an Iterator.
     *
     * <b>THIS METHOD IS OPTIONAL</b>
     */
    public static Iterator<Archer> quickSort(Iterator<Archer> archers, Comparator<Archer> scoringScheme) {
        return null;
    }

}
