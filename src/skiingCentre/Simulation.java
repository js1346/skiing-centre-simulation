package skiingCentre;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import kadra.mapki.GeneratorMapek;

import kadra.mapki.pliki.WyjatekSystemuPlikow;
import skiingCentre.athlete.*;
import skiingCentre.engine.*;
import skiingCentre.infrastructure.*;
import skiingCentre.statics.MapCreator;
import skiingCentre.statics.Parser;
import skiingCentre.statics.Writer;
import skiingCentre.event.*;

public class Simulation {
    private static Random random;
    private static EventQueue eventQueue;
    private static BFS BFS;
    private static GeneratorMapek generatorMapek;

    static {
        random = new Random();
        eventQueue = new SystemHeap();
        BFS = new BFS();
    }

    public static EventQueue eventQueue() {
        return eventQueue;
    }

    public static Random random() {
        return random;
    }

    public static BFS BFS() {
        return BFS;
    }

    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("Brak ścieżki");
        else
            try {
                generatorMapek = new GeneratorMapek(args[0]);
                MapCreator.setGeneratorMapek(generatorMapek);

                Parser.parseInput();
                processQueue();
                Writer.printLastStatistics();
                MapCreator.allMaps();

            } catch (WyjatekSystemuPlikow e) {
                System.err.println(
                        "Błąd zapisu/odczytu. Upewnij się, że podałeś poprawną ścieżkę i masz uprawnienia do tworzenia katalogów.");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Wystąpił nieoczekiwany błąd. Prosimy zgłosić problem zespołowi deweloperskiemu.");
                e.printStackTrace();
            }

    }

    public static void processQueue() {
        Event event;
        while (!eventQueue.isEmpty()) {
            event = eventQueue.pollEvent();
            event.execute();
        }
    }

    /*
     * public static void printStatistics() {
     * System.out.println("Ride statistics:");
     * for (int i = 0; i < SkiLift.totalCount(); i++) {
     * System.out.println("Ski lift no: " + i + " - " +
     * SkiLift.peopleCountOnSkiLift(i) + " people");
     * }
     * for (int i = 0; i < Route.totalCount(); i++) {
     * System.out.println("Route no: " + i + " - " + Route.peopleCountOnRoute(i) +
     * " people");
     * }
     * }
     */

    /*
     * public static void parseInput() {
     * Scanner fullLineScanner = new Scanner(System.in);
     * 
     * int knotsCount = fullLineScanner.nextInt();
     * fullLineScanner.nextLine();
     * for (int i = 0; i < knotsCount; i++) {
     * String line = fullLineScanner.nextLine();
     * parseKnot(line);
     * }
     * 
     * fullLineScanner.nextLine(); // Ommiting of empty line
     * 
     * int skiLiftsCount = fullLineScanner.nextInt();
     * fullLineScanner.nextLine();// finishing the line of int
     * for (int i = 0; i < skiLiftsCount; i++) {
     * String line = fullLineScanner.nextLine();
     * parseSkiLift(line);
     * }
     * 
     * fullLineScanner.nextLine(); // Ommiting of empty line
     * int routesCount = fullLineScanner.nextInt();
     * fullLineScanner.nextLine();
     * for (int i = 0; i < routesCount; i++) {
     * String line = fullLineScanner.nextLine();
     * parseRoute(line);
     * }
     * 
     * fullLineScanner.nextLine(); // Ommiting of empty line
     * 
     * int athleteGroupsCount = fullLineScanner.nextInt();
     * fullLineScanner.nextLine();
     * for (int i = 0; i < athleteGroupsCount; i++) {
     * String[] lines = new String[3];
     * for (int j = 0; j < 3; j++) {
     * lines[j] = fullLineScanner.nextLine();
     * }
     * parseAthleteGroup(lines);
     * }
     * }
     * 
     * public static void parseKnot(String line) {
     * Scanner singleLineScanner = new Scanner(line);
     * singleLineScanner.useLocale(Locale.ENGLISH);
     * 
     * long elevation = singleLineScanner.nextLong();
     * long x = singleLineScanner.nextLong();
     * long y = singleLineScanner.nextLong();
     * boolean isTransferKnot = singleLineScanner.hasNext("s");
     * 
     * new Knot(elevation, x, y, isTransferKnot);
     * }
     * 
     * public static void parseSkiLift(String line) {
     * Scanner singleLineScanner = new Scanner(line);
     * singleLineScanner.useLocale(Locale.ENGLISH);
     * 
     * int startKnotId = singleLineScanner.nextInt();
     * int endKnotId = singleLineScanner.nextInt();
     * int timeInterval = singleLineScanner.nextInt();
     * long cabinCapacity = singleLineScanner.nextLong();
     * long travelTime = singleLineScanner.nextLong();
     * 
     * Knot startKnot = Knot.get(startKnotId);
     * Knot endKnot = Knot.get(endKnotId);
     * 
     * SkiLift skiLift = new SkiLift(startKnot, endKnot, travelTime, cabinCapacity,
     * timeInterval);
     * eventQueue.insertEvent(new LiftCycle(new Time("9:00:00"), skiLift,
     * eventQueue));
     * }
     * 
     * public static void parseRoute(String line) {
     * Scanner singleLineScanner = new Scanner(line);
     * singleLineScanner.useLocale(Locale.ENGLISH);
     * 
     * int startKnotId = singleLineScanner.nextInt();
     * int endKnotId = singleLineScanner.nextInt();
     * int difficultyLevel = singleLineScanner.nextInt();
     * long travelTime = singleLineScanner.nextLong();
     * double baseAttractiveness = singleLineScanner.nextDouble();
     * double wearResistance = singleLineScanner.nextDouble();
     * 
     * Knot startKnot = Knot.get(startKnotId);
     * Knot endKnot = Knot.get(endKnotId);
     * 
     * new Route(difficultyLevel, baseAttractiveness, wearResistance, startKnot,
     * endKnot, travelTime);
     * }
     * 
     * public static void parseAthleteGroup(String[] lines) {
     * Scanner[] scanners = new Scanner[3];
     * for (int i = 0; i < 3; i++) {
     * scanners[i] = new Scanner(lines[i]);
     * scanners[i].useLocale(Locale.ENGLISH);
     * }
     * 
     * int athletesCount = scanners[0].nextInt();
     * int skillLevel = scanners[0].nextInt();
     * double spontaneityFactor = scanners[0].nextDouble();
     * double boredomFactor = scanners[0].nextDouble();
     * char type = scanners[0].next().charAt(0);
     * boolean isTracked = scanners[0].hasNext("s");
     * 
     * double skillWeight = scanners[1].nextDouble();
     * double surfaceWeight = scanners[1].nextDouble();
     * double boredomWeight = scanners[1].nextDouble();
     * 
     * int knotId = scanners[2].nextInt();
     * String timeString = scanners[2].next();
     * 
     * Knot knot = Knot.get(knotId);
     * Time time = new Time(timeString);
     * 
     * int secondsToAdd = 0;
     * if (scanners[2].hasNextInt()) {
     * secondsToAdd = scanners[2].nextInt();
     * }
     * 
     * for (int i = 0; i < athletesCount; i++) {
     * Athlete athlete = null;
     * if (type == 'L') {
     * athlete = new LocalAthlete(spontaneityFactor, boredomFactor,
     * skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
     * }
     * if (type == 'Z') {
     * athlete = new GreedyAthlete(spontaneityFactor, boredomFactor,
     * skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
     * }
     * if (type == 'K') {
     * athlete = new CollectionerAthlete(spontaneityFactor, boredomFactor,
     * skillLevel, surfaceWeight, boredomWeight, skillWeight, isTracked, knot);
     * }
     * 
     * Time athleteTime = new Time(time, i * secondsToAdd);
     * Event event = new Decision(athleteTime, athlete, knot, eventQueue);
     * eventQueue.insertEvent(event);
     * }
     * }
     */
}
