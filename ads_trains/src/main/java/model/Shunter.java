package model;

public class Shunter {

    /**
     * finds the instance of the first wagon in the train and checks if the wagon is of the same type
     * @param train a train to Compare with
     * @param wagon a wagon to check for compatibility
     * @return boolean if the instance are the same
     */
    private static boolean isSuitableWagon(Train train, Wagon wagon) {
        /* four helper methods than are used in other methods in this class to do checks */

        // trains can only exist of passenger wagons or of freight wagons
        if (train.getFirstWagon() == null) {
            return true;
        } else {
            return train.isPassengerTrain() && wagon instanceof PassengerWagon || train.isFreightTrain() && wagon instanceof FreightWagon;
        }

    }

    /**
     * Checks if both wagons are the same instance
     * @param one a wagon to Compare with
     * @param two a wagon to check for compatibility
     * @return boolean if the instance are the same
     */
    private static boolean isSuitableWagon(Wagon one, Wagon two) {
        // passenger wagons can only be hooked onto passenger wagons
        return one instanceof PassengerWagon && two instanceof PassengerWagon || one instanceof FreightWagon && two instanceof FreightWagon;
    }

    /**
     * Checks if there is space for the wagon to attach to the train based on the maxWagons of the engine
     *
     * @param train train to get the attached waggons and maxWagons
     * @param wagon wagon to get the number of wagons that are attaching to the train
     * @return boolean if there is space
     */
    private static boolean hasPlaceForWagons(Train train, Wagon wagon) {
        // the engine of a train has a maximum capacity, this method checks for a row of wagons
        int maxWagons = train.getEngine().getMaxWagons();
        int waggonsAttachedToTrain = (train.hasNoWagons()) ? 0 : train.getFirstWagon().getNumberOfWagonsAttached();
        int waggonsAttachedToWagon = wagon.getNumberOfWagonsAttached();

        return (waggonsAttachedToTrain + waggonsAttachedToWagon) <= maxWagons;
    }

    /**
     * Checks if there is space for 1 more waggon for the wagon to attach to the train based on the maxWagons of the engine
     *
     * @param train train to get the attached waggons and maxWagons
     * @param wagon wagon to get the number of wagons that are attaching to the train
     * @return boolean if there is space
     */
    private static boolean hasPlaceForOneWagon(Train train, Wagon wagon) {
        // the engine of a train has a maximum capacity, this method checks for one wagon
        int maxWagons = train.getEngine().getMaxWagons();
        int waggonsAttachedToTrain = (train.hasNoWagons()) ? 0 : train.getFirstWagon().getNumberOfWagonsAttached();
        int waggonsAttachedToWagon = wagon.getNumberOfWagonsAttached();

        return maxWagons - (waggonsAttachedToTrain + waggonsAttachedToWagon) == 1;
    }

