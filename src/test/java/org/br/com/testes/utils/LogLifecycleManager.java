package org.br.com.testes.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class LogLifecycleManager {
    
    @Before
    public void beforeScenario() {
        // Inicialização do log é feita automaticamente pelo LogFileManager
    }

    @After
    public void afterScenario() {
        LogFormatter.close();
    }
} 