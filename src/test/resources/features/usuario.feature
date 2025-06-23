@UsuarioCms @API @ATJ-334
Feature: Gerenciamento de Usuários CMS
  Como um administrador de sistema
  Eu quero gerenciar usuários CMS
  Para que eu possa controlar o acesso ao sistema

  @CT-3001 @ATJ-332 @API
  Scenario: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    Given o sistema processa a requisição
    Then a API deve retornar o código de status 201

  @CT-3002 @ATJ-331 @API
  Scenario: Realizar login com usuário CMS
    Given eu envio a requisição de login com as credenciais do usuário
    Then a API deve retornar o código de status 200
    And o token de autenticação deve ser retornado

  @CT-3003 @ATJ-329 @API
  Scenario: Buscar a lista de usuários CMS com autenticação
    When eu envio a requisição de listar de usuários com autenticação
    Then a API deve retornar o código de status 200
    And os dados do usuário devem ser retornados na resposta


  @CT-3004
  Scenario: Busca de usuario por ID
    Given que eu tenho um token de autenticação válido
    When eu envio a requisição de busca de usuário por ID
    Then a API deve retornar o código de status 200
    And os dados do usuário devem ser retornados na resposta

  @CT-3005
  Scenario: Validar alteração de usuário
    Given que eu tenho um token de autenticação válido
    When que envio a solicitação de PUT com ID
    Then valido o retorno usuario atualizado com status code 200 e mensagem 'Usuário atualizado ou sem alterações'



  @CT-3006 @ATJ-330 @API
  Scenario: Validar exclusão de usuario
    When envio uma solicitação de DELETE para o ID
    Then deve retornar o status code 204 para exclusão
