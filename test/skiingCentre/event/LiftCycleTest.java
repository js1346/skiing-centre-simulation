package skiingCentre.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import skiingCentre.infrastructure.*;
import skiingCentre.athlete.*;
import skiingCentre.event.*;
import skiingCentre.engine.*;

public class LiftCycleTest {
    private Knot[] knots;
    private SkiLift skiLift;
    private Athlete[] athletes;
    private LiftCycle liftCycle;
    private EventQueue eventQueue;

    @BeforeEach
    void setUp() {
        Knot.clear();
        SkiLift.clear();
        Route.clear();
        knots = new Knot[2];
        knots[1] = new Knot(1, 1, 1, false);
        knots[0] = new Knot(2, 3, 4, true);
        skiLift = new SkiLift(knots[0], knots[1], 13, 3, 5);
        athletes = new Athlete[5];
        for (int i = 0; i < 5; i++) {
            athletes[i] = new LocalAthlete(0, 0, 0, 0, 0, 0, false, knots[0]);
        }
        eventQueue = new SystemHeap();
        liftCycle = new LiftCycle(new Time("9:01:03"), skiLift, eventQueue);
    }

    @Test
    void CounterOfDrivesIncreasementAfterLiftCycle() {
        for (int i = 0; i < 4; i++) {
            skiLift.enqueue(athletes[i], new Time("9:01:02"));
        }
        liftCycle.execute();
        eventQueue.pollEvent().execute();
        eventQueue.pollEvent().execute();
        eventQueue.pollEvent().execute();
        assertEquals(3, skiLift.peopleCount());
    }

    @Test
    void EmptyQueueOfTwoAfterRide() {
        for (int i = 0; i < 2; i++) {
            skiLift.enqueue(athletes[i], new Time("9:01:02"));
        }
        liftCycle.execute();
        assertEquals(skiLift.queueLenght(), 0);
        assertTrue(eventQueue.pollEvent() instanceof RideStart);
        assertTrue(eventQueue.pollEvent() instanceof RideStart);
    }

    @Test
    void FourDriveOne() {
        for (int i = 0; i < 4; i++) {
            skiLift.enqueue(athletes[i], new Time("9:01:02"));
        }
        liftCycle.execute();
        assertTrue(skiLift.maxLen() == 4);
        skiLift.enqueue(athletes[4], new Time("9:01:04"));
        assertTrue(skiLift.maxLen() - 4 == 0);
    }
}
