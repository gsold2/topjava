package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StopWatchUtil extends Stopwatch {

    private final Logger log;
    private final List<String> record;

    public StopWatchUtil(Logger log, List<String> record) {
        this.log = log;
        this.record = record;
    }

    private void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String message = String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos));
        log.info(message);
        record.add(message);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }
}
