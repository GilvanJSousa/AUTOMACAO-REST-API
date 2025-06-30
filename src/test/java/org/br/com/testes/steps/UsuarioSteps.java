package org.br.com.testes.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.usuarios.UsuarioController;


public class UsuarioSteps {

    private final UsuarioController usuarioController;


    public UsuarioSteps(){
        this.usuarioController = new UsuarioController();
    }

    @Given("que cadastro um usuario valido para os testes")
    public void queCadastroUmUsuarioValidoParaOsTestes() {
        usuarioController.cadastrarNovoUsuario();
    }

    @Given("envio a requisição de login com usuario cadastrado")
    public void envioARequisicaoDeLoginComUsuarioCadastrado() {
        usuarioController.realizarLogin();
    }

    @Given("envio a requisição de login com usuario cadastrado e email invalido")
    public void envioARequisiçãoDeLoginComUsuarioCadastradoEEmailInvalido() {
        usuarioController.realizarLoginInvalido();
    }

    @Given("que envio a solicitação de GET com id")
    public void queEnvioASolicitacaoDePUTComId() {
        usuarioController.consultarUsuarioPorId();
    }

    @Given("que envio a solicitação de GET com id invalido")
    public void queEnvioASolicitacaoDePUTComIdInvalido() {
        usuarioController.consultarUsuarioIdInvalido();
    }

    @Given("que envio a requisição PUT com id para editar o usuario")
    public void queEnvioARequisicaoPUTComId() {
        usuarioController.atualizarUsuarioPorId();
    }

    @Given("envio a solicitação de GET para listar todos os usuarios")
    public void envioASolicitacaoDeGETParaListarTodosOsUsuarios() {
        usuarioController.listarUsuariosComAutenticacao();
    }

    @Given("envio uma solicitação de DELETE para excluir o usuario")
    public void envioUmaSolicitacaoDeDELETEParaExcluirOUsuario() {
        usuarioController.excluirUsuarioPorId();
    }
    
    @Given("envio a solicitação de cadastro na loja")
    public void envioASolicitacaoDeCompraNaLoja() {
        usuarioController.cadastrarNovoUsuario();
    }

    @Given("envio a solicitação de GET com id")
    public void envioASolicitaoDeGETComId() {
        usuarioController.consultarUsuarioPorId();
    }

    @Then("deve retornar usuario com status code {int}")
    public void deveRetornarUsuarioComStatusCode(int arg0) {
        usuarioController.validarStatusCode(200);
    }

    @And("envio a solicitação de cadastro de usuario com email ja cadastrado")
    public void envioASolicitacaoDeCadastroDeUsuarioComEmailJaCadastrado() {
        usuarioController.cadastrorUsuarioComEmailJaCadastrado();
        
    }

    @Then("valido a resposta da API retornar o status code {int} e mensagem {string}")
    public void validoARespostaDaAPIRetornarOStatusCodeEMensagemEsteEmailJáEstaSendoUsado(int statusCode, String mensagem) {
        usuarioController.validarStatusCodeEMensagem(statusCode, mensagem);
    }

    @Then("deve retornar usuario com status code {int} e mesagem {string}")
    public void deveRetornarUsuarioComStatusCodeEMesagemLoginRealizadoComSucesso(int statusCode, String mensagem) {
        usuarioController.validarStatusCodeEMensagem(statusCode, mensagem);
    }

    @Then("deve retornar usuario com status code {int} e mensagem {string}")
    public void deveRetornarUsuarioComStatusCodeEMensagemCadastroRealizadoComSucesso(int statusCode, String mensagem) {
        usuarioController.validarStatusCodeEMensagem(statusCode, mensagem);
    }
}