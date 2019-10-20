package nl.hva.ict.se.ads;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Place all your own tests for Archer in this class. Tests in any other class will be ignored!
 */
public class ExtendedArcherTest extends ArcherTest {
    private List<Archer> archers;
    private int[] scores;

    @BeforeEach
    void setUp() {
        archers = Archer.generateArchers(2);
        scores = new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 15};
    }

    @AfterEach
    void tearDown() {
        archers = null;
        scores = null;
    }

    @Test
    void archersGeneratedCorrectly() {
        assertEquals(2, archers.size());
    }

    @Test
    void testIfCorrectTotalScore() {
        Archer archer = archers.get(0);
        archer.setScores(scores);
        assertEquals(174, archer.getTotalScore());
    }

    @Test
    void testIfCorrectWeighedScore() {
        Archer archer = archers.get(0);
        archer.setScores(scores);
        assertEquals(108, archer.getWeighedScore());
    }

    @Test
    void checkIfComparatorWorksLess() {
        Archer firstArcher = archers.get(0);
        Archer secondArcher = archers.get(1);

        firstArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 14});
        secondArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 15});

        assertEquals(-1, firstArcher.compareTo(secondArcher));
    }

    @Test
    void checkIfComparatorWorksGreater() {
        Archer firstArcher = archers.get(0);
        Archer secondArcher = archers.get(1);

        firstArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 15});
        secondArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 14});

        assertEquals(1, firstArcher.compareTo(secondArcher));
    }

    @Test
    void checkIfComparatorWorksEqual() {
        Archer firstArcher = archers.get(0);
        Archer secondArcher = archers.get(1);

        firstArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 15});
        secondArcher.setScores(new int[]{12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 15});

        // Scores are equal so this checks if the first archer id is lower than the second
        assertEquals(-1, firstArcher.compareTo(secondArcher));
    }

    @Test
    void testCorrectToStringImplementation() {
        Archer archer = archers.get(0);

        archer.setScores(scores);
        archer.setFirstName("Firstname");
        archer.setLastName("LASTNAME");

        assertEquals("135805 (174 / 108) Firstname LASTNAME", archer.toString());
    }
}
