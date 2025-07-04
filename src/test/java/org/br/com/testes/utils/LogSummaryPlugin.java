package org.br.com.testes.utils;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class LogSummaryPlugin implements ConcurrentEventListener {
    private int testsRun = 0;
    private int failures = 0;
    private int errors = 0;
    private int skipped = 0;
    private long startTime = System.currentTimeMillis();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        testsRun++;
        if (event.getResult().getStatus() == Status.FAILED) {
            failures++;
        }
        if (event.getResult().getStatus() == Status.SKIPPED) {
            skipped++;
        }
        // Adapte para contar errors se necessário
    }

    private void handleTestRunFinished(TestRunFinished event) {
        double totalTime = (System.currentTimeMillis() - startTime) / 1000.0;
        LogFormatter.logFooterSummary(
            testsRun, failures, errors, skipped,
            totalTime, totalTime, java.time.LocalDateTime.now().toString()
        );
    }
} 