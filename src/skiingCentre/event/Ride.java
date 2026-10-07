package skiingCentre.event;

import skiingCentre.engine.Time;
import skiingCentre.infrastructure.GraphEdge;

public class Ride {
    // it doesn't do anything useful other than storing information about rides
    // to make it easier for other classes to write info to map
    private Time begin;
    private Time end;
    private GraphEdge edge;

    Ride(Time begin, Time end, GraphEdge edge) {
        this.begin = begin;
        this.end = end;
        this.edge = edge;
    }

    public Time begin() {
        return this.begin;
    }

    public Time end() {
        return this.end;
    }

    public GraphEdge edge() {
        return this.edge;
    }

}