    /**
     * Hooks the wagon to the back of the train
     *
     * @param train train to attach to
     * @param wagon wagon to attach
     * @return if the method is successful
     */
    public static boolean hookWagonOnTrainRear(Train train, Wagon wagon) {
        /* check if Locomotive can pull new number of Wagons
        check if wagon is correct kind of wagon for train
        find the last wagon of the train
        hook the wagon on the last wagon (see Wagon class)
        adjust number of Wagons of Train */

        if (hasPlaceForWagons(train, wagon) && isSuitableWagon(train, wagon)) {
            Wagon currentWagon = train.getFirstWagon();
            if (currentWagon == null) {
                train.setFirstWagon(wagon);
                wagon.setPreviousWagon(null);
            } else {
                currentWagon.getLastWagonAttached().setNextWagon(wagon);
            }
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * Hoooks the wagon to the front of the train
     *
     * @param train train to attach to
     * @param wagon wagon to attach
     * @return if the method is successful
     */
    public static boolean hookWagonOnTrainFront(Train train, Wagon wagon) {
        /* check if Locomotive can pull new number of Wagons
         check if wagon is correct kind of wagon for train
         if Train has no wagons hookOn to Locomotive
         if Train has wagons hookOn to Locomotive and hook firstWagon of Train to lastWagon attached to the wagon
         adjust number of Wagons of Train */

        if (hasPlaceForWagons(train, wagon) && isSuitableWagon(train, wagon)) {
            Wagon currentWagon = train.getFirstWagon();
            if (train.hasNoWagons()) {
                train.setFirstWagon(wagon);
            } else {
                currentWagon.getLastWagonAttached().setNextWagon(currentWagon);

                wagon.setNextWagon(currentWagon.getNextWagon());

                currentWagon.setNextWagon(null);

                train.setFirstWagon(wagon);
            }
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * Hooks a wagon to another wagon if they are of the same type
     *
     * @param first  wagon to attach to
     * @param second wagon to attach
     * @return if the method is successful
     */
    public static boolean hookWagonOnWagon(Wagon first, Wagon second) {
        /* check if wagons are of the same kind (suitable)
         * if so make second wagon next wagon of first */
        if (isSuitableWagon(first, second)) {
            if (second.hasPreviousWagon()) {
                second.getPreviousWagon().setNextWagon(null);
            }

            if (second.hasNextWagon()) {
                if (first.hasNextWagon()) {
                    Wagon lastWagon = second.getLastWagonAttached();

                    lastWagon.setNextWagon(first.getNextWagon());
                    first.getNextWagon().setPreviousWagon(lastWagon);
                    first.getLastWagonAttached().setNextWagon(null);
                }
            } else if (first.hasNextWagon()) {
                Wagon oldSecond = first.getNextWagon();
                oldSecond.setPreviousWagon(second);
                second.setNextWagon(oldSecond);
            }

            first.setNextWagon(second);
            second.setPreviousWagon(first);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Detaches all wagons from the train if the given wagon is on the train
     *
     * @param train train to empty
     * @param wagon wagon to detach
     * @return if the method is successful
     */
    public static boolean detachAllFromTrain(Train train, Wagon wagon) {
        /* check if wagon is on the train
         detach the wagon from its previousWagon with all its successor
         recalculate the number of wagons of the train */

        if (train.getPositionOfWagon(wagon.getWagonId()) != -1) {
            if (train.getPositionOfWagon(wagon.getWagonId()) == 1) {
                train.setFirstWagon(null);
            } else {
                Wagon previousWagon = wagon.getPreviousWagon();

                if (previousWagon != null) {
                    previousWagon.setNextWagon(null);
                }
            }

            train.resetNumberOfWagons();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Detaches wagon from train and attaches the rest of the wagons back to the train
     *
     * @param train train that gets its wagon removed
     * @param wagon wagon to get removed
     * @return if the method is successful
     */
    public static boolean detachOneWagon(Train train, Wagon wagon) {
        /* check if wagon is on the train
         detach the wagon from its previousWagon and hook the nextWagon to the previousWagon
         so, in fact remove the one wagon from the train
        */
        if (train.getPositionOfWagon(wagon.getWagonId()) != -1) {
            if (train.getPositionOfWagon(wagon.getWagonId()) == 1) {
                if (wagon.hasNextWagon()) {
                    train.setFirstWagon(wagon.getNextWagon());
                    wagon.getNextWagon().setPreviousWagon(null);
                } else {
                    train.setFirstWagon(null);
                }
            } else {
                Wagon previousWagon = wagon.getPreviousWagon();
                Wagon nextWagon = wagon.getNextWagon();

                if (previousWagon != null) {
                    previousWagon.setNextWagon(nextWagon);
                }

                if (nextWagon != null) {
                    nextWagon.setPreviousWagon(previousWagon);
                }
            }

            wagon.setPreviousWagon(null);
            wagon.setNextWagon(null);

            train.resetNumberOfWagons();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Move all Wagons starting with wagon from Train from to Train to if wagon is on Train to
     *
     * @param from  Train to remove wagon(s)
     * @param to
     * @param wagon
     * @return if the method is successful
     */
    public static boolean moveAllFromTrain(Train from, Train to, Wagon wagon) {
        /* check if wagon is on train from
         check if wagon is correct for train and if engine can handle new wagons
         detach Wagon and all successors from train from and hook at the rear of train to
         remember to adjust number of wagons of trains */
        if (from.getPositionOfWagon(wagon.getWagonId()) != -1 && isSuitableWagon(to, wagon) && hasPlaceForWagons(to, wagon)) {
            return detachAllFromTrain(from, wagon) && hookWagonOnTrainRear(to, wagon);
        }
        return false;
    }

    /**
     * move wagon from Train from to Train to if the wagon is on Train from
     *
     * @param from  train to remove wagon from
     * @param to    train to attach wagon to
     * @param wagon wagon to get moved
     * @return if the method is successful
     */
    public static boolean moveOneWagon(Train from, Train to, Wagon wagon) {
        // detach only one wagon from train from and hook on rear of train to
        // do necessary checks and adjustments to trains and wagon
        if (from.getPositionOfWagon(wagon.getWagonId()) != -1 && isSuitableWagon(to, wagon) && hasPlaceForWagons(to, wagon)) {
            return detachOneWagon(from, wagon) && hookWagonOnTrainRear(to, wagon);
        }
        return false;
    }
}
