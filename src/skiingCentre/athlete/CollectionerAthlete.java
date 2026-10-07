package skiingCentre.athlete;

import skiingCentre.infrastructure.*;
import skiingCentre.Simulation;
import java.util.List;
import java.util.HashSet;

public class CollectionerAthlete extends GoalAthlete {
    public CollectionerAthlete(double spontaneityFactor, double boredomFactor, int skillLevel, double surfaceWeight,
            double boredomWeight, double skillWeight, boolean isTracked, Knot knot) {
        super(spontaneityFactor, boredomFactor,
                skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
    }

    // collectioner wants to find nearest then best route he had least drives on
    public Route getNewGoal() {
        int min = Integer.MAX_VALUE;
        HashSet<Route> bests = new HashSet<>();

        for (int i = 0; i < Route.totalCount(); i++) {
            Route x = Route.get(i);
            int temp = rideNum(x);
            if (min == temp) {
                bests.add(x);
            }
            if (min > temp) {
                bests = new HashSet<Route>(List.of(x));
                min = temp;
            }
        }

        return Simulation.BFS().makeShortestBestFrom(lastKnot, bests, this);
    }
}
