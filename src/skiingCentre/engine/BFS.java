package skiingCentre.engine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import skiingCentre.infrastructure.*;
import skiingCentre.athlete.Athlete;

public class BFS {

    public BFS() {
    }

    // BFS returns List of edges where position go from favourite edge (0)
    // to edge from startknot(last)
    public ArrayList<GraphEdge> make(Knot begin, GraphEdge endEdge) {
        // in hashset there are Knots visited
        HashSet<Knot> vis = new HashSet<Knot>(Set.of(begin));
        LinkedList<Knot> q = new LinkedList<Knot>(List.of(begin));
        // we know, that on first position will be favourite edge, we can put it
        ArrayList<GraphEdge> toReturn = new ArrayList<GraphEdge>(List.of(endEdge));
        // we will do BFS from knot were staying to knot, from which favourite edge
        // starts
        Knot end = endEdge.startKnot();
        Knot another = endEdge.startKnot();// overriden
        // table to restore path to knot
        int[] prevs = new int[Knot.allCount()];
        boolean isFinished = false;

        while (!q.isEmpty() && isFinished == false) {
            Knot x = q.removeFirst();
            for (int i = 0; i < x.outgoingCount(); i++) {
                // we iterate along edges around x
                // and their ending knots
                another = x.edge(i).endKnot();
                if (another.equals(end)) {
                    // if we found goal we exit
                    isFinished = true;
                    prevs[another.getNum()] = x.getNum();
                    break;
                }
                if (vis.add(another) == true) {
                    // if knot was not visited, we ad it to queue
                    q.addLast(another);
                    prevs[another.getNum()] = x.getNum();
                }
            }
        }
        // we know that we found end and it stays in another
        // we put GraphEdges for while we don't have that knot we ended
        // is our goal
        int finding = prevs[another.getNum()];
        while (!another.equals(begin)) {
            end = another;
            another = Knot.get(finding);
            toReturn.add(another.giveEdgeOfAnd(end));
            finding = prevs[another.getNum()];
        }
        // we return array, it's never empty cause we only added
        return toReturn;
    }

    // takes hashset of roues and makes bfs to find graphedge nearest to the knot
    // and if it finds it runs
    public Route makeShortestBestFrom(Knot begin, HashSet<Route> routes, Athlete a) {
        boolean isFinished = false;
        LinkedList<Knot> q = new LinkedList<Knot>(List.of(begin));
        Route toReturn = null;
        // it stores the info about was knot visited(contains) and how far it was with
        // get
        HashMap<Knot, Integer> distance = new HashMap<Knot, Integer>(Map.of(begin, 0));

        while (!q.isEmpty() && isFinished == false) {
            Knot x = q.removeFirst();
            for (int i = 0; i < x.outgoingCount(); i++) {
                GraphEdge another = x.edge(i);
                Knot endKnot = another.endKnot();
                // we put not contained key with increased distance
                if (!distance.containsKey(endKnot)) {
                    distance.put(endKnot, (Integer) ((int) distance.get(x) + 1));
                    q.add(endKnot);
                } // we check edge if it is in hashset
                if (routes.contains(another)) {// we can do this casting, couse we,re sure its route, if its in routes
                    if (toReturn == null) // if its first we put it in
                        toReturn = (Route) another;
                    else {// if it is next we check the distances, if it's further, we cannot get nearer
                          // due to bfs
                        if (distance.get(toReturn.startKnot()) < distance.get(another.startKnot())) {
                            isFinished = true;
                            break;
                        } // if(it is better, by distance, we can take coming route)
                        else if (distance.get(toReturn.startKnot()) > distance.get(another.startKnot()))
                            toReturn = (Route) another;
                        // coming route has equal distance, we check its characterness
                        else if (a.attractivenessForAthlete(toReturn) < a.attractivenessForAthlete(another)) {
                            toReturn = (Route) another;
                        }
                    }
                }
            }
        }
        // due to not emptiness of hashset and graph connectivity we never return null
        return toReturn;
    }
}
