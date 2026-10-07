package skiingCentre.engine;

import skiingCentre.event.Event;
import java.util.PriorityQueue;

public class SystemHeap implements EventQueue {
    private PriorityQueue<Event> queue;

    // event is comparable, therefore nothing to comment
    public SystemHeap() {
        this.queue = new PriorityQueue<Event>();
    }

    public Event pollEvent() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot poll from an empty queue.");
        }

        return queue.poll();
    }

    public void insertEvent(Event event) {
        queue.add(event);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
