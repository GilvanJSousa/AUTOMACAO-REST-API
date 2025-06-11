package org.br.com.testes.steps.recorrencia;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.recorrencia.RecorrenciaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecorrenciaSteps {
    private final RecorrenciaController recorrenciaController;
    private static final Logger logger = LoggerFactory.getLogger(RecorrenciaSteps.class);

    public RecorrenciaSteps() {
        recorrenciaController = new RecorrenciaController();
    }


}
