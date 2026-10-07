package skiingCentre.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import skiingCentre.infrastructure.*;
import skiingCentre.athlete.*;
import skiingCentre.event.*;
import skiingCentre.engine.*;

public class BFSTest {
    private Knot[] knots;
    private GraphEdge[] edges;
    private BFS BFS;

    @BeforeEach
    void setUp() {
        Knot.clear();
        SkiLift.clear();
        Route.clear();
        knots = new Knot[6];
        for (int i = 0; i < 6; i++) {
            knots[i] = new Knot(1, 1, 1, false);
        }
        BFS = new BFS();
        edges = new GraphEdge[11];
        edges[0] = new Route(0, 0, 0, knots[2], knots[0], 1);
        knots[2].addEdge(edges[0]);
        edges[1] = new SkiLift(knots[0], knots[1], 10, 0, 0);
        knots[0].addEdge(edges[1]);
        edges[2] = new Route(0, 0, 0, knots[1], knots[0], 1);
        knots[1].addEdge(edges[2]);
        edges[3] = new Route(0, 0, 0, knots[1], knots[2], 1);
        knots[1].addEdge(edges[3]);
        edges[4] = new SkiLift(knots[2], knots[4], 10, 0, 0);
        knots[2].addEdge(edges[4]);
        edges[5] = new SkiLift(knots[2], knots[3], 10, 0, 0);
        knots[2].addEdge(edges[5]);
        edges[6] = new Route(0, 0, 0, knots[3], knots[1], 1);
        knots[3].addEdge(edges[6]);
        edges[7] = new Route(0, 0, 0, knots[3], knots[4], 1);
        knots[3].addEdge(edges[7]);
        edges[8] = new SkiLift(knots[4], knots[5], 10, 0, 0);
        knots[4].addEdge(edges[8]);
        edges[9] = new Route(0, 0, 0, knots[5], knots[3], 1);
        knots[5].addEdge(edges[9]);
        edges[10] = new Route(0, 0, 0, knots[5], knots[3], 1);
        knots[5].addEdge(edges[10]);
    }

    @Test
    void ZeroToFour() {
        ArrayList<GraphEdge> a = BFS.make(knots[0], edges[4]);
        assertEquals(a.size(), 3);
        assertEquals(a.get(2), edges[1]);
        assertEquals(a.get(1), edges[3]);
        assertEquals(a.get(0), edges[4]);
    }

    @Test
    void ThreeToOne() {
        ArrayList<GraphEdge> a = BFS.make(knots[3], edges[6]);
        assertEquals(a.size(), 1);
        assertEquals(a.get(0), edges[6]);
    }

    // test of empty road cannot be done due to the program's logic
    // test fourth needs to get the special road

    @Test
    void FourToTwo() {
        ArrayList<GraphEdge> a = BFS.make(knots[4], edges[10]);
        assertEquals(a.size(), 2);
    }

    @Test
    void FourToOne() {
        ArrayList<GraphEdge> a = BFS.make(knots[4], edges[6]);
        assertEquals(a.size(), 3);
        assertTrue(a.get(1).equals(edges[10]) || a.get(1).equals(edges[9]));
    }

}
