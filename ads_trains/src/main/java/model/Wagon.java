package model;

public abstract class Wagon {
    private int wagonId;
    private Wagon previousWagon;
    private Wagon nextWagon;

    public Wagon (int wagonId) {
        this.wagonId = wagonId;
    }

    /**
     * Gets the last wagon in the chain of wagons
     * @return Wagon  the last wagon in the chain
     */
    public Wagon getLastWagonAttached() {
        // find the last wagon of the row of wagons attached to this wagon
        // if no wagons are attached return this wagon
        if (this.hasNextWagon()) {
            return this.nextWagon.getLastWagonAttached();
        } else {
            return this;
        }
    }


    /**
     * Connects the given wagon to this wagon and sets a reference to this wagon in the nextWagon
     * @param nextWagon Wagon to connect
     */
    public void setNextWagon(Wagon nextWagon) {
        // when setting the next wagon, set this wagon to be previous wagon of next wagon
        this.nextWagon = nextWagon;
        if (nextWagon != null) nextWagon.setPreviousWagon(this);
    }

    public Wagon getPreviousWagon() {
        return previousWagon;
    }

    public void setPreviousWagon(Wagon previousWagon) {
        this.previousWagon = previousWagon;
    }

    public Wagon getNextWagon() {
        return nextWagon;
    }

    public int getWagonId() {
        return wagonId;
    }

    /**
     * Recursive method that gets the number of wagons attached to the given wagon
     * @return int the number of wagons attached to wagon
     */
    public int getNumberOfWagonsAttached() {
        int count = 1;
        if (this.hasNextWagon()) {
            count += this.nextWagon.getNumberOfWagonsAttached();
        }
        return count;
    }

    /**
     *  Checks if there this wagon has a next wagon attached
     * @return Boolean if there is a next wagon
     */
    public boolean hasNextWagon() {
        return !(nextWagon == null);
    }

    /**
     *  Checks if there this wagon has a previous wagon attached
     * @return Boolean if there is a previous wagon
     */
    public boolean hasPreviousWagon() {
        return !(previousWagon == null);
    }

    @Override
    public String toString() {
        return String.format("[Wagon %d]", wagonId);
    }
}
