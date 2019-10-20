package nl.hva.ict.se.ads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArcherLauncher {

    public static void main(String[] args) {
        List<Archer> archers = Archer.generateArchers(100);

        List<Archer> sortedArchers = ChampionSelector.quickSort(archers, new ArcherComparator());

        for (Archer archer : sortedArchers) {
            System.out.println(archer);
        }
    }
}
