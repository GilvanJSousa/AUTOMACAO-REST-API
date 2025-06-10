package org.br.com.testes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    private static final Logger logger = LoggerFactory.getLogger(LogFormatter.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");

    public static void logStep(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        logger.info("[{}] STEP: {}", timestamp, message);
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
} 