package org.br.com.testes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    private static final Logger logger = LoggerFactory.getLogger(LogFormatter.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");

    private static void logToFile(String message) {
        LogFileManager.writeLog(message);
    }

    public static void logStep(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logMessage = String.format("==================================================\n[%s] STEP: %s\n==================================================", 
            timestamp, message);
        
        logger.info(logMessage);
        logToFile(logMessage);
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