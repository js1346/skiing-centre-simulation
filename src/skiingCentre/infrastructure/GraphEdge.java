package skiingCentre.infrastructure;

import skiingCentre.athlete.Athlete;
import skiingCentre.engine.Time;
import java.util.ArrayList;

public abstract class GraphEdge {
    // protected static ArrayList<GraphEdge> all;
    protected Knot startKnot;
    protected Knot endKnot;
    protected long travelTime;
    protected int peopleCount;
    protected boolean isSkiLift;

    public GraphEdge(Knot startKnot, Knot endKnot, long travelTime) {
        this.endKnot = endKnot;
        this.startKnot = startKnot;
        this.travelTime = travelTime;
        this.peopleCount = 0;
    }

    public boolean isSkiLift() {
        return isSkiLift;
    }

    public void incrementPeopleCount() {
        peopleCount++;
    }

    public abstract void enqueue(Athlete athlete, Time time);

    public abstract int number();

    public int travelTime() {
        return (int) travelTime;
    }

    public Knot endKnot() {
        return endKnot;
    }

    public Knot startKnot() {
        return startKnot;
    }

    public int peopleCount() {
        return peopleCount;
    }

    // equals and hashcode needed in hashsets
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GraphEdge edge = (GraphEdge) o;
        return (edge.isSkiLift() == isSkiLift && edge.number() == this.number());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(isSkiLift(), number());
    }
}
