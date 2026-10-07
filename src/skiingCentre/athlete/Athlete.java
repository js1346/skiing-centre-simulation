package skiingCentre.athlete;

import skiingCentre.Simulation;
import skiingCentre.event.Ride;
import skiingCentre.engine.Time;
import skiingCentre.infrastructure.*;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Athlete {
    protected static long nextId = 0;
    protected static ArrayList<Athlete> tracked = new ArrayList<Athlete>();
    protected long id;
    protected double spontaneityFactor;
    protected double boredomFactor;
    protected int skillLevel;
    protected double boredomWeight;
    protected double surfaceWeight;
    protected double skillWeight;
    protected boolean isTracked;
    protected Time time;
    protected Knot lastKnot;
    protected ArrayList<Ride> rides;

    // clears the static history
    public static void clear() {
        tracked = new ArrayList<Athlete>();
        nextId = 0;
    }

    // gives references to all tracked athletes
    public static ArrayList<Athlete> getAllTracked() {
        return tracked;
    }

    public Athlete(double spontaneityFactor, double boredomFactor, int skillLevel, double surfaceWeight,
            double boredomWeight, double skillWeight, boolean isTracked, Knot knot) {
        this.boredomWeight = boredomWeight;
        this.spontaneityFactor = spontaneityFactor;
        this.boredomFactor = boredomFactor;
        this.skillLevel = skillLevel;
        this.surfaceWeight = surfaceWeight;
        this.skillWeight = skillWeight;
        this.isTracked = isTracked;
        this.lastKnot = knot;
        this.id = nextId;
        rides = new ArrayList<Ride>();
        if (isTracked == true)
            tracked.add(this);
        nextId++;
    }

    // returns boredom with given route
    public double boredomAtractiveness(Route route) {
        double boredom = 0;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).edge().equals(route)) {
                boredom = boredomFactor + (1 - boredomFactor) * boredom;
            } else
                boredom *= 1 - boredomFactor;
        }
        return 1 - boredom;
    }

    // makes forwarding to methods getting attractivenes for athlete
    public double attractivenessForAthlete(GraphEdge edge) {
        if (edge instanceof Route)
            return attractivenessForAthlete((Route) edge);
        else
            return attractivenessForAthlete((SkiLift) edge);
    }

    // makes formula for atractivenes for a route
    public double attractivenessForAthlete(Route route) {
        double pn = this.skillLevel();
        double pt = route.difficultyLevel();

        double difficultyAttractiveness = 0;

        if (pn + 5 > pt && pt >= pn) {
            difficultyAttractiveness = 1 - 0.2 * pt + 0.2 * pn;
        }
        if (pn > pt) {
            difficultyAttractiveness = Math.max(0.2, 1 - (pn - pt) / 7.0);
        }

        return skillWeight * difficultyAttractiveness +
                surfaceWeight * route.surfaceAttractiveness() +
                boredomWeight * boredomAtractiveness(route);
    }

    // formula for attractivenes of skilift could be pushed to Local athlete, but to
    // let for all
    // athletes analyse all GraphEdges
    public double attractivenessForAthlete(SkiLift skiLift) {
        double maxAttractiveness = 0;
        for (int i = 0; i < skiLift.endKnot().outgoingRoutesCount(); i++) {
            Route route = skiLift.endKnot().route(i);
            maxAttractiveness = Math.max(maxAttractiveness,
                    attractivenessForAthlete(route));
        }
        return maxAttractiveness;
    }

    // returns graphEdge if choice is random
    // returns null catched in method choose if choice was not random
    public GraphEdge randomChoice() {
        double randomValue = Simulation.random().nextDouble(0.0, 1.0);
        GraphEdge bestEdge = lastKnot.edge(0);

        if (randomValue < spontaneityFactor) {
            int randomEdgeIndex = Simulation.random().nextInt(0, lastKnot.outgoingCount());
            bestEdge = lastKnot.edge(randomEdgeIndex);
            return bestEdge;
        } else
            return null;
    }

    // every lower class implements method choose given to decision
    public abstract GraphEdge choose();

    public void setLastKnot(Knot knot) {
        this.lastKnot = knot;
    }

    public void addRide(Ride ride) {
        rides.add(ride);
    }

    // returns how many rides had athlite on graphEdge
    public int rideNum(GraphEdge edge) {
        int toReturn = 0;
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).edge().equals(edge))
                toReturn++;
        }
        return toReturn;
    }

    public double spontaneityFactor() {
        return spontaneityFactor;
    }

    public double surfaceWeight() {
        return surfaceWeight;
    }

    public double skillWeight() {
        return skillWeight;
    }

    public boolean isTracked() {
        return isTracked;
    }

    public int skillLevel() {
        return skillLevel;
    }

    public int number() {
        return (int) id;
    }

    public ArrayList<Ride> rides() {
        return rides;
    }
}
