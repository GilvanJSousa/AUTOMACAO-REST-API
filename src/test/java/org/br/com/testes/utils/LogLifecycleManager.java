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

        // Extrai o nome da feature do arquivo (ex: login.feature -> login)
        String featureName = new java.io.File(featurePath).getName().replace(".feature", "");

        // Define o diretório e arquivo de log por feature e data
        String logDir = String.format("target/log/%s", featureName);
        String logFile = String.format("%s/%s_log.txt", logDir, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd_MM_yy")));
        LogFileManager.setLogFile(logFile);

        // Cabeçalho só se o arquivo estiver vazio
        java.io.File f = new java.io.File(logFile);
        boolean isFirstScenario = !f.exists() || f.length() == 0;
        if (isFirstScenario) {
            LogFormatter.logHeader();
        } else {
            // Adiciona linha em branco antes de cada novo cenário (exceto o primeiro)
            LogFileManager.writeLog("");
        }

        // Monta string de tags: @featureTag | @cenarioTag
        String cenarioTag = scenario.getSourceTagNames().stream()
            .filter(tag -> !tag.equals(featureTag))
            .findFirst().orElse("");
        String tagsLog = featureTag;
        if (!cenarioTag.isEmpty()) {
            tagsLog += " | " + cenarioTag;
        }
        LogFormatter.logTag(tagsLog);
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

    // Novo método para rodapé
    public static void logTestSummary(int testsRun, int failures, int errors, int skipped, double timeElapsed, double totalTime, String finishedAt) {
        LogFileManager.writeLog("");
        LogFileManager.writeLog(String.format("Tests run: %d, ", testsRun));
        LogFileManager.writeLog(String.format("Failures: %d, ", failures));
        LogFileManager.writeLog(String.format("Errors: %d, ", errors));
        LogFileManager.writeLog(String.format("Skipped: %d, ", skipped));
        LogFileManager.writeLog(String.format("Time elapsed: %.2f s", timeElapsed));
        LogFileManager.writeLog(String.format("Total time:  %.3f s", totalTime));
        LogFileManager.writeLog(String.format("Finished at: %s", finishedAt));
        LogFileManager.writeLog("-------------------------------------------------------");
        LogFileManager.writeLog("BUILD SUCCESS");
        LogFileManager.writeLog("-------------------------------------------------------");
    }
} 