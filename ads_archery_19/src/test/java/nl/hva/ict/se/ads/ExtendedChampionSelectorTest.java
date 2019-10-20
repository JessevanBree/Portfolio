package nl.hva.ict.se.ads;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Place all your own tests for ChampionSelector in this class. Tests in any other class will be ignored!
 */
public class ExtendedChampionSelectorTest extends ChampionSelectorTest {

    @Test
    void quickSortAndCollectionSortResultInSameOrder() {
        List<Archer> unsortedArchersForQuick = Archer.generateArchers(15);
        List<Archer> unsortedArchersForCollection = new ArrayList<>(unsortedArchersForQuick);

        List<Archer> sortedArchersQuick = ChampionSelector.quickSort(unsortedArchersForQuick, comparator);
        List<Archer> sortedArchersCollection = ChampionSelector.collectionSort(unsortedArchersForCollection, comparator);

        assertEquals(sortedArchersCollection, sortedArchersQuick);
    }

    @Test
    void testInsertionSortSeparately() {
        List<Archer> unsortedArchers = Archer.generateArchers(4);
        final int NUMBER_OF_TENS = 27;

        for (int i = 0; i < unsortedArchers.size(); i++) {
            unsortedArchers.get(i).setScores(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NUMBER_OF_TENS + i});
        }

        List<Archer> sortedArchers = ChampionSelector.selInsSort(unsortedArchers, comparator);

        for (int i = 0; i < sortedArchers.size() - 1; i++) {
            Archer currentArcher = sortedArchers.get(i);
            Archer nextArcher = sortedArchers.get(i + 1);

            assertEquals(1, currentArcher.compareTo(nextArcher));
        }
    }

    @Test
    void testQuickSortSeparately() {
        List<Archer> unsortedArchers = Archer.generateArchers(4);
        final int NUMBER_OF_TENS = 27;

        for (int i = 0; i < unsortedArchers.size(); i++) {
            unsortedArchers.get(i).setScores(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NUMBER_OF_TENS + i});
        }

        List<Archer> sortedArchers = ChampionSelector.quickSort(unsortedArchers, comparator);

        for (int i = 0; i < sortedArchers.size() - 1; i++) {
            Archer currentArcher = sortedArchers.get(i);
            Archer nextArcher = sortedArchers.get(i + 1);

            assertEquals(1, currentArcher.compareTo(nextArcher));
        }
    }

    @Test
    void testCollectionSortSeparately() {
        List<Archer> unsortedArchers = Archer.generateArchers(4);
        final int NUMBER_OF_TENS = 27;

        for (int i = 0; i < unsortedArchers.size(); i++) {
            unsortedArchers.get(i).setScores(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NUMBER_OF_TENS + i});
        }

        List<Archer> sortedArchers = ChampionSelector.collectionSort(unsortedArchers, comparator);

        for (int i = 0; i < sortedArchers.size() - 1; i++) {
            Archer currentArcher = sortedArchers.get(i);
            Archer nextArcher = sortedArchers.get(i + 1);

            assertEquals(1, currentArcher.compareTo(nextArcher));
        }
    }
}
