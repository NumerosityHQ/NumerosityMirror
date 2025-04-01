package org.vaadin.numerosity.Subsystems;

import java.time.Duration;
import java.time.Instant;

public class BackgroundProcess {
    private Instant siteClockStart;
    private Instant siteClockEnd;
    private Instant questionClockStart;
    private Instant questionClockEnd;

    public void startSiteClock() {
        siteClockStart = Instant.now();
        System.out.println("Site clock started at: " + siteClockStart);
    }

    public void endSiteClock() {
        siteClockEnd = Instant.now();
        System.out.println("Site clock ended at: " + siteClockEnd);
    }

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

    public void startQuestionClock() {
        questionClockStart = Instant.now();
        System.out.println("Question clock started at: " + questionClockStart);
    }

    public void endQuestionClock() {
        questionClockEnd = Instant.now();
        System.out.println("Question clock ended at: " + questionClockEnd);
    }

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
