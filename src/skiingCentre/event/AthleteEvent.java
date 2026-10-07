package skiingCentre.event;

import skiingCentre.athlete.Athlete;
import skiingCentre.engine.*;

public abstract class AthleteEvent extends Event {
    protected Athlete athlete;

    public AthleteEvent(Time time, Athlete athlete, EventQueue eventQueue) {
        super(time, eventQueue);
        this.athlete = athlete;
    }

    // atheleEvent needs to store only info about athlete
    public Athlete athlete() {
        return athlete;
    }
}
