package skiingCentre.engine;

import java.util.Scanner;

public class Time {
    private int timeInSeconds;

    // makes Time from string HH:MM:SS
    public Time(String timeString) {
        Scanner scanner = new Scanner(timeString);
        scanner.useDelimiter(":");
        int hours = scanner.nextInt();
        int minutes = scanner.nextInt();
        int seconds = scanner.nextInt();
        this.timeInSeconds = seconds + 60 * minutes + 3600 * hours;
    }

    // returns absolute diffirence from this and b
    public int differ(Time b) {
        return Math.abs(this.timeInSeconds - b.timeInSeconds);
    }

    // it is for convinience of writing code, when we often check diffirence from
    // nine
    public int diffNine() {
        return timeInSeconds - 9 * 3600;
    }

    // this constructor is convinient to add and create new in one moment
    public Time(Time time, int secondsToAdd) {
        this.timeInSeconds = time.moment();
        this.add(secondsToAdd);
    }

    public void add(int secondsToAdd) {
        timeInSeconds += secondsToAdd;
    }

    public boolean isBefore15() {
        return timeInSeconds < 3600 * 15;
    }

    public boolean isBefore16() {
        return timeInSeconds < 3600 * 16;
    }

    public int moment() {
        return timeInSeconds;
    }

    // much more convienient to write than to write in every write situation
    public String formattedTime() {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
