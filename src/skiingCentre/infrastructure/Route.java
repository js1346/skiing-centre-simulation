package skiingCentre.infrastructure;

import java.lang.Math;

import skiingCentre.athlete.Athlete;
import skiingCentre.engine.Time;

public class Route extends GraphEdge {
    private static int routeCount;
    private static int capacity;
    private static Route[] routes;

    private int difficultyLevel;
    private double baseAttractiveness;
    private double wearResistance;
    private int id;

    // --------------statics-------------------------------------
    static {
        routes = new Route[10];
        capacity = 10;
        routeCount = 0;
    }

    public static void push(Route route) {
        if (routeCount >= capacity) {
            Route[] temp = new Route[2 * capacity];
            for (int i = 0; i < capacity; i++) {
                temp[i] = routes[i];
            }
            routes = temp;
            capacity *= 2;
        }
        routes[routeCount] = route;
        routeCount++;
    }

    public static int totalCount() {
        return routeCount;
    }

    public static int peopleCountOnRoute(int i) {
        return routes[i].peopleCount;
    }

    public static Route get(int i) {
        return routes[i];
    }

    public static void clear() {
        routeCount = 0;
    }
    // ------------standards-------------------

    public Route(int difficultyLevel, double baseAttractiveness, double wearResistance,
            Knot startKnot, Knot endKnot, long travelTime) {

        super(startKnot, endKnot, travelTime);
        super.isSkiLift = false;
        this.difficultyLevel = difficultyLevel;
        this.baseAttractiveness = baseAttractiveness;
        this.wearResistance = wearResistance;
        this.id = routeCount;

        Route.push(this);
        this.startKnot.addEdge(this);
    }

    public void enqueue(Athlete athlete, Time time) {
        throw new IllegalStateException("You cannot enqueue on route");
    }

    @Override
    public int number() {
        return this.id;
    }

    public double baseAttractiveness() {
        return this.baseAttractiveness;
    }

    public double surfaceLeveling() {
        return Math.pow(wearResistance, peopleCount);
    }

    public double surfaceAttractiveness() {
        return baseAttractiveness +
                (1 - baseAttractiveness) * Math.pow(wearResistance, peopleCount);
    }

    public double wearResistance() {
        return this.wearResistance;
    }

    public int difficultyLevel() {
        return this.difficultyLevel;
    }
}
