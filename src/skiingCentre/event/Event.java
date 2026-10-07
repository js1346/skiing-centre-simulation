package skiingCentre.event;

import skiingCentre.engine.*;

public abstract class Event implements Comparable<Event> {
    protected Time time;
    protected static int nextId = 0;
    protected int id;
    protected EventQueue eventQueue;

    public Event(Time time, EventQueue eventQueue) {
        this.eventQueue = eventQueue;
        this.time = time;
        this.id = nextId;
        nextId++;
    }

    public Time time() {
        return time;
    }

    public int id() {
        return id;
    }

    // event needs to be comparable, therefore we compare for times and id
    public int compareTo(Event other) {
        if (this.time.moment() < other.time.moment()) {
            return -1;
        }
        if (this.time.moment() > other.time.moment()) {
            return 1;
        }
        return Integer.compare(this.id, other.id);
    }

    // we keep this function to backward compability
    public boolean isBefore(Event other) {
        return this.compareTo(other) < 0;
    }

    public abstract void execute();
}
