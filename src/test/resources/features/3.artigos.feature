@artigos @all
Feature: Validar Operações relacionadas a artigos
  Como usuario do sistema
  Eu quero gerenciar artigos
  Para organizar e compartilhar conteudos

  Background: Criar um novo usuário, efetuar login e preparar dados para artigos
    Given que envio uma requisição de registro de usuario CMS
    When o sistema processa a requisição
    Then eu envio a requisição de login com as credenciais do usuário
    And que envio uma requisição de cadastro de categoria
#    And preparo o autor para os artigos

  @CT-3001
  Scenario: Validar Criar um novo artigo
    Given que envio uma requisição de cadastro de Artigos
    Then a API Artigos deve retornar o código de status 201

  @CT-3002
  Scenario: Listar artigos
    Given que envio uma requisição de cadastro de Artigos
    When eu envio a requisição de listar Artigos com autenticação
    Then a API Artigos deve retornar o código de status 200

  @CT-3003
  Scenario: Buscar artigos por ID
    Given que envio uma requisição de cadastro de Artigos
    When eu envio a requisição de busca de artigos por ID
    Then a API Artigos deve retornar o código de status 200

  @CT-3004
  Scenario: Atualizar artigos
    Given que envio uma requisição de cadastro de Artigos
    When eu envio a requisição PUT Artigos com ID
    Then a API Artigos deve retornar o código de status 200

  @CT-3005
  Scenario: Validar exclusão de artigos
    Given que envio uma requisição de cadastro de Artigos
    When eu envio a requisição DELETE Artigos com ID
    Then a API Artigos deve retornar o status code 204 para exclusão

