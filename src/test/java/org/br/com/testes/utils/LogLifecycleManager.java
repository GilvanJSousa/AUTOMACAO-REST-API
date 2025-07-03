package org.br.com.testes.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.util.UUID;

public class LogLifecycleManager {
    private static final ThreadLocal<String> testId = new ThreadLocal<>();

    @Before
    public void beforeScenario(Scenario scenario) {
        String id = UUID.randomUUID().toString();
        testId.set(id);

        // Extrai a primeira tag (ex: @login)
        String tag = scenario.getSourceTagNames().stream().findFirst().orElse("no_tag").replace("@", "");

        // Define o diretório e arquivo de log
        String logDir = String.format("target/log/%s/%s", tag, id);
        String logFile = String.format("%s/%s_log.txt", logDir, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd_MM_yy")));
        LogFileManager.setLogFile(logFile);

        // Imprime a tag no console
        System.out.println("@" + tag);

        // Loga TestID e Feature
        LogFormatter.logTestId(id);
        LogFormatter.logFeature(scenario.getName());
    }

    @After
    public void afterScenario() {
        LogFormatter.close();
        testId.remove();
    }

    public static String getTestId() {
        return testId.get();
    }
} 