# Passo a Passo: Criação de um Logger Java Avançado

Este guia rápido mostra como criar e organizar um logger Java avançado e reutilizável, seguindo o padrão da pasta `core/logger` com integração Allure e logs estruturados.

---

## 1. Estrutura de Pastas

Crie a seguinte estrutura no seu projeto:

```
core/
└── logger/
    ├── Console.java          # Constantes e separadores
    ├── ContextTime.java      # Medição de tempo com ThreadLocal
    ├── LogFormatter.java     # Sistema avançado de formatação
    ├── LogFileManager.java   # Gerenciamento de arquivos de log
```

---

## 2. Criação dos Arquivos

### Console.java (Constantes)
```java
package core.logger;

public class Console {
    public static final String SEPARATE = "===========================================================================================";
    public static final String SEPARATE_HYPHEN = "------------------------------------------------------------------------------------";
    public static final String ID_TAG_NOT_FOUND = "[ID tag not found]";
    public static final String DATA_TAG_NOT_FOUND = "[Data tag not found]";
}
```

### ContextTime.java (Medição Avançada)
```java
package core.logger;

import java.time.LocalDateTime;

public class ContextTime {
    private static ThreadLocal<LocalDateTime> timeSuiteInit = new ThreadLocal<>();
    private static ThreadLocal<LocalDateTime> timeTestInit = new ThreadLocal<>();

    public static void printTimeInitial() {
        timeTestInit.set(LocalDateTime.now());
        LogFormatter.logStep("Initial hour of test: " + timeTestInit.get());
        
        if (timeSuiteInit.get() == null)
            timeSuiteInit.set(timeTestInit.get());
    }

    public static void printTimeFinal() {
        LocalDateTime timeTestFinal = LocalDateTime.now();
        LogFormatter.logStep("Final hour of test: " + timeTestFinal);
    }
}
```

### LogFormatter.java (Sistema Avançado)
```java
package core.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Attachment(value = "Log do Step", type = "text/plain")
    public static String logStep(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("%s INFO %s", timestamp, message);
        System.out.println(formattedMessage);
        LogFileManager.writeLog(formattedMessage);
        return formattedMessage;
    }

    @Attachment(value = "Log JSON", type = "application/json")
    public static String logJson(String data) {
        // Formatar JSON para ficar legível
        System.out.println(data);
        return data;
    }

    public static void logTestId(String testId) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("%s +--:| TestID: %s", timestamp, testId);
        System.out.println(formattedMessage);
        LogFileManager.writeLog(formattedMessage);
    }

    public static void logFeature(String featureName) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String formattedMessage = String.format("%s INFO Feature: %s", timestamp, featureName);
        System.out.println(formattedMessage);
        LogFileManager.writeLog(formattedMessage);
    }
}
```

### LogFileManager.java (Gerenciamento de Arquivos)
```java
package core.logger;

import java.io.*;
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
}
```

---

## 3. Como Usar no Projeto

```java
import core.logger.LogFormatter;
import core.logger.ContextTime;

public class ExemploLoggerAvancado {
    public void executarTeste() {
        // Log de identificação do teste
        LogFormatter.logTestId("test-123");
        
        // Log de feature
        LogFormatter.logFeature("Login do Sistema");
        
        // Início da medição de tempo
        ContextTime.printTimeInitial();
        
        // Log de step com anexo no Allure
        LogFormatter.logStep("Iniciando processo de login");
        
        // Log de dados JSON
        LogFormatter.logJson("{\"user\": \"admin\", \"status\": \"success\"}");
        
        // Fim da medição de tempo
        ContextTime.printTimeFinal();
    }
}
```

---

## 4. Dependências Necessárias (Maven)

```xml
<dependencies>
    <!-- SLF4J para logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.36</version>
    </dependency>
    
    <!-- Jackson para formatação JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
    
    <!-- Allure para anexos -->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-java-commons</artifactId>
        <version>2.24.0</version>
    </dependency>
</dependencies>
```

---

## 5. Dicas de Expansão
- **Logs estruturados**: Implemente logs JSON para dados complexos
- **Integração Allure**: Use @Attachment para anexos automáticos
- **Thread-safety**: Use ThreadLocal para execuções paralelas
- **Performance**: Adicione logs de tempo de execução
- **Arquivos**: Configure logs em arquivo com data automática

---

Pronto! Agora você tem um logger Java avançado, reutilizável e integrado com relatórios para qualquer projeto. 