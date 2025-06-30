package org.br.com.testes.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.br.com.testes.manager.ArtigosManager;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.LogFormatter;

/**
 * Hooks para gerenciamento do ciclo de vida dos testes
 */
public class Hooks {

    @Before
    public void setUp() {
        LogFormatter.logStep("Iniciando cenário de teste");
    }

    @After
    public void tearDown() {
        LogFormatter.logStep("Finalizando cenário de teste");
        
        // Limpar dados do ThreadLocal para evitar vazamento de memória
        try {
            TokenManager.remove();
            UsuarioManager.remove();
            ArtigosManager.remove();
            CategoriaManager.remove();
            LogFormatter.logStep("Dados do ThreadLocal limpos com sucesso");
        } catch (Exception e) {
            LogFormatter.logStep("Erro ao limpar dados do ThreadLocal: " + e.getMessage());
        }
    }
} 