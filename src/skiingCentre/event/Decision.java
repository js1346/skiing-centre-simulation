package skiingCentre.event;

import skiingCentre.Simulation;
import skiingCentre.athlete.Athlete;
import skiingCentre.engine.*;
import skiingCentre.infrastructure.*;
import skiingCentre.statics.Writer;

public class Decision extends AthleteEvent {
    private Knot knot;

    public Decision(Time time, Athlete athlete, Knot knot, EventQueue eventQueue) {
        super(time, athlete, eventQueue);
        this.knot = knot;
    }

    // execution mean chosing edge
    @Override
    public void execute() {
        GraphEdge bestEdge = athlete.choose();
        // if route put ridestart to eventqueue
        if (!bestEdge.isSkiLift()) {
            RideStart rideStart = new RideStart(time, athlete, bestEdge, eventQueue);
            if (time.isBefore15()) {
                eventQueue.insertEvent(rideStart);
            }
        } else {// if skilift enqueue athlete and write info about starting staying in the queue
            if (time.isBefore15()) {
                bestEdge.enqueue(athlete, time);
                if (athlete.isTracked()) {
                    Writer.printQueueEvent(time, athlete, bestEdge);
                }
            }
        }
    }
}
