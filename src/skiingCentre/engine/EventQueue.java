package skiingCentre.engine;

import skiingCentre.event.Event;

public interface EventQueue {
    public abstract Event pollEvent();// removes event from top and returns

    public abstract void insertEvent(Event event);

    public boolean isEmpty();
}
