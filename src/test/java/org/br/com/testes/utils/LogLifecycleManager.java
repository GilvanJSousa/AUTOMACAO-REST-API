package org.br.com.testes.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogLifecycleManager {
    private static final ThreadLocal<String> testId = new ThreadLocal<>();

    @Before
    public void beforeScenario(Scenario scenario) {
        String id = UUID.randomUUID().toString();
        testId.set(id);

        String featurePath = scenario.getUri().getPath();
        String featureTag = extractFeatureTag(featurePath);

        String tagForPath = scenario.getSourceTagNames().stream().findFirst().orElse("no_tag").replace("@", "");
        String logDir = String.format("target/log/%s/%s", tagForPath, id);
        String logFile = String.format("%s/%s_log.txt", logDir, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd_MM_yy")));
        LogFileManager.setLogFile(logFile);

        LogFormatter.logTag(featureTag);
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

    // Método para extrair a tag da feature
    private static String extractFeatureTag(String featureFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(featureFilePath))) {
            String line;
            Pattern tagPattern = Pattern.compile("@\\w+");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = tagPattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group();
                }
                // Pare ao encontrar a linha "Feature:" para não pegar tags de cenários
                if (line.trim().startsWith("Feature:")) break;
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo da feature: " + e.getMessage());
        }
        return "[TAG_NOT_FOUND]";
    }
} 