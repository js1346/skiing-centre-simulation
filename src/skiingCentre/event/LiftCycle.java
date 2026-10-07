package skiingCentre.event;

import skiingCentre.athlete.Athlete;
import skiingCentre.engine.*;
import skiingCentre.infrastructure.SkiLift;

public class LiftCycle extends Event {
    private SkiLift skiLift;

    public LiftCycle(Time time, SkiLift skiLift, EventQueue eventQueue) {
        super(time, eventQueue);
        this.skiLift = skiLift;
    }

    @Override
    public void execute() {
        int remainingSeats = skiLift.cabinCapacity();
        Time newTime = new Time(time, skiLift.timeInterval());

        if (newTime.isBefore15()) {// it puts another liftcycle
            eventQueue.insertEvent(new LiftCycle(newTime, skiLift, eventQueue));
        }
        // here we start rides of athletes to while queue is empty or cabin is full
        while (!skiLift.isQueueEmpty() && remainingSeats != 0) {
            Athlete athlete = skiLift.pollAthlete(time);
            eventQueue.insertEvent(new RideStart(time, athlete, skiLift, eventQueue));
            // we need to

            remainingSeats--;
            // we write nothnig here
        }
        // we increment numbers of drives of skilift by one
        skiLift.incrementDrives();
    }
}
