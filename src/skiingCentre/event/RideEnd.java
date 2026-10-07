package skiingCentre.event;

import skiingCentre.infrastructure.GraphEdge;
import skiingCentre.statics.Writer;
import skiingCentre.athlete.Athlete;
import skiingCentre.engine.*;

public class RideEnd extends AthleteEvent {
    private GraphEdge edge;

    public RideEnd(Time time, Athlete athlete, GraphEdge edge, EventQueue eventQueue) {
        super(time, athlete, eventQueue);
        this.edge = edge;
    }

    @Override
    public void execute() {
        if (time.isBefore15()) {
            athlete.setLastKnot(edge.endKnot());
            eventQueue.insertEvent(new Decision(time, athlete, edge.endKnot(), eventQueue));
        }
        // we don't need to check beforeness of 15.00, because decision doesn't have
        // decisive power,
        // only desision need's to check beforeness of 15;00 of its folowers, because
        // they have decisive powers
        if (athlete.isTracked()) {
            Writer.printRide(time, athlete, edge, false);
        }
    }

}
