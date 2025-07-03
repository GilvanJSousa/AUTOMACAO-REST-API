package org.br.com.testes.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileManager {
    private static final String LOG_DIR = "logs";
    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static PrintWriter logWriter;

    static {
        createLogDirectory();
        initializeLogFile();
    }

    private static void createLogDirectory() {
        try {
            Path logPath = Paths.get(LOG_DIR);
            if (!Files.exists(logPath)) {
                Files.createDirectories(logPath);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório de logs: " + e.getMessage());
        }
    }

    private static void initializeLogFile() {
        try {
            String fileName = String.format("%s/test_%s.log", LOG_DIR, LocalDateTime.now().format(FILE_DATE_FORMATTER));
            File logFile = new File(fileName);
            logWriter = new PrintWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            System.err.println("Erro ao inicializar arquivo de log: " + e.getMessage());
        }
    }

    public static void writeLog(String message) {
        if (logWriter != null) {
            logWriter.println(message);
            logWriter.flush();
        }
    }

    public static void close() {
        if (logWriter != null) {
            logWriter.close();
        }
    }

    public static void setLogFile(String filePath) {
        close();
        try {
            File logFile = new File(filePath);
            logFile.getParentFile().mkdirs();
            logWriter = new PrintWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            System.err.println("Erro ao inicializar arquivo de log: " + e.getMessage());
        }
    }
} 