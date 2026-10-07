package skiingCentre.statics;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import kadra.mapki.GeneratorMapek;
import kadra.mapki.pliki.WyjatekSystemuPlikow;
import kadra.mapki.styl.*;
import skiingCentre.athlete.Athlete;
import skiingCentre.infrastructure.*;
import skiingCentre.event.Ride;

//maps will be written in a manner: generic function not to double code
//helper responsible to check correctness of generic types and adding edge
//create map which takes map(edge,text) and creating map with given title
//string preparators which prepare maps nessesary to put to create map
//all maps managing preparation and creating

public class MapCreator {
    public static GeneratorMapek generatorMapek;

    public static void allMaps() throws WyjatekSystemuPlikow {
        HashMap<GraphEdge, List<String>> a = prepareFirstLOStrings();
        createMap(a, "FirstStats");
        a = prepareSecondLOStrings();
        createMap(a, "SecondStats");
        HashMap<GraphEdge, String> c;
        ArrayList<Athlete> all = Athlete.getAllTracked();
        for (Athlete i : all) {
            c = prepareAthlete(i);
            String title = ("Athlete(" + i.number() + ")Stats");
            createMap(c, title);
        }
    }

    private static HashMap<GraphEdge, String> prepareAthlete(Athlete athlete) {
        HashMap<GraphEdge, String> toReturn = new HashMap<>();
        ArrayList<Ride> x = athlete.rides();
        // putting info about n.o drives
        for (int i = 0; i < Route.totalCount(); i++) {
            Route route = Route.get(i);
            String a = new String();
            a = ("t" + route.number() + "(" + athlete.rideNum(route) + "): ");
            toReturn.put(route, a);
        }
        for (int i = 0; i < SkiLift.totalCount(); i++) {
            SkiLift skiLift = SkiLift.get(i);
            String a = new String();
            a = ("w" + skiLift.number() + "(" + athlete.rideNum(skiLift) + "): ");
            toReturn.put(skiLift, a);
        }
        // putting info about the history to maps
        for (int i = 0; i < x.size(); i++) {
            String a = toReturn.get(x.get(i).edge());
            a = a.concat((i + 1) + ",");
            toReturn.put(x.get(i).edge(), a);
        }
        // cuting comas
        for (GraphEdge g : toReturn.keySet()) {
            String a = toReturn.get(g);
            if (a.endsWith(","))
                a = a.substring(0, a.length() - 1);
            toReturn.put(g, a);
        }
        return toReturn;
    }

    private static HashMap<GraphEdge, List<String>> prepareSecondLOStrings() {
        HashMap<GraphEdge, List<String>> toReturn = new HashMap<>();

        for (int i = 0; i < Route.totalCount(); i++) {
            Route route = Route.get(i);
            List<String> a = new ArrayList<String>();
            a.add("t" + route.number() + ": snieg: " + String.format("%.2f", route.surfaceAttractiveness()));
            a.add("zjazdy: " + route.peopleCount());
            toReturn.put(route, a);
        }

        for (int i = 0; i < SkiLift.totalCount(); i++) {
            SkiLift skiLift = SkiLift.get(i);
            List<String> a = new ArrayList<String>();
            a.add("w" + skiLift.number() + ": kol:" + String.format("%.0f", skiLift.averageQueueLength())
                    + "(śr), " + skiLift.maxLen() + "(maks)");
            a.add("wjazdy: " + skiLift.peopleCount() + "/" + skiLift.allPossible() + " " +
                    String.format("%.0f", skiLift.percentage()) + "%");
            toReturn.put(skiLift, a);
        }

        return toReturn;
    }

    private static HashMap<GraphEdge, List<String>> prepareFirstLOStrings() {
        HashMap<GraphEdge, List<String>> toReturn = new HashMap<>();

        for (int i = 0; i < Route.totalCount(); i++) {
            Route route = Route.get(i);
            List<String> a = new ArrayList<String>();
            a.add("t" + route.number() + ": poziom: " + route.difficultyLevel()
                    + "," + "czas: " + route.travelTime() + "s");
            a.add("odpornosc: " + String.format("%.2f", route.baseAttractiveness())
                    + ", " + String.format("%.5f", route.wearResistance()));
            toReturn.put(route, a);
        }

        for (int i = 0; i < SkiLift.totalCount(); i++) {
            SkiLift skiLift = SkiLift.get(i);
            List<String> a = new ArrayList<String>();
            a.add("w" + skiLift.number() + ": " + skiLift.cabinCapacity()
                    + " os. co " + skiLift.timeInterval() + "s");
            a.add("czas: " + skiLift.travelTime() + "s ");
            toReturn.put(skiLift, a);
        }
        return toReturn;
    }

    private static <T> void createMap(HashMap<GraphEdge, T> a, String Title) throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();
        // knot preparation---------------------
        for (int i = 0; i < Knot.allCount(); i++) {
            Knot knot = Knot.get(i);
            StylWezla styl = new StylWezla(GruboscKonturu.POGRUBIONY);
            if (knot.isTransferKnot() == false)
                styl = new StylWezla(GruboscKonturu.ZWYKLY);

            generatorMapek.dodajWezel(i, knot.x(), knot.y(), styl);
        }
        // routes preparation-------------------
        for (int i = 0; i < Route.totalCount(); i++) {
            Route route = Route.get(i);
            StylKrawedzi styl = new StylKrawedzi(StylLinii.CIAGLA);
            helper(route, styl, a.get(route));

        }
        // skilifts preparation------------------------------
        for (int i = 0; i < SkiLift.totalCount(); i++) {
            SkiLift skiLift = SkiLift.get(i);
            StylKrawedzi styl = new StylKrawedzi(StylLinii.PRZERYWANA);
            helper(skiLift, styl, a.get(skiLift));
        }
        // main function- -- - -- -
        generatorMapek.tworzMapke(Title + ".tex");
    }

    // in funciton below we check correctness of types
    @SuppressWarnings("unchecked")
    private static <T> void helper(GraphEdge edge, StylKrawedzi styl, T var) throws WyjatekSystemuPlikow {
        if (var == null)
            return;
        if (var instanceof String) {
            generatorMapek.dodajKrawedz(edge.startKnot().getNum(), edge.endKnot().getNum(), styl, (String) var);
        }
        if (var instanceof List) {
            List<?> temp = (List) var; // we can cast cause we know var is a list of something
            if (temp.get(0) instanceof String) { // check if we can cast into list of strings
                generatorMapek.dodajKrawedz(edge.startKnot().getNum(), edge.endKnot().getNum(), styl,
                        (List<String>) var);
            }
        }
    }

    public static void setGeneratorMapek(GeneratorMapek g) {
        generatorMapek = g;
    }

}
