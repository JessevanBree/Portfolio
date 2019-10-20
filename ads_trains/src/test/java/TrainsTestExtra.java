import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainsTestExtra {
    private Locomotive engine;
    private Train train1;
    private Train train2;
    private PassengerWagon w1;
    private PassengerWagon w2;
    private PassengerWagon w3;
    private PassengerWagon w4;
    private FreightWagon w5;
    private FreightWagon w6;
    private FreightWagon w7;

    @BeforeEach
    void setUp() {
        engine = new Locomotive(1, 4);

        train1 = new Train(engine, "Alkmaar", "Maastricht");
        train2 = new Train(engine, "Amsterdam", "Berlin Hauptbahnhof");

        w1 = new PassengerWagon(2, 250);
        w2 = new PassengerWagon(21, 150);
        w3 = new PassengerWagon(23, 120);
        w4 = new PassengerWagon(24, 70);

        w5 = new FreightWagon(2, 150);
        w6 = new FreightWagon(2, 150);
        w7 = new FreightWagon(2, 120);

        Shunter.hookWagonOnTrainRear(train1, w1);
        Shunter.hookWagonOnTrainRear(train1, w2);
        Shunter.hookWagonOnTrainRear(train1, w3);

        Shunter.hookWagonOnTrainRear(train2, w5);
        Shunter.hookWagonOnTrainRear(train2, w6);
    }

    @AfterEach
    void tearDown() {
        engine = null;
        train1 = null;
        train2 = null;
        w1 = null;
        w2 = null;
        w3 = null;
        w4 = null;
        w5 = null;
        w6 = null;
        w7 = null;
    }

    @Test
    void getTotalMaxWeight() {
        int totalWeight = 300;
        int totalWeightPassengers = 0;
        assertEquals(totalWeight, train2.getTotalMaxWeight());
        assertEquals(totalWeightPassengers, train1.getTotalMaxWeight());
    }

    @Test
    void getDestination() {
        String destination = "Maastricht";
        assertEquals(destination, train1.getDestination());
    }

    @Test
    void setDestination() {
        String destination = "Hoorn";
        train2.setDestination(destination);
        assertEquals(destination, train2.getDestination());
    }

    @Test
    void getOrigin() {
        String origin = "Alkmaar";
        assertEquals(origin, train1.getOrigin());
    }

    @Test
    void setOrigin() {
        String origin = "Rotterdam";
        train2.setOrigin(origin);
        assertEquals(origin, train2.getOrigin());
    }

    @Test
    void getNumberOfWagonsAttachedOverloaded() {
        int overloadedParam = w1.getNumberOfWagonsAttached();
        int overloadedNoParam = w1.getNumberOfWagonsAttached();
        assertEquals(overloadedNoParam, overloadedParam);
    }

    @Test
    void getLastWagonAttachedOverloaded() {
        Wagon overloadedParam = w1.getLastWagonAttached();
        Wagon overloadedNoParam = w1.getLastWagonAttached();
        assertEquals(w3, overloadedNoParam);
        assertEquals(w3, overloadedParam);
    }
}