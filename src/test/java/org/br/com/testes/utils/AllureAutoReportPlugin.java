package org.br.com.testes.utils;

import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.ConcurrentEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Plugin Cucumber para executar o relatório Allure automaticamente ao final dos testes usando Allure CLI.
 */
public class AllureAutoReportPlugin implements ConcurrentEventListener {
    
    private static boolean alreadyExecuted = false;
    
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunFinished.class, event -> runAllureCli());
    }

    private void runAllureCli() {
        // Evita execução múltipla
        if (alreadyExecuted) {
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Já foi executado, ignorando...");
            return;
        }
        
        // Verifica se o Allure está habilitado
        if (!isAllureEnabled()) {
//            System.out.println("[INFO] AllureAutoReportPlugin: Allure desabilitado em allure.properties. Pulando geração do relatório.");
            return;
        }
        
        alreadyExecuted = true;
        // System.out.println("[DEBUG] AllureAutoReportPlugin: runAllureCli chamado!");
        try {
            // Aguarda 2 segundos para garantir que todos os arquivos foram gravados
            Thread.sleep(2000);

            // System.out.println("[DEBUG] AllureAutoReportPlugin: Iniciando servidor Allure...");
            // Inicia o servidor Allure diretamente dos resultados (gera e serve automaticamente)
            ProcessBuilder pbServe = new ProcessBuilder("cmd", "/c", "allure serve target/allure-results");
            pbServe.redirectErrorStream(true);
            Process serveProcess = pbServe.start();
            
            // Captura a saída do comando para obter a URL do servidor
            String serverUrl = captureServerUrl(serveProcess);
            
            // Aguarda um pouco para o servidor inicializar
            Thread.sleep(3000);
            
            if (serverUrl != null && !serverUrl.isEmpty()) {
                // Verifica se já existe um navegador aberto para essa URL
                if (!isBrowserAlreadyOpen(serverUrl)) {
                    // System.out.println("[DEBUG] AllureAutoReportPlugin: Abrindo navegador...");
                    // Abre o navegador na URL correta retornada pelo servidor
                    ProcessBuilder pbBrowser = new ProcessBuilder("cmd", "/c", "start " + serverUrl);
                    pbBrowser.start();
                    
                    System.out.println("[INFO] AllureAutoReportPlugin: Relatório Allure disponível em " + serverUrl);
                } else {
                    // System.out.println("[DEBUG] AllureAutoReportPlugin: Navegador já está aberto para " + serverUrl);
                }
            } else {
                System.out.println("[WARN] AllureAutoReportPlugin: Não foi possível obter a URL do servidor Allure");
            }
            
        } catch (Exception e) {
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Exceção capturada: " + e.getMessage());
            e.printStackTrace();
            // Não exibe erro se for relacionado a porta em uso
            if (!e.getMessage().contains("Address already in use") && 
                !e.getMessage().contains("BindException")) {
                System.err.println("Erro ao abrir o relatório Allure via CLI: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica se o Allure está habilitado no arquivo allure.properties
     */
    private boolean isAllureEnabled() {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/allure.properties");
            props.load(fis);
            fis.close();
            
            String enabled = props.getProperty("allure.enabled", "true");
            return "true".equalsIgnoreCase(enabled);
            
        } catch (IOException e) {
            // Se não conseguir ler o arquivo, assume que está habilitado por padrão
            System.out.println("[WARN] AllureAutoReportPlugin: Não foi possível ler allure.properties. Assumindo Allure habilitado.");
            return true;
        }
    }
    
    /**
     * Captura a URL do servidor Allure a partir da saída do comando
     */
    private String captureServerUrl(Process process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            // Padrão mais específico para capturar URLs do Allure
            Pattern urlPattern = Pattern.compile("http://[0-9.]+:[0-9]+");
            
            // Aguarda até 30 segundos para encontrar a URL
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 30000) {
                if (reader.ready()) {
                    line = reader.readLine();
                    if (line != null) {
                        // System.out.println("[DEBUG] AllureAutoReportPlugin: Saída do servidor: " + line);
                        Matcher matcher = urlPattern.matcher(line);
                        if (matcher.find()) {
                            String url = matcher.group();
                            // System.out.println("[DEBUG] AllureAutoReportPlugin: URL capturada: " + url);
                            return url;
                        }
                    }
                } else {
                    Thread.sleep(500);
                }
            }
            
            // Se não encontrou a URL, tenta uma abordagem alternativa
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Tentando abordagem alternativa para capturar URL...");
            return captureUrlFromNetstat();
            
        } catch (Exception e) {
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Erro ao capturar URL: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Captura a URL do servidor Allure usando netstat como fallback
     */
    private String captureUrlFromNetstat() {
        try {
            // Aguarda um pouco mais para o servidor inicializar
            Thread.sleep(5000);
            
            // Executa netstat para encontrar a porta do Allure
            ProcessBuilder pbNetstat = new ProcessBuilder("cmd", "/c", "netstat -an | findstr LISTENING");
            pbNetstat.redirectErrorStream(true);
            Process netstatProcess = pbNetstat.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(netstatProcess.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Procura por portas que podem ser do Allure (geralmente portas altas)
                if (line.contains("LISTENING") && line.matches(".*:[0-9]{4,5}.*")) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 2) {
                        String address = parts[1];
                        if (address.contains(":")) {
                            String port = address.split(":")[1];
                            // Verifica se é uma porta alta (provavelmente do Allure)
                            int portNum = Integer.parseInt(port);
                            if (portNum > 10000) {
                                String url = "http://localhost:" + port;
                                // System.out.println("[DEBUG] AllureAutoReportPlugin: URL encontrada via netstat: " + url);
                                return url;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Erro ao capturar URL via netstat: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Verifica se já existe um navegador aberto para a URL do Allure
     */
    private boolean isBrowserAlreadyOpen(String serverUrl) {
        try {
            // Extrai a porta da URL
            String port = serverUrl.split(":")[2];
            
            // Verifica se há processos do navegador acessando essa porta
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "netstat -an | findstr :" + port + " | findstr ESTABLISHED");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int connections = 0;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("ESTABLISHED")) {
                    connections++;
                }
            }
            
            // Se há conexões estabelecidas, provavelmente o navegador já está aberto
            return connections > 0;
            
        } catch (Exception e) {
            // System.out.println("[DEBUG] AllureAutoReportPlugin: Erro ao verificar navegador aberto: " + e.getMessage());
            return false;
        }
    }
} 