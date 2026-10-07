package skiingCentre.engine;

import skiingCentre.event.Event;

//old heap stayes because content's of the task lets it to stay!
//we will be trying to use systemHeap
public class Heap implements EventQueue {
    private Event[] events;
    private int capacity;
    private int size;

    public Heap() {
        events = new Event[10];
        capacity = 10;
        size = 0;
    }

    private void push(Event event) {
        if (size >= capacity) {
            Event[] temp = new Event[2 * capacity];
            for (int i = 0; i < capacity; i++) {
                temp[i] = events[i];
            }
            events = temp;
            capacity *= 2;
        }
        if (size < capacity) {
            events[size] = event;
            size++;
        }
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) < size;
    }

    private boolean hasParent(int index) {
        return index > 0;
    }

    private void swap(int indexOne, int indexTwo) {
        Event temp = events[indexOne];
        events[indexOne] = events[indexTwo];
        events[indexTwo] = temp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Event pollEvent() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot poll from an empty queue.");
        }

        Event minimum = events[0];
        events[0] = events[size - 1];
        size--;

        heapifyDown();
        return minimum;
    }

    @Override
    public void insertEvent(Event event) {
        this.push(event);
        heapifyUp();
    }

    public void heapifyUp() {
        int currentIndex = size - 1;
        while (hasParent(currentIndex) && events[currentIndex].isBefore(events[parentIndex(currentIndex)])) {
            swap(parentIndex(currentIndex), currentIndex);
            currentIndex = parentIndex(currentIndex);
        }
    }

    public void heapifyDown() {
        int currentIndex = 0;

        while (hasLeftChild(currentIndex)) {
            int smallerChildIndex = leftChildIndex(currentIndex);

            if (hasRightChild(currentIndex)
                    && events[rightChildIndex(currentIndex)].isBefore(events[leftChildIndex(currentIndex)])) {
                smallerChildIndex = rightChildIndex(currentIndex);
            }

            if (events[currentIndex].isBefore(events[smallerChildIndex])) {
                break;
            } else {
                swap(currentIndex, smallerChildIndex);
            }

            currentIndex = smallerChildIndex;
        }
    }
}
