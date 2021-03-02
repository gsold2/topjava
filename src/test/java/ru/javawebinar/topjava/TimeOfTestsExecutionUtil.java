package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeOfTestsExecutionUtil extends Stopwatch {

    private final List<String> records;

    public TimeOfTestsExecutionUtil(List<String> records) {
        this.records = records;
    }

    @Override
    protected void finished(long nanos, Description description) {
        String testName = description.getMethodName();
        long time = TimeUnit.NANOSECONDS.toMillis(nanos);
        int gapLength = 41 - testName.length() - Long.toString(time).length();
        String gap = String.format("%0" + gapLength + "d", 0).replace('0', ' ');

        String message = String.format("%s" + gap + "%d ms",
                testName, time) + "\n";
        records.add(message);
    }
}
