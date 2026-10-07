package skiingCentre.event;

import skiingCentre.infrastructure.GraphEdge;
import skiingCentre.statics.Writer;
import skiingCentre.athlete.Athlete;
import skiingCentre.engine.*;

public class RideStart extends AthleteEvent {
    private GraphEdge edge;

    public RideStart(Time time, Athlete athlete, GraphEdge edge, EventQueue eventQueue) {
        super(time, athlete, eventQueue);
        this.edge = edge;
    }

    @Override
    public void execute() {
        // we need to put rideend, add ride to athlete's story
        Time newTime = new Time(time, edge.travelTime());
        edge.incrementPeopleCount();
        RideEnd rideEnd = new RideEnd(newTime, athlete, edge, eventQueue);
        athlete.addRide(new Ride(time, newTime, edge));
        eventQueue.insertEvent(rideEnd); // rideStart always need to end with ride end
        if (athlete.isTracked()) {
            Writer.printRide(time, athlete, edge, true);
        }
    }
}
