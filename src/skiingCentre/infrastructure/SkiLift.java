package skiingCentre.infrastructure;

import skiingCentre.athlete.Athlete;
import skiingCentre.engine.Time;
import java.util.ArrayList;

public class SkiLift extends GraphEdge {
    private static int liftCount = 0;
    private static int capacity = 10;
    private static SkiLift[] skiLifts;

    private long cabinCapacity;
    private int timeInterval;
    private int id;
    private AthleteQueue athleteQueue;

    // -----------start of statics
    static {
        skiLifts = new SkiLift[10];
        capacity = 10;
        liftCount = 0;
    }

    public static void push(SkiLift skiLift) {
        if (liftCount >= capacity) {
            SkiLift[] temp = new SkiLift[2 * capacity];
            for (int i = 0; i < capacity; i++)
                temp[i] = skiLifts[i];
            skiLifts = temp;
            capacity *= 2;
        }
        skiLifts[liftCount] = skiLift;
        liftCount++;
    }

    public static int totalCount() {
        return liftCount;
    }

    public static int peopleCountOnSkiLift(int i) {
        return skiLifts[i].peopleCount;
    }

    public static SkiLift get(int i) {
        return skiLifts[i];
    }

    public static void clear() {
        liftCount = 0;
    }
    // ---------end of statics----------------------------

    public SkiLift(Knot startKnot, Knot endKnot, long travelTime,
            long cabinCapacity, int timeInterval) {
        super(startKnot, endKnot, travelTime);
        this.cabinCapacity = cabinCapacity;
        this.timeInterval = timeInterval;
        this.id = liftCount;
        this.athleteQueue = new AthleteQueue();
        super.isSkiLift = true;
        SkiLift.push(this);
        this.startKnot.addEdge(this);
    }

    @Override
    public int number() {
        return this.id;
    }

    public int cabinCapacity() {
        return (int) cabinCapacity;
    }

    public int timeInterval() {
        return timeInterval;
    }

    // this class realises linked list with methods to get and put athletes
    private class AthleteQueue {
        private QueueNode head;
        private QueueNode tail;
        private int counter;// of people staying
        private int max;// of maximal time of staying
        private Time lastActualisation;
        private int drives; // number of drives(actualised by LiftCycle)
        private double averageQueue;

        public AthleteQueue() {
            this.head = null;
            this.tail = null;
            this.counter = 0;
            this.max = 0;
            this.lastActualisation = new Time("9:00:00");
            this.drives = 0;
            this.averageQueue = 0;
        }

        public void enqueue(Athlete athlete, Time time) {
            if (tail == null) {
                QueueNode temp = new QueueNode(null, null, athlete, time);
                head = temp;
                tail = temp;
            } else {
                QueueNode temp = new QueueNode(null, tail, athlete, time);
                tail.next = temp;
                tail = temp;
            }
            recountAverage(time);
            counter++;
            if (counter > max)
                max = counter;
        }

        public Athlete poll(Time end) {
            if (head == null)
                return null;
            Athlete toReturn = head.athlete;
            if (head.next == null) {
                head = null;
                tail = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }

            recountAverage(end);
            counter--;
            return toReturn;
        }

        public void recountAverage(Time time) {
            if (time.diffNine() == 0)
                averageQueue = 0;
            else // Navg=Pavg*Ptime/allTIme+IbAvg*Ibtime/allTime
                averageQueue = (double) lastActualisation.diffNine() * averageQueue / (double) time.diffNine()
                        + (double) counter * (double) time.differ(lastActualisation) / (double) time.diffNine();
            lastActualisation = time;
        }

        private class QueueNode {
            private QueueNode next;
            private QueueNode prev;
            private Athlete athlete;
            private Time start;

            QueueNode(QueueNode next, QueueNode prev, Athlete athlete, Time time) {
                this.next = next;
                this.prev = prev;
                this.athlete = athlete;
                this.start = time;
            }
        }
    }

    // getters for athleteQueue-----------------------------
    public double averageQueueLength() {
        return athleteQueue.averageQueue;
    }

    public int queueLenght() {
        return athleteQueue.counter;
    }

    public Athlete pollAthlete(Time time) {
        return athleteQueue.poll(time);
    }

    public void incrementDrives() {
        athleteQueue.drives++;
    }

    @Override
    public void enqueue(Athlete athlete, Time time) {
        athleteQueue.enqueue(athlete, time);
    }

    public boolean isQueueEmpty() {
        return athleteQueue.counter == 0;
    }

    public int maxLen() {
        return athleteQueue.max;
    }

    // another methods in contact with queue--------------------------------------
    // total count in another metod
    public int allPossible() {
        return athleteQueue.drives * (int) cabinCapacity;
    }

    public double percentage() {
        if (athleteQueue.drives == 0)
            return 100;
        return (double) this.peopleCount() / (double) (athleteQueue.drives * cabinCapacity) * 100;
    }
}
