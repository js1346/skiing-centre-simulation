package skiingCentre.statics;

import skiingCentre.infrastructure.*;
import skiingCentre.athlete.Athlete;
import skiingCentre.engine.Time;

public class Writer {
    // taking nearly all writing functions into one place
    public static void printLastStatistics() {
        System.out.println("Ride statistics:");
        for (int i = 0; i < SkiLift.totalCount(); i++) {
            System.out.println("Ski lift no: " + i + " - " + SkiLift.peopleCountOnSkiLift(i) + " people");
            System.out.println("Ski lift no: " + i + " max queue lenght: " + SkiLift.get(i).maxLen());
            System.out.println(
                    "Ski lift no: " + i + " percentage: " + String.format("%.2f", SkiLift.get(i).percentage()));
            System.out.println("Ski lift no: " + i + " avg queue lenght: "
                    + String.format("%.2f", SkiLift.get(i).averageQueueLength()));
        }
        for (int i = 0; i < Route.totalCount(); i++) {
            System.out.println("Route no: " + i + " - " + Route.peopleCountOnRoute(i) + " people");
            System.out
                    .println("Route no: " + i + " surface - " + String.format("%.2f", Route.get(i).surfaceLeveling()));
        }
    }

    public static void printRide(Time time, Athlete athlete, GraphEdge edge, boolean start) {
        String whatItIs = new String();
        String startEnd = new String();

        if (edge.isSkiLift())
            whatItIs = "skiLift";
        else
            whatItIs = "Route";
        if (start)
            startEnd = "started";
        else
            startEnd = "ended";

        System.out.println(time.formattedTime() + ": athlete nr: " + athlete.number()
                + startEnd + " ride on " + whatItIs + " numbered: " + edge.number());
    }

    public static void printQueueEvent(Time time, Athlete athlete, GraphEdge edge) {
        System.out.println(time.formattedTime() + ": athlete number: " + athlete.number()
                + " stood in queue numbered: " + edge.number());
    }
}
