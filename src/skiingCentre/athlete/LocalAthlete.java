package skiingCentre.athlete;

import skiingCentre.Simulation;
import skiingCentre.infrastructure.*;

public class LocalAthlete extends Athlete {

    public LocalAthlete(double spontaneityFactor, double boredomFactor, int skillLevel, double surfaceWeight,
            double boredomWeight, double skillWeight, boolean isTracked, Knot knot) {
        super(spontaneityFactor, boredomFactor, skillLevel, surfaceWeight,
                boredomWeight, skillWeight, isTracked, knot);
    }

    // chooses edge as in logic in first version of programme
    public GraphEdge choose() {
        GraphEdge x = randomChoice();
        if (x != null) {
            return x;
        } else {
            double bestAttractiveness = attractivenessForAthlete(lastKnot.edge(0));
            GraphEdge bestEdge = lastKnot.edge(0);

            for (int i = 1; i < lastKnot.outgoingCount(); i++) {
                double tempAttractiveness = attractivenessForAthlete(lastKnot.edge(i));
                if (tempAttractiveness > bestAttractiveness) {
                    bestEdge = lastKnot.edge(i);
                    bestAttractiveness = tempAttractiveness;
                }
            }
            return bestEdge;
        }

    }
}
