package org.vaadin.numerosity.Subsystems;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for managing timing operations in the application.
 * Provides methods to start, stop, and measure durations for site sessions and individual questions.
 */
public class BackgroundProcess {
    private Instant siteClockStart;
    private Instant siteClockEnd;
    private Instant questionClockStart;
    private Instant questionClockEnd;

    /**
     * Starts the site clock timer.
     * Records the current instant as the start time for the overall site session.
     */
    public void startSiteClock() {
        siteClockStart = Instant.now();
        System.out.println("Site clock started at: " + siteClockStart);
    }

    /**
     * Ends the site clock timer.
     * Records the current instant as the end time for the overall site session.
     */
    public void endSiteClock() {
        siteClockEnd = Instant.now();
        System.out.println("Site clock ended at: " + siteClockEnd);
    }

    /**
     * Calculates and returns the duration of the site session.
     *
     * @return the duration between start and end times, or Duration.ZERO if not properly started/ended
     */
    public Duration getSiteClock() {
        if (siteClockStart != null && siteClockEnd != null) {
            Duration duration = Duration.between(siteClockStart, siteClockEnd);
            System.out.println("Site clock duration: " + duration);
            return duration;
        } else {
            System.out.println("Site clock has not been started or ended.");
            return Duration.ZERO;
        }
    }

    /**
     * Starts the question clock timer.
     * Records the current instant as the start time for an individual question.
     */
    public void startQuestionClock() {
        questionClockStart = Instant.now();
        System.out.println("Question clock started at: " + questionClockStart);
    }

    /**
     * Ends the question clock timer.
     * Records the current instant as the end time for an individual question.
     */
    public void endQuestionClock() {
        questionClockEnd = Instant.now();
        System.out.println("Question clock ended at: " + questionClockEnd);
    }

    /**
     * Calculates and returns the duration for answering a question.
     *
     * @return the duration between start and end times, or Duration.ZERO if not properly started/ended
     */
    public Duration getQuestionClock() {
        if (questionClockStart != null && questionClockEnd != null) {
            Duration duration = Duration.between(questionClockStart, questionClockEnd);
            System.out.println("Question clock duration: " + duration);
            return duration;
        } else {
            System.out.println("Question clock has not been started or ended.");
            return Duration.ZERO;
        }
    }
}
