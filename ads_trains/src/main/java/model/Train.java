package model;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Train implements Iterable<Wagon> {
    private Locomotive engine;
    private Wagon firstWagon;
    private String destination;
    private String origin;
    private int numberOfWagons;

    public Train(Locomotive engine, String origin, String destination) {
        this.engine = engine;
        this.destination = destination;
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public int getNumberOfWagons() {
        return numberOfWagons;
    }

    public Wagon getFirstWagon() {
        return firstWagon;
    }

    public void setFirstWagon(Wagon firstWagon) {
        this.firstWagon = firstWagon;
    }

    /**
     * when wagons are hooked to or detached from a train,
     * the number of wagons of the train should be reset
     * this method does the calculation
     */
    public void resetNumberOfWagons() {
        if(firstWagon != null){
            numberOfWagons = firstWagon.getNumberOfWagonsAttached();
        } else {
            numberOfWagons = 0;
        }
    }

    /**
     * find a wagon on a train by id, return the position (first wagon had position 1)
     * if not found, then return -1
     * @param wagonId int id of the wagon
     * @return int position of wagon
     */
    public int getPositionOfWagon(int wagonId) {
        int position = 1;

        if (firstWagon == null) return -1;

        for (Wagon wagon : this) {
            if (wagon.getWagonId() == wagonId) {
                return position;
            }
            position++;
        }

        return -1;
    }

    /**
     * find the wagon on a given position on the train
     * position of wagons start at 1 (firstWagon of train)
     * use exceptions to handle a position that does not exist
     * @param position int wagon position
     * @return the wagon on the given index
     * @throws IndexOutOfBoundsException wagon not found
     */
    public Wagon getWagonOnPosition(int position) throws IndexOutOfBoundsException {
        Wagon currentWagon = firstWagon;
        int numberOfWagonsFound = 1;

        if(firstWagon == null) return null;

        for (int i = 1; i < position; i++) {
            if (currentWagon.hasNextWagon()) {
                currentWagon = currentWagon.getNextWagon();
                numberOfWagonsFound++;
            }
        }

        if (numberOfWagonsFound == position) {
            return currentWagon;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * give the total number of seats on a passenger train
     * for freight trains the result should be 0
     * @return int number of seats
     */
    public int getNumberOfSeats() {
        int seats = 0;

        if (firstWagon instanceof PassengerWagon) {
            for (Wagon wagon : this) {
                seats += ((PassengerWagon) wagon).getNumberOfSeats();
            }
            return seats;
        } else
            return 0;
    }

    /**
     * give the total maximum weight of a freight train
     * for passenger trains the result should be 0
     * @return int total max weight
     */
    public int getTotalMaxWeight() {
        int weight = 0;

        if (firstWagon instanceof FreightWagon) {
            for (Wagon wagon : this) {
                weight += ((FreightWagon) wagon).getMaxWeight();
            }
            return weight;
        }

        return 0;
    }

    /* three helper methods that are useful in other methods */
    public boolean hasNoWagons() {
        return (firstWagon == null);
    }

    public boolean isPassengerTrain() {
        return firstWagon instanceof PassengerWagon;
    }

    public boolean isFreightTrain() {
        return firstWagon instanceof FreightWagon;
    }

    public Locomotive getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(engine.toString());
        Wagon next = this.getFirstWagon();
        while (next != null) {
            result.append(next.toString());
            next = next.getNextWagon();
        }
        result.append(String.format(" with %d wagons and %d seats from %s to %s", numberOfWagons, getNumberOfSeats(), origin, destination));
        return result.toString();
    }

    /**
     * Iterator to go over the wagons attached to the train. It will start from the firstWagon
     * and iterate through the linked list of Wagons
     * @return Iterator<Wagon> the Iterator that can be used to iterate through the wagons
     */
    @Override
    public Iterator<Wagon> iterator() {
        return new Iterator<Wagon>() {

            Wagon current = firstWagon;

            // Will return true if the current wagon in the iteration is not null
            @Override
            public boolean hasNext() {
                return current != null;
            }

            // Sets current wagon to the next wagon and returns the current wagon
            @Override
            public Wagon next() {
                Wagon wagon = current;
                current = current.getNextWagon();
                return wagon;
            }
        };
    }

    /**
     * Overridden forEach from the Iterable interface, this method is not used
     */
    @Override
    public void forEach(Consumer action) {}

    /**
     * Overridden spliterator from the Iterable interface, this method is not used
     */
    @Override
    public Spliterator<Wagon> spliterator() {
        return null;
    }
}
