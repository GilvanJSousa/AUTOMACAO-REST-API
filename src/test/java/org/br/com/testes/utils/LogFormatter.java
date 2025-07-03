package org.br.com.testes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Attachment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    private static final Logger logger = LoggerFactory.getLogger(LogFormatter.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static void logToFile(String message) {
        LogFileManager.writeLog(message);
    }

    public static void logTestId(String testId) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("[%s] +--:| TestID: %s", timestamp, testId);
        System.out.println(formattedMessage);
        logToFile(formattedMessage);
    }

    public static void logFeature(String featureName) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("[%s] +---:| Feature: %s", timestamp, featureName);
        System.out.println(formattedMessage);
        logToFile(formattedMessage);
    }

    @Attachment(value = "Log do Step", type = "text/plain")
    public static String logStep(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("[%s] +----: %s", timestamp, message);
        System.out.println(formattedMessage);
        logToFile(formattedMessage);
        return formattedMessage;
    }

    @Attachment(value = "Log JSON do Step", type = "application/json")
    public static String logStepJson(String stepName, String data) {
        try {
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            
            // Criar objeto JSON estruturado
            String jsonLog = String.format("""
                {
//                    "timestamp": "%s",
//                    "step": "%s",
                    "Status Code": %s
                }""", timestamp, stepName, data);
            
            // Formatar JSON para ficar legível
            JsonNode jsonNode = objectMapper.readTree(jsonLog);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            
            System.out.println(prettyJson);
            return prettyJson;
        } catch (Exception e) {
            // Se não conseguir formatar como JSON, retorna como texto simples
            return logStep(stepName + ": " + data);
        }
    }

    @Attachment(value = "Log JSON", type = "application/json")
    public static String logJson(String data) {
        try {
            // Formatar JSON para ficar legível
            JsonNode jsonNode = objectMapper.readTree(data);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            
            System.out.println(prettyJson);
            return prettyJson;
        } catch (Exception e) {
            // Se não conseguir formatar como JSON, retorna como texto simples
            return logStep("JSON inválido: " + data);
        }
    }

    @Attachment(value = "Log de Requisição", type = "application/json")
    public static String logRequestJson(String method, String url, String body) {
        try {
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            
            String jsonLog = String.format("""
                {
                    "timestamp": "%s",
                    "type": "REQUEST",
                    "method": "%s",
                    "url": "%s",
                    "body": %s
                }""", timestamp, method, url, body);
            
            JsonNode jsonNode = objectMapper.readTree(jsonLog);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            
            logger.info(prettyJson);
            logToFile(prettyJson);
            return prettyJson;
        } catch (Exception e) {
            logRequest(method, url, body);
            return "Erro ao formatar JSON - log gravado como texto";
        }
    }

    @Attachment(value = "Log de Resposta", type = "application/json")
    public static String logResponseJson(String statusCode, String body) {
        try {
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            
            String jsonLog = String.format("""
                {
                    "timestamp": "%s",
                    "type": "RESPONSE",
                    "statusCode": "%s",
                    "body": %s
                }""", timestamp, statusCode, body);
            
            JsonNode jsonNode = objectMapper.readTree(jsonLog);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            
            logger.info(prettyJson);
            logToFile(prettyJson);
            return prettyJson;
        } catch (Exception e) {
            logResponse(statusCode, body);
            return "Erro ao formatar JSON - log gravado como texto";
        }
    }

    public static void logRequest(String method, String url, String body) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logMessage = String.format("==================================================\n[%s] REQUEST: %s %s\nBody:\n%s\n==================================================", 
            timestamp, method, url, body);
        
        logger.info(logMessage);
        logToFile(logMessage);
    }

    public static void logResponse(String statusCode, String body) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logMessage = String.format("==================================================\n[%s] RESPONSE: Status Code %s\nBody:\n%s\n==================================================", 
            timestamp, statusCode, body);
        
        logger.info(logMessage);
        logToFile(logMessage);
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    public static void close() {
        LogFileManager.close();
    }
} 