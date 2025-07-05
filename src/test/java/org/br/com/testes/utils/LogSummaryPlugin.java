package org.br.com.testes.utils;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.StringBuilder;

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
        // Removido: geração automática do Allure e manipulação de diretórios customizados
    }

    private boolean isAllureEnabled() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/properties.allure")) {
            prop.load(fis);
            return Boolean.parseBoolean(prop.getProperty("allure.enabled", "true"));
        } catch (IOException e) {
            return true; // padrão: ativado
        }
    }
} 