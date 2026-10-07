package skiingCentre.infrastructure;

import java.util.ArrayList;

public class Knot {
    private static Knot[] list;
    private static int knotIndex;
    private static int listCapacity;

    private int elevation;
    private int x;
    private int y;
    private boolean isTransferKnot;

    private ArrayList<GraphEdge> outgoingEdges;
    private ArrayList<Route> outgoingRoutes;
    private ArrayList<SkiLift> outgoingSkiLifts;
    private int index;

    static {
        Knot.list = new Knot[10];
        Knot.knotIndex = 0;
        Knot.listCapacity = 10;
    }

    private static void addKnot(Knot knot) {
        if (knotIndex >= listCapacity) {
            Knot[] temp = new Knot[2 * listCapacity];
            for (int i = 0; i < knotIndex; i++) {
                temp[i] = Knot.list[i];
            }
            listCapacity *= 2; // Naprawione: aktualizacja limitu tablicy statycznej
            Knot.list = temp;
        }

        if (knotIndex < listCapacity) {
            Knot.list[knotIndex] = knot;
            knot.index = knotIndex;
            knotIndex++;
        }
    }

    public static Knot get(int i) {
        return Knot.list[i];
    }

    public int getNum() {
        return index;
    }

    public static int allCount() {
        return Knot.knotIndex;
    }

    public static void clear() {
        knotIndex = 0;
    }

    public Knot(int elevation, int x, int y, boolean isTransferKnot) {
        this.elevation = elevation;
        this.x = x;
        this.y = y;
        this.isTransferKnot = isTransferKnot;
        this.outgoingEdges = new ArrayList<GraphEdge>();
        this.outgoingRoutes = new ArrayList<Route>();
        this.outgoingSkiLifts = new ArrayList<SkiLift>();
        Knot.addKnot(this);
    }

    public GraphEdge giveEdgeOfAnd(Knot end) {
        for (GraphEdge i : outgoingEdges) {
            if (i.endKnot().equals(end))
                return i;
        }
        return outgoingEdges.get(0);
    }

    public void addEdge(GraphEdge edge) {
        if (edge.isSkiLift() == true) {
            outgoingEdges.add(edge);
            outgoingSkiLifts.add((SkiLift) edge); // we can use downcasting
        } // cause we are sure
        else { // that the types are right
            outgoingEdges.add(edge); // and ladder checks is short
            outgoingRoutes.add((Route) edge);
        }
    }

    public boolean isTransferKnot() {
        return this.isTransferKnot;
    }

    public int outgoingCount() {
        return this.outgoingEdges.size();
    }

    public int outgoingRoutesCount() {
        return this.outgoingRoutes.size();
    }

    public int outgoingSkiLiftsCount() {
        return this.outgoingSkiLifts.size();
    }

    public GraphEdge edge(int i) {
        return outgoingEdges.get(i);
    }

    public Route route(int i) {
        return outgoingRoutes.get(i);
    }

    public SkiLift skilift(int i) {
        return outgoingSkiLifts.get(i);
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int elevation() {
        return this.elevation;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Knot knot = (Knot) o;
        return index == knot.index;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(index);
    }
}
