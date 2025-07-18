# Logger Java Padrão (core/logger)

## Visão Geral

Este módulo define um padrão de logger Java para projetos de automação de testes, aplicações backend ou qualquer sistema que exija rastreabilidade, clareza e padronização de logs.
A estrutura foi pensada para ser plugável, reutilizável e facilmente adaptável a diferentes projetos.

---

## Estrutura Recomendada

```
core/
└── logger/
    ├── Console.java
    ├── ContextTime.java
    └── (outros utilitários de log)
```

---

## Objetivos

- Centralizar e padronizar a geração de logs.
- Facilitar a manutenção e evolução do sistema de logging.
- Permitir fácil integração com frameworks de teste, aplicações web, microserviços, etc.
- Suportar logs coloridos, logs de contexto, logs de tempo de execução e outros formatos.

---

## Passo a Passo para Criação e Uso

### 1. Crie a pasta `core/logger` no seu projeto

```bash
mkdir -p src/main/java/core/logger
```

### 2. Adicione as classes utilitárias

#### Exemplo: Console.java

```java
package core.logger;

public class Console {
    public static void log(String message) {
        System.out.println(message);
    }
    // Adicione métodos para logs coloridos, níveis (INFO, ERROR, etc.) conforme necessidade
}
```

#### Exemplo: ContextTime.java

```java
package core.logger;

public class ContextTime {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long elapsed() {
        return System.currentTimeMillis() - startTime;
    }
    // Permite medir o tempo de execução de blocos de código
}
```

### 3. Importe e utilize o logger no seu projeto

```java
import core.logger.Console;
import core.logger.ContextTime;

public class ExemploUsoLogger {
    public static void main(String[] args) {
        Console.log("Iniciando processo...");

        ContextTime timer = new ContextTime();
        timer.start();

        // ... código a ser medido ...

        Console.log("Tempo de execução: " + timer.elapsed() + " ms");
    }
}
```

---

## Boas Práticas

- Sempre utilize o logger centralizado para todas as saídas de log.
- Evite `System.out.println` dispersos pelo código.
- Adapte o logger para suportar diferentes níveis (INFO, WARN, ERROR) e formatos conforme a necessidade do projeto.
- Considere integração futura com frameworks como Log4j, SLF4J ou Allure, se necessário.

---

## Customização

- Adicione métodos para logs coloridos (ANSI), logs em arquivo, logs JSON, etc.
- Implemente logs de contexto para rastrear execuções paralelas ou multi-thread.
- Crie wrappers para integração com frameworks de teste (JUnit, Cucumber, etc).

---

## Exemplo de Estrutura Completa

```
core/
└── logger/
    ├── Console.java
    ├── ContextTime.java
    ├── LogFormatter.java
    ├── LogFileManager.java
    └── (outros utilitários)
```

---

## Licença

Este módulo pode ser utilizado livremente em projetos internos ou open source, desde que mantida a referência ao autor original. 