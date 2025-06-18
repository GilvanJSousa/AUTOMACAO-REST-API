@FuncionalideUsuario
Feature: Validar funcionalidades de usuario


 Cadastrar usuario
  @CT-1001
  Scenario: Cadastrar usuário válido para os testes
    Given que cadastro um usuario valido para os testes
    Then deve retornar usuario com status code 200


  @CT-1002
  Scenario: Reallizar login com usuario cadastrado
    Given envio a requisição de login com usuario cadastrado
    Then deve retornar usuario com status code 200

# Alteração de usuario
  @CT-1003
  Scenario: Validar Busca de usuario por ID
    Given que envio a solicitação de GET com id
    Then deve retornar usuario com status code 200

    # Listar usuarios cadastrados
  @CT-1004
  Scenario: Validar listar usuarios cadastrados
    Given envio a solicitação de GET para listar todos os usuarios
    Then deve retornar usuario com status code 200

  @CT-1005
  Scenario: Validar Editar usuario
    Given que envio a requisição PUT com id para editar o usuario
    Then deve retornar usuario com status code 200

# Excluir usuario
  @CT-1006
  Scenario: Validar exclusão de usuario
    Given envio uma solicitação de DELETE para excluir o usuario
    Then deve retornar usuario com status code 200


# Cadastrar usuario com email ja utilizado
  @CT-1007
  Scenario: Validar cadastro com email ja utilizado
    Given que cadastro um usuario valido para os testes
    And envio a solicitação de cadastro de usuario com email ja cadastrado
    Then valido a resposta da API retornar o status code 400 e mensagem 'Este email já está sendo usado'


