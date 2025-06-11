package org.br.com.testes.controllers.recorrencia;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.recorrencia.RecorrenciaRequest;
import org.br.com.testes.model.recorrencia.RecorrenciaRequest.*;
import org.br.com.testes.utils.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RecorrenciaController {
    private static final Logger logger = LoggerFactory.getLogger(RecorrenciaController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_RECURRENT = "/1/RecurrentPayment";
    private static final String MERCHANT_ID = "a5d4e7b9-2f1c-4d3e-8f6a-9b0c1d2e3f4a";
    private static final String MERCHANT_KEY = "0123456789012345678901234567890123456789";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoRecorrencia() throws Exception {
    }

    public void enviarRequisicaoRecorrencia() {
    }

}
