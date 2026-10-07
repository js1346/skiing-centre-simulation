package skiingCentre.athlete;

import skiingCentre.Simulation;
import skiingCentre.engine.Time;
import skiingCentre.infrastructure.*;

public class GreedyAthlete extends GoalAthlete {

    public GreedyAthlete(double spontaneityFactor, double boredomFactor, int skillLevel, double surfaceWeight,
            double boredomWeight, double skillWeight, boolean isTracked, Knot knot) {
        super(spontaneityFactor, boredomFactor,
                skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
    }

    // takes best route in whole skiing centre and returns it
    public Route getNewGoal() {
        Route x = Route.get(0);
        double max = attractivenessForAthlete(x);
        for (int i = 1; i < Route.totalCount(); i++) {
            Route y = Route.get(i);
            double res = attractivenessForAthlete(y);
            if (res > max) {
                x = y;
                max = res;
            }
        }
        return x;
    }
}
