package org.br.com.testes.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = Constants.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Não foi possível encontrar o arquivo config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo config.properties", e);
        }
    }

    public static final String BASE_URL = properties.getProperty("cielo.base.url");
    public static final String MERCHANT_ID = properties.getProperty("cielo.merchant.id");
    public static final String MERCHANT_KEY = properties.getProperty("cielo.merchant.key");
} 