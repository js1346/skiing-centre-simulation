package skiingCentre.athlete;

import skiingCentre.infrastructure.*;
import skiingCentre.Simulation;
import java.util.ArrayList;

public abstract class GoalAthlete extends Athlete {
    protected ArrayList<GraphEdge> listToGoal; // list to goal with a goal in it
    // it will go [goal, togoal, totogoal ...]
    // it's emptiness indicates new goal to set

    public GoalAthlete(double spontaneityFactor, double boredomFactor, int skillLevel, double surfaceWeight,
            double boredomWeight, double skillWeight, boolean isTracked, Knot knot) {
        super(spontaneityFactor, boredomFactor,
                skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
        this.listToGoal = new ArrayList<GraphEdge>();
    }

    // he follows list if possible, if not, makes if choice is random
    // if not makes new goal and new list
    public GraphEdge choose() {
        if (listToGoal.isEmpty()) {
            GraphEdge x = randomChoice();
            if (x != null)
                return x;
            Route fav = getNewGoal();
            listToGoal = Simulation.BFS().make(lastKnot, fav);
        }
        GraphEdge toReturn = listToGoal.getLast();
        listToGoal.removeLast();
        return toReturn;
    }

    public abstract Route getNewGoal();
}
