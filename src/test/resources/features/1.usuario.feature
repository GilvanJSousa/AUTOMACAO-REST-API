@usuario @all
Feature: Gerenciamento de Usuários CMS
  Como um administrador de sistema
  Eu quero gerenciar usuários CMS
  Para que eu possa controlar o acesso ao sistema

  @ATJ-332 @API
  Scenario: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    Then a API deve retornar o código de status 201

  @ATJ-331 @API
  Scenario: Realizar login com usuário CMS
    Given eu envio a requisição de login com as credenciais do usuário
    Then a API deve retornar o código de status 200
    And o token de autenticação deve ser retornado

  @ATJ-329 @API
  Scenario: Buscar a lista de usuários CMS com autenticação
    When eu envio a requisição de listar de usuários com autenticação
    Then a API deve retornar o código de status 200

  @API
  Scenario: Busca de usuario por ID
    Given eu envio a requisição de busca de usuário por ID
    Then a API deve retornar o código de status 200

  @API
  Scenario: Validar alteração de usuário
    When que envio a solicitação de PUT com ID
    Then valido o retorno usuario atualizado com status code 200 e mensagem 'Usuário atualizado ou sem alterações'

  @ATJ-310 @API
  Scenario: Validar exclusao de usuario
    Given envio uma solicitação de DELETE para o ID
    Then deve retornar o status code 204 para exclusão
