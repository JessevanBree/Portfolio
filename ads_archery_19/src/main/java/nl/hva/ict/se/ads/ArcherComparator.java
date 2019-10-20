package nl.hva.ict.se.ads;

import java.util.Comparator;

public class ArcherComparator implements Comparator<Archer> {
    @Override
    /**
     * Compares 2 archers on total score, Weighed score or id.
     * Returns -1 if archer 2 is ahead, 0 if they are equal, 1 if archer 1 is ahead
     */
    public int compare(Archer o1, Archer o2) {
        if(o1.getTotalScore() == o2.getTotalScore()){
            if(o1.getWeighedScore() == o2.getWeighedScore()){
                return Integer.compare(o1.getId(), o2.getId());
            } else {
                return Integer.compare(o1.getWeighedScore(), o2.getWeighedScore());
            }
        } else {
            return Integer.compare(o1.getTotalScore(), o2.getTotalScore());
        }
    }
}